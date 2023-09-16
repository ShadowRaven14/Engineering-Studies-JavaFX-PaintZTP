package paintztp.ComputerGraphics;
import java.util.Arrays;

public class RgbCmykConverter {
    public static void main(String[] args) {
        int[] cmyk = rgbToCmyk(
            Integer.parseInt(args[0]),
            Integer.parseInt(args[1]),
            Integer.parseInt(args[2])
        );

        System.out.println(Arrays.toString(cmyk));
    }

    private static int[] rgbToCmyk(int r, int g, int b) {
        double percentageR = r / 255.0 * 100;
        double percentageG = g / 255.0 * 100;
        double percentageB = b / 255.0 * 100;

        double k = 100 - Math.max(Math.max(percentageR, percentageG), percentageB);

        if (k == 100) {
            return new int[]{ 0, 0, 0, 100 };
        }

        int c = (int)((100 - percentageR - k) / (100 - k) * 100);
        int m = (int)((100 - percentageG - k) / (100 - k) * 100);
        int y = (int)((100 - percentageB - k) / (100 - k) * 100);

        return new int[]{ c, m, y, (int)k };
    }
}
