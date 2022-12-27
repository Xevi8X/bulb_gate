package pl.edu.pw.mini.projektZPOIF.Utils;

public class ColorUtils {
    public static int toRgb(byte red, byte green, byte blue)
    {
        return (red << 16) + (green << 8) + blue;
    }
}
