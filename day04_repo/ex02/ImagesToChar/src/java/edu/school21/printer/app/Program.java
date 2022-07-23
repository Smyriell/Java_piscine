package edu.school21.printer.app;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.diogonunes.jcdp.color.ColoredPrinter;
import edu.school21.printer.logic.Args;
import edu.school21.printer.logic.ImageReader;
import java.util.IllegalFormatException;

public class Program {
    public static final int NUMB_OF_ARGS = 2;
    public static final String PATH_TO_BMP = "src/resources/image.bmp";
    public static final char ARR_BACKGROUND_SYMB = '.';
    public static final char ARR_PRINT_SYMB = '0';
    public static Args param;


    public static void argsValidation (String[] args) {
        if (args.length != NUMB_OF_ARGS) {
            printError("There should be two arguments. To see instruction read README.txt file");
        }

        try {
            param = new Args();
            JCommander.newBuilder().addObject(param).build().parse(args);
        } catch (IllegalArgumentException | ParameterException e) {
            printError("Invalid color name! Avaliable colors:\n" +
                        "BLACK  RED  GREEN  YELLOW  BLUE  MAGENTA  CYAN  WHITE  NONE\n" + e.getMessage());
        }
    }

    public static void printError(String message) {
        System.err.println(message);
        System.exit(1);
    }

    public static void main(String[] args) {
        argsValidation(args);

        try {
            char[][] arrBMP = ImageReader.createArrBMP(PATH_TO_BMP, ARR_BACKGROUND_SYMB, ARR_PRINT_SYMB);

            ColoredPrinter whitePrinter = new ColoredPrinter.Builder(1, false)
                    .background(param.getBackground()).build();

            ColoredPrinter blackPrinter = new ColoredPrinter.Builder(1, false)
                    .background(param.getSign()).build();

            for (int x = 0; x < arrBMP.length; x++) {
                for (int y = 0; y < arrBMP[x].length; y++) {
                    if (arrBMP[y][x] == ARR_BACKGROUND_SYMB) {
                        whitePrinter.print("  ");
                    } else if (arrBMP[y][x] == ARR_PRINT_SYMB) {
                        blackPrinter.print("  ");
                    }
                }
                System.out.println();
            }
        } catch (IllegalFormatException | IndexOutOfBoundsException e) {
            printError(e.getMessage());
        }
    }
}
