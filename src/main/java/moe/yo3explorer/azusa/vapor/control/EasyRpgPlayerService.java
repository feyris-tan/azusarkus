package moe.yo3explorer.azusa.vapor.control;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.GZIPInputStream;

@Singleton
public class EasyRpgPlayerService
{
    private static final String player_js_tar_gz_url = "https://ci.easyrpg.org/job/player-js/lastSuccessfulBuild/artifact/player-js.tar.gz";
    private static final String player_js_tar_gz = "player-js.tar.gz";
    private static final String index_html = "player-js/index.html";
    private static final String index_js = "player-js/index.js";
    private static final String index_wasm = "player-js/index.wasm";

    @Inject
    Logger logger;

    private boolean testForPlayerJsTarGz()
    {
        File fi = new File(player_js_tar_gz);
        return fi.isFile();
    }

    private void downloadPlayerJsTarGz()
    {
        if (testForPlayerJsTarGz())
            return;

        try {
            logger.infof("Downloading: %s",player_js_tar_gz_url);
            InputStream in = new URL(player_js_tar_gz_url).openStream();
            Files.copy(in, Paths.get(player_js_tar_gz), StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException ioe)
        {
            throw new RuntimeException(ioe);
        }
    }

    private void unpackPlayerFiles()
    {
        logger.infof("Unpacking: %s",player_js_tar_gz);
        downloadPlayerJsTarGz();
        try {
            FileInputStream level1 = new FileInputStream(player_js_tar_gz);
            GZIPInputStream level2 = new GZIPInputStream(level1);
            TarArchiveInputStream level3 = new TarArchiveInputStream(level2);
            TarArchiveEntry currentEntry;
            long done = 0;
            while ((currentEntry = level3.getNextTarEntry()) != null)
            {
                if (currentEntry.isDirectory())
                {
                    File file = new File(currentEntry.getName());
                    file.mkdirs();
                    logger.info("Created directory: " + file.getPath());
                    continue;
                }

                if (!currentEntry.isFile())
                    continue;


                if (currentEntry.getSize() > Integer.MAX_VALUE)
                    throw new RuntimeException("An entry in the easyRPG archive was too big.");

                int realLen = (int)currentEntry.getSize();
                int len32 = (int)currentEntry.getSize();
                int offset = 0;
                int blockSize;
                byte[] unpackedEntry = new byte[len32];
                while (len32 > 0) {
                    blockSize = level3.read(unpackedEntry, offset, len32);
                    len32 -= blockSize;
                    offset += blockSize;
                }

                logger.infof("Unpacking: %s (%d bytes)",currentEntry.getName(),currentEntry.getSize());
                FileOutputStream fos = new FileOutputStream(currentEntry.getName());
                fos.write(unpackedEntry,0,realLen);
                fos.flush();
                fos.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] readFully(String filename)
    {
        File file = new File(filename);
        int len32 = (int)file.length();
        byte[] buffer = new byte[len32];

        try {
            FileInputStream fis = new FileInputStream(file);
            fis.read(buffer,0,len32);
            fis.close();
            return buffer;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] getFile(String filename)
    {
        File htmlFile = new File(filename);
        if (!htmlFile.exists()) {
            unpackPlayerFiles();
            return getFile(filename);
        }

        return readFully(filename);
    }

    public byte[] getHtml()
    {
        return getFile(index_html);
    }

    public byte[] getJs()
    {
        return getFile(index_js);
    }

    public byte[] getWasm()
    {
        return getFile(index_wasm);
    }
}
