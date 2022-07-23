package edu.school21.printer.app;

import edu.school21.printer.logic.ImageReader;

import java.io.IOException;


public class Program {
    public static final int NUMB_OF_ARGS = 2;
    public static final String PATH_TO_BMP = "src/resources/image.bmp";

    public static void argsValidation (String[] args) {
        if (args.length != NUMB_OF_ARGS) {
            printError("There should be two arguments. To see instruction read README.txt file");
        }

        if (args[0].length() > 1 || args[1].length() > 1) {
            printError("First and second arguments should be consist of one symbol. " +
                    "To see instruction read README.txt file ");
        }
    }

    public static void printError(String message) {
        System.err.println(message);
        System.exit(1);
    }

    public static void main(String[] args) {
        argsValidation(args);

        char background = args[0].charAt(0);
        char sign = args[1].charAt(0);

        try {
            char[][] arrBMP = ImageReader.createArrBMP(PATH_TO_BMP, background, sign);

            for (int x = 0; x < arrBMP.length; x++) {
                for (int y = 0; y < arrBMP[x].length; y++) {
                    System.out.print(arrBMP[y][x]);
                }
                System.out.println();
            }
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            printError(e.getMessage());
        }
    }
}
