package moe.yo3explorer.azusa.licensing;

import org.jetbrains.annotations.NotNull;

public class LicensingException extends RuntimeException
{
    public LicensingException(@NotNull LicensingStatus status)
    {
        super(status.getMessage());
    }
}
