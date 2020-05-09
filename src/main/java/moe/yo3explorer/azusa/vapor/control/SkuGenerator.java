package moe.yo3explorer.azusa.vapor.control;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Random;

@Singleton
public class SkuGenerator {
    @Inject
    Random random;

    private final char[] CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public String generateSku()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++)
            sb.append(CHARS[random.nextInt(CHARS.length)]);

        sb.append(String.format("%05d",random.nextInt(99999)));
        return sb.toString();
    }
}
