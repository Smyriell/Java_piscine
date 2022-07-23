package edu.school21.printer.app;

import edu.school21.printer.logic.ImageReader;

public class Program {
    public static final int NUMB_OF_ARGS = 3;

    public static void argsValidation (String[] args) {
        if (args.length != NUMB_OF_ARGS) {
            printError("There should be three arguments. To see instruction read README.txt file");
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
        try {
            argsValidation(args);

            char background = args[0].charAt(0);
            char sign = args[1].charAt(0);
            String pathToBMP = args[2];

            char[][] arrBMP = ImageReader.createArrBMP(pathToBMP, background, sign);

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
