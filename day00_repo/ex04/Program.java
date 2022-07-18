package ex04;

import java.util.Scanner;



public class Program {

    private static final int MAX_CODE_VALUE = 65535;
    private static final int NUMB_OF_POP_CHAR = 10;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();

        checkUserInput(userInput);

        char[] charArr = userInput.toCharArray();
        int[] intArr = new int[MAX_CODE_VALUE];

        for (int i = 0; i < userInput.length(); i++) {
            intArr[charArr[i]]++;
        }

        int[][] popChar = new int[2][NUMB_OF_POP_CHAR];
        int maxNumb;
        int raw = 0;

        while (raw < NUMB_OF_POP_CHAR) {
            maxNumb = 0;
            for (int i = 0; i < MAX_CODE_VALUE; i++) {
                if (intArr[i] == 999) {
                    printError("The maximum number of character occurrences is 999");
                }
                if (intArr[i] > maxNumb) {
                    maxNumb = intArr[i];
                    popChar[0][raw] = i;
                }
            }
            popChar[1][raw] = maxNumb;
            intArr[popChar[0][raw]] = 0;
            raw++;
        }
        printResult(popChar);
    }

    public static void checkUserInput(String input) {
        if (input.equals("")) {
            printError("Empty input");
        }
    }

    public static int scale(int line, int max, int current) {
        int scaled;

        scaled = (int) ((float) line / (float) max * (float) current);
        return scaled;
    }

    public static void printResult(int[][] popChar) {
        int line = NUMB_OF_POP_CHAR + 2;
        int raw = 0;

        for (int i = 0; i < NUMB_OF_POP_CHAR; i++) {
            if (popChar[1][i] > 0) {
                raw++;
            }
        }
        if (popChar[1][0] < 10) {
            line = popChar[1][0] + 2;
        }
        for (int i = 0; i < line; i++) {
            for (int j = 0; j < raw; j++) {
                if (line - i == scale(line - 2, popChar[1][0], (popChar[1][j]) + 2)) {
                    System.out.print(popChar[1][j] + "\t");
                } else if (line - i == 1) {
                    System.out.print((char) popChar[0][j] + "\t");
                } else if ((line - 1) - i <= scale(line - 2, popChar[1][0], popChar[1][j])) {
                    System.out.print("#" + "\t");
                }
            }
            System.out.println();
        }
    }

    public static void printError(String message) {
        System.err.println(message);
        System.exit(-1);
    }
}
