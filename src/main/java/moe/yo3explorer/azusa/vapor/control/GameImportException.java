package moe.yo3explorer.azusa.vapor.control;

import net.bytebuddy.implementation.bytecode.Throw;

public class GameImportException extends RuntimeException
{
    public GameImportException(String s)
    {
        super(s);
    }

    public GameImportException(Throwable t)
    {
        super(t);
    }
}
