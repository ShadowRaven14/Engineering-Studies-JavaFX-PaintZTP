package paintztp.FilterAlgorithms;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class EdgeDetectorAlgorithm {
    public static BufferedImage detect(BufferedImage imageOrginal, int window) {

        int[][] filter1 = {
            { -1,  0,  1 },
            { -2,  0,  2 },
            { -1,  0,  1 }
        };

        int[][] filter2 = {
            {  1,  2,  1 },
            {  0,  0,  0 },
            { -1, -2, -1 }
        };

        BufferedImage edgeImage = new BufferedImage(
                imageOrginal.getWidth(),
                imageOrginal.getHeight(),
                imageOrginal.getType());

        int width = edgeImage.getWidth();
        int height = edgeImage.getHeight();
        int rgb;
        List<Integer> pixels = new ArrayList<>();


        for (int column = 1; column < height - 1; column++) {
            for (int row = 1; row < width - 1; row++) {

                // get 3-by-3 array of colors in neighborhood
                //int[][] gray = new int[3][3];
                for (int ji = -window; ji < window; ji++) {
                    for (int jj = -window; jj < window; jj++) {
                        if (column + ji >= 0 && column + ji < width) {
                            if (row + jj >= 0 && row + jj < height) {
                                //gray[i][j] = (int) Luminance.intensity(imageOrginal.get(x-1+i, y-1+j));
                                rgb = imageOrginal.getRGB(column + ji, row + jj);
                                //gray[ji][jj] = (new Color(rgb).getRGB());
                                pixels.add(new Color(rgb).getRGB());
                            }
                        }
                    }
                }

                // apply filter
                int gray1 = 0, gray2 = 0, it = 0;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        pixels.get(it);
                        gray1 += pixels.get(it) * filter1[i][j];
                        gray2 += pixels.get(it) * filter2[i][j];
                        it++;
                    }
                }
                int magnitude = 255 - truncate((int) Math.sqrt(gray1*gray1 + gray2*gray2));
                edgeImage.setRGB(row, row, new Color(magnitude, magnitude, magnitude).getRGB());
            }
        }
        System.out.println("EdgeDetector Done");
        return edgeImage;
    }
    
    public static int truncate(int a) {
        if      (a <   0) return 0;
        else if (a > 255) return 255;
        else              return a;
    }
}
