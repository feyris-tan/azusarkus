package moe.yo3explorer.azusa.vapor.control;

import moe.yo3explorer.azusa.vapor.entity.Game;
import org.jetbrains.annotations.NotNull;

public class GameResourceMissingException extends RuntimeException
{
    public GameResourceMissingException(String sku, String resCat, String resName)
    {
        super(String.format("%d\\%d\\%d",sku,resCat,resName));
    }

    public GameResourceMissingException(@NotNull Game game, String resCat, String resName)
    {
        this(game.vapor_sku,resCat,resName);
    }
}
