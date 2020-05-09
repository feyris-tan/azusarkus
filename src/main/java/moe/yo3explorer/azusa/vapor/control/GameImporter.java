package moe.yo3explorer.azusa.vapor.control;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jp.gr.java_conf.dangan.util.lha.LhaHeader;
import jp.gr.java_conf.dangan.util.lha.LhaInputStream;
import moe.yo3explorer.azusa.vapor.entity.*;
import org.jboss.logging.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.zip.GZIPOutputStream;

@ApplicationScoped
public class GameImporter
{
    @Inject
    Logger logger;

    @Inject
    SkuGenerator skuGenerator;

    public Game tryImportGame(LhaInputStream lha)
    {
        try {
            return importGame(lha);
        } catch (IOException e) {
            try {
                lha.close();
            } catch (IOException ioException) {
                logger.warn("Failed to close LHA Input!");
            }
            throw new GameImportException(e);
        }
        catch (GameImportException gie)
        {
            try {
                if (lha != null)
                    lha.close();
                lha.closeEntry();
            } catch (IOException e) {
                logger.warn("Failed to close LHA Input 2!");
            }
            catch (NullPointerException f)
            {
                logger.warn("LHA stream was closed too early");
            }
            throw new GameImportException(gie);
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public Game importGame(@NotNull LhaInputStream lha) throws IOException {
        logger.info("Begin importing Vapor title from LHA...");
        LinkedList<ResourceFile> resourcesRequired = new LinkedList<>();
        LinkedList<MapFile> mapsRequired = new LinkedList<>();
        Hashdeep contentHashcalculator = new Hashdeep();

        Long rpgRtExeId = null;
        Date rpgRtExeDate = null;
        byte[] rpgRtLdb = null;
        Date rpgRtLdbDate = null;
        RpgRtIni rpgRtIni = null;
        Date rpgRtIniDate = null;
        byte[] rpgRtLmt = null;
        Date rpgRtLmtDate = null;

        while (true)
        {
            LhaHeader lhaHeader = lha.getNextEntry();
            if (lhaHeader == null)
                break;
            String fname = lhaHeader.getPath().toLowerCase();
            if (fname.endsWith("\\"))
                continue;
            byte[] unpackedEntry = extractLhaEntry(lha, lhaHeader);
            contentHashcalculator.engineUpdate(unpackedEntry,0,unpackedEntry.length);

            boolean isPng = fname.endsWith(".png");
            boolean isAvi = fname.startsWith("movie") && fname.endsWith(".avi");
            boolean isMidi = fname.startsWith("music") && fname.endsWith(".mid");
            boolean isWav = (fname.startsWith("music") || fname.startsWith("sound")) && fname.endsWith(".wav");
            boolean isExe = fname.equalsIgnoreCase("RPG_RT.exe");
            boolean isLdb = fname.equalsIgnoreCase("RPG_RT.ldb");
            boolean isIni = fname.equalsIgnoreCase("RPG_RT.ini");
            boolean isLmt = fname.equalsIgnoreCase("RPG_RT.lmt");
            boolean isLmu = fname.startsWith("map") && fname.endsWith(".lmu");
            boolean isDll = !fname.contains("\\") && fname.endsWith(".dll");
            if (isPng || isAvi || isMidi || isWav || isDll)
            {
                importResource(resourcesRequired,lhaHeader.getPath(),unpackedEntry);
                continue;
            }
            else if (isExe)
            {
                rpgRtExeId = getZBlob(unpackedEntry).id;
                rpgRtExeDate = lhaHeader.getLastModified();
                continue;
            }
            else if (isLdb)
            {
                rpgRtLdb = unpackedEntry;
                rpgRtLdbDate = lhaHeader.getLastModified();
                continue;
            }
            else if (isIni)
            {
                rpgRtIni = RpgRtIni.parse(unpackedEntry);
                rpgRtIniDate = lhaHeader.getLastModified();
                if (rpgRtIni.getGameTitle() == null || rpgRtIni.getGameTitle().equalsIgnoreCase("Untitled"))
                {
                    throw new GameImportException("Game requires a title!");
                }
                continue;
            }
            else if (isLmt)
            {
                rpgRtLmt = unpackedEntry;
                rpgRtLmtDate = lhaHeader.getLastModified();
                continue;
            }
            else if (isLmu)
            {
                mapsRequired.add(createMapFile(lhaHeader,unpackedEntry));
                continue;
            }

            logger.warnf("Don't know what to do with file: %s",lhaHeader.getPath());
            lha.close();
            throw new GameImportException(String.format("Don't know what to do with file: %s", lhaHeader.getPath()));
        }
        lha.close();

        String contentHash = contentHashcalculator.toString();
        long contentHashTest = Game.count("contenthash = ?1", contentHash);
        if (contentHashTest > 0)
            throw new GameImporterDuplicateException();

        if (rpgRtExeId == null)
            throw new GameImportException("The LHA does not contain an RPG_RT.exe");

        Game game = new Game();
        game.vapor_sku = skuGenerator.generateSku();
        game.gametitle = rpgRtIni.getGameTitle();
        game.mapeditmode = rpgRtIni.getMapeditmode();
        game.mapeditzoom = rpgRtIni.getMapeditzoom();
        game.fullpackageflag = rpgRtIni.getFullpackageflag();
        game.knownversion = rpgRtIni.getKnownversion();
        game.dateadded = new Date();
        game.rpg_rtid = rpgRtExeId;
        game.lcfmaptree = rpgRtLmt;
        game.lcfdatabase = rpgRtLdb;

        Integer guessedRtp = guessRtp(rpgRtExeId);
        if (guessedRtp != null)
            game.resourcedependency = guessedRtp;

        game.exedate = rpgRtExeDate;
        game.ldbdate = rpgRtLdbDate;
        game.inidate = rpgRtIniDate;
        game.lmtdate = rpgRtLdbDate;
        game.contenthash = contentHash;
        game.rtp = false;
        game.persist();

        for (ResourceFile resourceFile : resourcesRequired) {
            resourceFile.gameid = game.id;
            resourceFile.persist();
        }

        for (MapFile mapFile : mapsRequired) {
            mapFile.gameid = game.id;
            mapFile.persist();
        }
        return game;
    }

    private @Nullable Integer guessRtp(Long rpgRtExe)
    {
        List<Game> list = Game.find("rpg_rtid = ?1 AND rtp = TRUE",rpgRtExe).list();
        if (list.size() > 0)
            return list.get(0).id;
        return null;
    }

    private @NotNull MapFile createMapFile(@NotNull LhaHeader lhaHeader, byte[] unpackedEntry) {
        String fname = lhaHeader.getPath();
        fname = fname.substring(3);
        fname = fname.substring(0,fname.length() - 4);

        MapFile mapFile = new MapFile();
        mapFile.mapnumber = Short.parseShort(fname);
        mapFile.dateadded = new Date();
        mapFile.mapdata = unpackedEntry;
        mapFile.filedate = lhaHeader.getLastModified();
        return mapFile;
    }

    private byte @Nullable [] extractLhaEntry(LhaInputStream lis, @NotNull LhaHeader lh)
    {
        try {
            long lhaSize = lh.getOriginalSize();
            if (lhaSize > Integer.MAX_VALUE) {
                throw new IOException("An LHA entry is too large.");
            }

            int lhasize32 = (int)lhaSize;
            byte[] buffer = new byte[lhasize32];
            lis.read(buffer,0,lhasize32);
            return buffer;
        } catch (IOException e) {
            logger.errorf(e,"Couldn't unpack an LHA entry.");
            return null;
        }
    }

    @Transactional(Transactional.TxType.MANDATORY)
    private void importResource(@NotNull List<ResourceFile> resourceFiles, String fname, byte[] buffer)
    {
        ZBlob zblob = getZBlob(buffer);

        ResourceFile resourceFile = new ResourceFile();
        resourceFile.zblobid = zblob.id;
        resourceFile.filename = fname;
        resourceFile.resourcetype = getResourceType(fname);
        resourceFile.dateadded = new Date();
        resourceFiles.add(resourceFile);
    }

    @NotNull
    private ZBlob getZBlob(byte[] buffer) {
        Hashdeep hashdeep = new Hashdeep();
        hashdeep.engineUpdate(buffer,0,buffer.length);
        String hash = hashdeep.toString();
        ZBlob zblob = testForZblob(hash);
        if (zblob == null)
            zblob = createZblob(buffer,hash);
        return zblob;
    }

    private short getResourceType(@NotNull String fname)
    {
        String lcase = fname.toLowerCase();
        if (lcase.startsWith("backdrop\\"))
            return 1;
        else if (lcase.startsWith("battle\\"))
            return 2;
        else if (lcase.startsWith("charset\\"))
            return 3;
        else if (lcase.startsWith("chipset\\"))
            return 4;
        else if (lcase.startsWith("faceset\\"))
            return 5;
        else if (lcase.startsWith("gameover\\"))
            return 6;
        else if (lcase.startsWith("monster\\"))
            return 7;
        else if (lcase.startsWith("movie\\"))
            return 8;
        else if (lcase.startsWith("music\\"))
            return 9;
        else if (lcase.startsWith("panorama\\"))
            return 10;
        else if (lcase.startsWith("picture\\"))
            return 11;
        else if (lcase.startsWith("sound\\"))
            return 12;
        else if (lcase.startsWith("system\\"))
            return 13;
        else if (lcase.startsWith("title\\"))
            return 14;
        else if (lcase.endsWith(".dll"))
            return 15;
        else
            throw new GameImportException("Could not detect resource type from: " + fname);
    }

    @Transactional(Transactional.TxType.MANDATORY)
    private @NotNull ZBlob createZblob(byte[] buffer, String hash)
    {
        byte[] uncompressed = buffer;
        byte[] compressed = gzipByteArray(buffer);
        int ratio = compressed.length * 100 / uncompressed.length;

        ZBlob result = new ZBlob();
        result.hashdeep = hash;
        if (ratio < 90)
        {
            logger.infof("Compressed %07d bytes to %07d bytes (%03d %%)",uncompressed.length,compressed.length,ratio);
            result.blob = compressed;
        }
        else
            result.blob = uncompressed;
        result.dateadded = new Date();
        result.persist();
        logger.info("Created ZBlob: " + hash);
        return result;
    }

    private byte[] gzipByteArray(byte[] inbuffer) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(baos, 1024, true);
            gzip.write(inbuffer, 0, inbuffer.length);
            gzip.flush();
            return baos.toByteArray();
        }
        catch (IOException e)
        {
            logger.warnf(e,"Failed to gzip %d bytes.",inbuffer.length);
            return inbuffer;
        }
    }

    private ZBlob testForZblob(String hash)
    {
        Optional<ZBlob> zBlob = ZBlob.find("hashdeep = ?1", hash).firstResultOptional();
        return zBlob.orElse(null);
    }
}
