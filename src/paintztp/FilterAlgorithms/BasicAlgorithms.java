package paintztp.FilterAlgorithms;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BasicAlgorithms {
    
     public static BufferedImage add(BufferedImage imageOrginal, int size) {
        BufferedImage addImage = new BufferedImage(
                imageOrginal.getWidth(),
                imageOrginal.getHeight(),
                imageOrginal.getType());

        int width = addImage.getWidth();
        int height = addImage.getHeight();
        Color color; int rgb; int R; int G; int B;

        for(int column=0; column < width; column++)
        {
            for(int row=0; row<height; row++)
            {
                color = new Color(imageOrginal.getRGB(column, row));
                R = color.getRed() + size;
                G = color.getGreen() + size;
                B = color.getBlue() + size;
                if(R>255) R=255;
                if(G>255) G=255;
                if(B>255) B=255;
                
                addImage.setRGB(column, row, new Color(R, G, B).getRGB());
                System.out.println(new Color(R, G, B).getRGB());
            }
        }
            
        System.out.println("Basic Done");
        return addImage;
    }
     
     public static BufferedImage subtract(BufferedImage imageOrginal, int size) {
        BufferedImage subtractImage = new BufferedImage(
                imageOrginal.getWidth(),
                imageOrginal.getHeight(),
                imageOrginal.getType());

        int width = subtractImage.getWidth();
        int height = subtractImage.getHeight();
        Color color; int rgb; int R; int G; int B;

        for(int column=0; column < width; column++)
        {
            for(int row=0; row<height; row++)
            {
                color = new Color(imageOrginal.getRGB(column, row));
                R = color.getRed() - size;
                G = color.getGreen() - size;
                B = color.getBlue() - size;
                if(R<0) R=0;
                if(G<0) G=0;
                if(B<0) B=0;
                
                subtractImage.setRGB(column, row, new Color(R, G, B).getRGB());
                System.out.println(new Color(R, G, B).getRGB());
            }
        }
            
        System.out.println("Basic Done");
        return subtractImage;
    }
     
     public static BufferedImage multiply(BufferedImage imageOrginal, int size) {
        BufferedImage multiplyImage = new BufferedImage(
                imageOrginal.getWidth(),
                imageOrginal.getHeight(),
                imageOrginal.getType());

        int width = multiplyImage.getWidth();
        int height = multiplyImage.getHeight();
        Color color; int rgb; int R; int G; int B;

        for(int column=0; column < width; column++)
        {
            for(int row=0; row<height; row++)
            {
                color = new Color(imageOrginal.getRGB(column, row));
                R = color.getRed() * size;
                G = color.getGreen() * size;
                B = color.getBlue() * size;
                if(R>255) R=255; if(R<0) R=0;
                if(G>255) G=255; if(G<0) G=0;
                if(B>255) B=255; if(B<0) B=0;
                
                multiplyImage.setRGB(column, row, new Color(R, G, B).getRGB());
            }
        }
            
        System.out.println("Basic Done");
        return multiplyImage;
    }
     
     public static BufferedImage divide(BufferedImage imageOrginal, int size) {
        BufferedImage divideImage = new BufferedImage(
                imageOrginal.getWidth(),
                imageOrginal.getHeight(),
                imageOrginal.getType());

        int width = divideImage.getWidth();
        int height = divideImage.getHeight();
        Color color; int rgb; int R; int G; int B;

        for(int column=0; column < width; column++)
        {
            for(int row=0; row<height; row++)
            {
                color = new Color(imageOrginal.getRGB(column, row));
                R = color.getRed() / size;
                G = color.getGreen() / size;
                B = color.getBlue() / size;
                if(R>255) R=255; if(R<0) R=0;
                if(G>255) G=255; if(G<0) G=0;
                if(B>255) B=255; if(B<0) B=0;
                
                divideImage.setRGB(column, row, new Color(R, G, B).getRGB());
            }
        }
            
        System.out.println("Basic Done");
        return divideImage;
    }
     
     public static BufferedImage brightnessUp(BufferedImage imageOrginal, int size) {
        BufferedImage divideImage = new BufferedImage(
                imageOrginal.getWidth(),
                imageOrginal.getHeight(),
                imageOrginal.getType());

        int width = divideImage.getWidth();
        int height = divideImage.getHeight();
        Color color; int rgb; int R; int G; int B;

        for(int column=0; column < width; column++)
        {
            for(int row=0; row<height; row++)
            {
                color = new Color(imageOrginal.getRGB(column, row));
                R = color.getRed() * size;
                G = color.getGreen() * size;
                B = color.getBlue() * size;
                if(R>255) R=255; if(R<0) R=0;
                if(G>255) G=255; if(G<0) G=0;
                if(B>255) B=255; if(B<0) B=0;
                
                divideImage.setRGB(column, row, new Color(R, G, B).getRGB());
            }
        }
            
        System.out.println("Basic Done");
        return divideImage;
    }
     
     public static BufferedImage brightnessDown(BufferedImage imageOrginal, int size) {
        BufferedImage divideImage = new BufferedImage(
                imageOrginal.getWidth(),
                imageOrginal.getHeight(),
                imageOrginal.getType());

        int width = divideImage.getWidth();
        int height = divideImage.getHeight();
        Color color; int rgb; int R; int G; int B;

        for(int column=0; column < width; column++)
        {
            for(int row=0; row<height; row++)
            {
                color = new Color(imageOrginal.getRGB(column, row));
                R = color.getRed() / size;
                G = color.getGreen() / size;
                B = color.getBlue() / size;
                if(R>255) R=255; if(R<0) R=0;
                if(G>255) G=255; if(G<0) G=0;
                if(B>255) B=255; if(B<0) B=0;
                
                divideImage.setRGB(column, row, new Color(R, G, B).getRGB());
            }
        }
            
        System.out.println("Basic Done");
        return divideImage;
    }
     
     public static BufferedImage grayscale1(BufferedImage imageOrginal) {
        BufferedImage grayscaleImage = new BufferedImage(
                imageOrginal.getWidth(),
                imageOrginal.getHeight(),
                imageOrginal.getType());

        int width = grayscaleImage.getWidth();
        int height = grayscaleImage.getHeight();
        Color color; int rgb; int R; int G; int B;
        int gray; int grayscale;

        for(int column=0; column < width; column++)
        {
            for(int row=0; row<height; row++)
            {
                color = new Color(imageOrginal.getRGB(column, row));
                R = color.getRed() / 3;
                G = color.getGreen() / 3;
                B = color.getBlue() / 3;
                gray = R + G + B;
                if(R>255) R=255; if(R<0) R=0;
                if(G>255) G=255; if(G<0) G=0;
                if(B>255) B=255; if(B<0) B=0;
                grayscale = (gray << 16) + (gray << 8) + gray; 
                grayscaleImage.setRGB(column, row, grayscale);
            }
        }
            
        System.out.println("Basic Done");
        return grayscaleImage;
    }
     
     public static BufferedImage grayscale2(BufferedImage imageOrginal) {
        BufferedImage grayscaleImage = new BufferedImage(
                imageOrginal.getWidth(),
                imageOrginal.getHeight(),
                imageOrginal.getType());

        int width = grayscaleImage.getWidth();
        int height = grayscaleImage.getHeight();
        Color color; int rgb; int R; int G; int B;
        int gray; int grayscale;

        for(int column=0; column < width; column++)
        {
            for(int row=0; row<height; row++)
            {
                color = new Color(imageOrginal.getRGB(column, row));
                R = (color.getRed() * 30) / 100;
                G = (color.getGreen() * 59) / 100;
                B = (color.getBlue() * 11) / 100;
                gray = R + G + B;
                if(R>255) R=255; if(R<0) R=0;
                if(G>255) G=255; if(G<0) G=0;
                if(B>255) B=255; if(B<0) B=0;
                grayscale = (gray << 16) + (gray << 8) + gray; 
                grayscaleImage.setRGB(column, row, grayscale);
            }
        }
            
        System.out.println("Basic Done");
        return grayscaleImage;
    }
}
