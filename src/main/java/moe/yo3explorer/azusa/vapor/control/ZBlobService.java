package moe.yo3explorer.azusa.vapor.control;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import moe.yo3explorer.azusa.vapor.entity.ZBlob;
import org.jboss.logging.Logger;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.zip.GZIPOutputStream;

@Singleton
public class ZBlobService
{
    @Inject
    Logger logger;

    public ZBlob findZBlobById(long id)
    {
        ZBlob result = ZBlob.findById(id);
        return result;
    }

    @NotNull
    public ZBlob findZBlobByContent(byte[] buffer) {
        Hashdeep hashdeep = new Hashdeep();
        hashdeep.engineUpdate(buffer,0,buffer.length);
        String hash = hashdeep.toString();
        ZBlob zblob = findZBlobByHash(hash);

        if (zblob == null)
            zblob = createZblob(buffer,hash);

        return zblob;
    }

    @Transactional(Transactional.TxType.MANDATORY)
    private  @NotNull ZBlob createZblob(byte[] buffer, String hash)
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
        result.newlyCreated = true;
        result.persist();
        logger.info("Created ZBlob: " + hash);
        return result;
    }

    public ZBlob findZBlobByHash(String hash)
    {
        Optional<ZBlob> zBlob = ZBlob.find("hashdeep = ?1", hash).firstResultOptional();
        return zBlob.orElse(null);
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


}
