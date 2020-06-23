package moe.yo3explorer.azusa.patchouli.entity;

import io.smallrye.mutiny.tuples.Tuple2;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

public class SmartInfo
{
    private static final boolean CONSIDER_BACKBLAZE_HDD_STATS = false;

    public final String model;
    public final String wwn;
    public final Integer reallocatedSectorCt;
    public final Long reportedUncorrect;
    public final Long commandTimeout;
    public final Integer currentPendingSector;
    public final Integer offlineUncorrectable;
    public final long capacity;

    public SmartInfo(@NotNull Tuple2<SmartDisk,SmartValuesEntity> data)
    {
        model = data.getItem1().model;
        wwn = data.getItem1().wwn;
        reallocatedSectorCt = data.getItem2().get_5();
        reportedUncorrect = data.getItem2().get_187();
        commandTimeout = data.getItem2().get_188();
        currentPendingSector = data.getItem2().get_197();
        offlineUncorrectable = data.getItem2().get_198();
        capacity = data.getItem1().capacity.divide(new BigInteger("1000")).longValue() / 1000 / 1000;
    }

    public long getHealthPenalty()
    {
        long result = 0;
        if (reallocatedSectorCt != null)
            result += reallocatedSectorCt;
        if (currentPendingSector != null)
            result += currentPendingSector;
        if (offlineUncorrectable != null)
            result += offlineUncorrectable;
        if (CONSIDER_BACKBLAZE_HDD_STATS)
        {
            if (reportedUncorrect != null)
                result += reportedUncorrect;
            if (commandTimeout != null)
                result += commandTimeout;
        }
        return result;
    }

    public String getColor()
    {
        return getHealthPenalty() > 0 ? "red" : "#a0a0a0";
    }
}
