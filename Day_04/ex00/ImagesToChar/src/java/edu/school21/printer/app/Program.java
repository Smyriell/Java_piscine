package edu.school21.printer.app;

import edu.school21.printer.logic.*;

import java.io.IOException;

public class Program {
    public static void main(String[] args) throws IOException {
        if (args.length != 3)
            printError("There should be three arguments");

        char background = args[0].charAt(0);
        char sign = args[1].charAt(0);
        String pathToBMP = args[2];

        char[][] arrBMP = ImageReader.readBMP(pathToBMP, background, sign);
        for (int x = 0; x < arrBMP.length; x++) {
            for (int y = 0; y < arrBMP[x].length; y++)
                System.out.print(arrBMP[y][x]);
             System.out.println();
        }
    }

    public static void printError(String message) {
        System.err.println(message);
        System.exit(-1);
    }

}
