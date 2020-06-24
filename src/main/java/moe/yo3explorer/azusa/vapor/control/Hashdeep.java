package moe.yo3explorer.azusa.vapor.control;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashdeep extends MessageDigest implements Cloneable
{
    long length;
    MessageDigest md5;
    MessageDigest sha256;

    public Hashdeep()
    {
        super("");
        engineReset();
    }

    protected Hashdeep(String algorithm) {
        super(algorithm);
        engineReset();
    }

    @Override
    protected void engineUpdate(byte input) {
        md5.update(input);
        sha256.update(input);
        length++;
    }

    @Override
    protected void engineUpdate(byte[] input, int offset, int len) {
        md5.update(input,offset,len);
        sha256.update(input,offset,len);
        length += len;
    }

    @Override
    protected byte[] engineDigest() {
        ByteBuffer temp = ByteBuffer.allocate(8 + md5.getDigestLength() + sha256.getDigestLength());
        temp.putLong(length);
        temp.put(md5.digest());
        temp.put(sha256.digest());
        return temp.array();
    }

    @Override
    protected void engineReset() {
        try {
            sha256 = MessageDigest.getInstance("SHA-256");
            md5 = MessageDigest.getInstance("MD5");
            length = 0;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new Error("Broken JVM. SHA256 and/or MD5 not supported.");
        }
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    @Contract("_ -> new")
    private static @NotNull String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    @Override
    public String toString() {
        return bytesToHex(engineDigest());
    }
}
