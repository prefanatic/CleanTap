package io.github.prefanatic.cleantap.util;

public class TextFormatUtil {
    public static String LongToFormattedString(long l) {
        double res;
        if (l > 1000) {
            return String.format("%.2fk", (float) l / 1000);
        }

        return String.format("%d", l);
    }
}
