import quadtree.ImageMeasure;
import quadtree.QuadTree;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Run {
    public final static String filePath = System.getProperty("user.dir") + "\\test files";
    public static String[] tiffFiles = {"Peppers.tiff", "PeppersRGB.tiff"};
    public static String[] bmpFiles = {"Peppers.bmp", "PeppersRGB.bmp"};
    public static String currentFile = "";

    public static void main(String[] args) {
        for (int i = 0; i < tiffFiles.length; i++) {
            try {
                BufferedImage image = ImageIO.read(new File(filePath + "\\" + tiffFiles[i]));
                currentFile = tiffFiles[i];
                compressTiff(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < bmpFiles.length; i++) {
            try {
                BufferedImage image = ImageIO.read(new File(filePath + "\\" + bmpFiles[i]));
                currentFile = bmpFiles[i];
                compressBmp(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeTiff(BufferedImage img, String fileName) {
        try {
            ImageIO.write(img, "tiff", new File(filePath + "\\compressed\\" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeBmp(BufferedImage img, String fileName) {
        try {
            ImageIO.write(img, "bmp", new File(filePath + "\\compressed\\" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage compress(BufferedImage image, double threshold) {
        Color[][] colors = makeColorArray(image);
        int width = image.getWidth();
        int height = image.getHeight();
        QuadTree quadTree = new QuadTree(colors, new ImageMeasure(), threshold);
        // QuadTree quadTree = new QuadTree(colors, new ImageMeasure(), threshold, new Color(0, 0, 0));
        BufferedImage outImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                outImage.setRGB(i, j, quadTree.get(i, j).getRGB());
            }
        }
        return outImage;
    }

    public static void compressTiff(BufferedImage image) {
        for (int i = 1; i < 20; i++) {
            BufferedImage outImage = compress(image, i / 300.0);
            writeTiff(outImage, i + currentFile);
        }
    }

    public static void compressBmp(BufferedImage image) {
        for (int i = 1; i < 20; i++) {
            BufferedImage outImage = compress(image, i / 300.0);
            writeBmp(outImage, i + currentFile);
        }
    }

    public static Color[][] makeColorArray(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        Color colors[][] = new Color[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                colors[i][j] = new Color(image.getRGB(i, j));
            }
        }

        return colors;
    }
}
