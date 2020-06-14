package moe.yo3explorer.azusa.skyscraper.control;

import moe.yo3explorer.azusa.skyscraper.entity.Satellite;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class SatelliteComparator implements Comparator<Satellite> {
    @Override
    public int compare(@NotNull Satellite o1, @NotNull Satellite o2) {
        double position1 = o1.orbitalposition;
        double position2 = o2.orbitalposition;

        if (o1.cardinaldirection.equals("W"))
            position1 /= -1;

        if (o2.cardinaldirection.equals("W"))
            position2 /= -1;

        return Double.compare(position1,position2) / -1;
    }
}
