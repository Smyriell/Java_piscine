package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageReader {

    public static char[][] createArrBMP(String pathToBMP, char white, char black) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(new FileInputStream(pathToBMP));
        } catch (IOException e) {
            printError(e.getMessage());
        }

        char arrImg[][] = new char[image.getWidth()][image.getHeight()];

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int color = image.getRGB(x, y);
                if (color == Color.BLACK.getRGB()) {
                    arrImg[x][y] = black;
                } else
                    arrImg[x][y] = white;
            }
        }

        return arrImg;
    }

    public static void printError(String message) {
        System.err.println(message);
        System.exit(1);
    }
}
