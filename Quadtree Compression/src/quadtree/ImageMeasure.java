package quadtree;

import java.awt.*;

public class ImageMeasure {


    public Color approximate(Color[][] data, int x, int y, int width, int height) {
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;

        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                redSum += data[i][j].getRed();
                greenSum += data[i][j].getGreen();
                blueSum += data[i][j].getBlue();
            }
        }

        int pixelCount = width * height;

        return new Color(redSum / pixelCount, greenSum / pixelCount, blueSum / pixelCount);
    }

    public double measureDetail(Color[][] data, int x, int y, int width, int height) {
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;

        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                redSum += data[i][j].getRed();
                greenSum += data[i][j].getGreen();
                blueSum += data[i][j].getBlue();
            }
        }

        double pixelCount = width * height;

        double redAvg = redSum / pixelCount;
        double greenAvg = greenSum / pixelCount;
        double blueAvg = blueSum / pixelCount;

        redSum = 0;
        greenSum = 0;
        blueSum = 0;

        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                int red = data[i][j].getRed();
                int green = data[i][j].getGreen();
                int blue = data[i][j].getBlue();

                redSum += Math.pow(red - redAvg, 2);
                greenSum += Math.pow(green - greenAvg, 2);
                blueSum += Math.pow(blue - blueAvg, 2);
            }
        }
        return redSum / (pixelCount * 255 * 255) + greenSum / (pixelCount * 255 * 255) + blueSum / (pixelCount * 255 * 255);
    }
}