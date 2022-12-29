package pl.edu.pw.mini.projektZPOIF.Utils;

public class ColorUtils {
    public static int toRgb(int red, int green, int blue)
    {
        return (red << 16) + (green << 8) + blue;
    }
}
