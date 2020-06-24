package moe.yo3explorer.azusa.js3db.entity;

import io.reactivex.annotations.NonNull;
import moe.yo3explorer.azusa.js3db.control.VersionComparator;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

public class Version implements Comparable<Version>
{
    public Version(String fullName) {
        this.fullName = fullName;
        this.saneName = sanitizeName(fullName);
        this.numbers = makeComparableVersion(this.saneName);
    }

    @NotNull
    private String sanitizeName(String insaneName)
    {
        insaneName = insaneName.replace('-','.');

        while(containsWeirdCharacters(insaneName))
            insaneName = insaneName.substring(0,insaneName.length() - 1);
        if (insaneName.endsWith("."))
            insaneName = insaneName.substring(0,insaneName.length() - 1);

        return insaneName;
    }

    private boolean containsWeirdCharacters(@NotNull String insaneName)
    {
        for (int i = 0; i < insaneName.length(); i++)
        {
            char c = insaneName.charAt(i);
            if (c == '.')
                continue;
            else if (Character.isDigit(c))
                continue;
            else
                return true;
        }
        return false;
    }

    @NotNull
    private int[] makeComparableVersion(@NotNull String saneName)
    {
        String[] split = saneName.split("\\.");
        int[] result = new int[split.length];
        for (int i = 0; i < split.length; i++)
        {
            result[i] = Integer.parseInt(split[i]);
        }
        return result;
    }

    private String fullName;
    private String saneName;
    private int[] numbers;

    public int[] getNumbers() {
        return Arrays.copyOf(numbers,numbers.length);
    }

    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Version version = (Version) o;
        return Objects.equals(fullName, version.fullName) &&
                Objects.equals(saneName, version.saneName) &&
                Arrays.equals(numbers, version.numbers);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(fullName, saneName);
        result = 31 * result + Arrays.hashCode(numbers);
        return result;
    }

    @Override
    public int compareTo(@NonNull Version o) {
        return new VersionComparator().compare(this,o);
    }
}
