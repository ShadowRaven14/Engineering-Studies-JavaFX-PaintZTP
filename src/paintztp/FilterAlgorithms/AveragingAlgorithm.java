package paintztp.FilterAlgorithms;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AveragingAlgorithm {
    public static BufferedImage average(BufferedImage imageOrginal, int window) {

        BufferedImage averageImage = new BufferedImage(
                imageOrginal.getWidth(),
                imageOrginal.getHeight(),
                imageOrginal.getType());

        int width = averageImage.getWidth();
        int height = averageImage.getHeight();
        Color color;

        for(int column=1; column < width-1; column++)
            for(int row=1; row<height-1; row++)
            {
                int R = 0; int G = 0; int B = 0;
                for (int ji = -window/2; ji <= window/2; ji++) {
                    for (int jj = -window/2; jj <= window/2; jj++) {
                        if (column + ji >= 0 && column + ji < width) {
                            if (row + jj >= 0 && row + jj < height) {
                                color = new Color(imageOrginal.getRGB(column + ji, row + jj));
                                R = R + color.getRed();
                                G = G + color.getGreen();
                                B = B + color.getBlue();
                            }
                        }
                    }
                }
                R = R / (window*window);
                G = G / (window*window);
                B = B / (window*window);
                averageImage.setRGB(column, row, new Color(R, G, B).getRGB());
            }
        
        System.out.println("Average Done");
        return averageImage;
    }
}
