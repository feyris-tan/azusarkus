package moe.yo3explorer.azusa.vapor.entity;

import moe.yo3explorer.azusa.vapor.control.GameImportException;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class RpgRtIni
{
    private String gameTitle;
    private Byte mapeditmode;
    private Byte mapeditzoom;
    private Boolean fullpackageflag;
    private String knownversion;

    private RpgRtIni() {}

    public static @NotNull RpgRtIni parse(byte[] buffer)
    {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bais));
            String magicNumber = bufferedReader.readLine();
            if (!magicNumber.equals("[RPG_RT]"))
                throw new GameImportException("The main category in RPG_RT.ini is invalid!");

            RpgRtIni rpgRtIni = new RpgRtIni();
            while (true)
            {
                String line = bufferedReader.readLine();
                if (line == null)
                    break;
                if (line.contains("=")) {
                    String[] split = line.split("=");
                    split[0] = split[0].toLowerCase();
                    switch (split[0])
                    {
                        case "gametitle":
                            rpgRtIni.gameTitle = split[1];
                            break;
                        case "mapeditmode":
                            rpgRtIni.mapeditmode = Byte.parseByte(split[1]);
                            break;
                        case "mapeditzoom":
                            rpgRtIni.mapeditzoom = Byte.parseByte(split[1]);
                            break;
                        case "fullpackageflag":
                            rpgRtIni.fullpackageflag = Long.parseLong(split[1]) != 0;
                            break;
                        case "knownversion":
                            rpgRtIni.knownversion = split[1];
                            break;
                        default:
                            throw new GameImportException("Don't know what RPG_RT.ini entry means: " + split[0]);
                    }
                }
            }
            return rpgRtIni;

        } catch (IOException e) {
            throw new GameImportException(e);
        }
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public Byte getMapeditmode() {
        return mapeditmode;
    }

    public Byte getMapeditzoom() {
        return mapeditzoom;
    }

    public Boolean getFullpackageflag() {
        return fullpackageflag;
    }

    public String getKnownversion() {
        return knownversion;
    }
}
