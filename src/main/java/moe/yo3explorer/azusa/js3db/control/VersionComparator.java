package moe.yo3explorer.azusa.js3db.control;

import moe.yo3explorer.azusa.js3db.entity.Version;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class VersionComparator implements Comparator<Version> {
    @Override
    public int compare(@NotNull Version o1, @NotNull Version o2) {
        int[] l = o1.getNumbers();
        int[] r = o2.getNumbers();
        if (l.length != r.length)
        {
            int newSize = Math.max(l.length,r.length);
            int[] lNew = new int[newSize];
            int[] rNew = new int[newSize];
            copy(l,lNew);
            copy(r,rNew);
            l = lNew;
            r = rNew;
        }

        for (int i = 0; i < l.length; i++)
        {
            int compare = Integer.compare(l[i], r[i]);
            if (compare != 0)
                return compare;
        }
        return 0;
    }

    private static void copy(@NotNull int[] inputs, int[] outputs)
    {
        for (int i = 0; i < inputs.length; i++)
        {
            outputs[i] = inputs[i];
        }
    }
}
