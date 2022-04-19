package ex_04;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        int i;
        int j = 0;

        char[] inputArr = userInput.toCharArray();
        int[] intArr = new int[65535];

        for (i = 0; i < userInput.length(); i++) {
            intArr[inputArr[i]]++;
        }

        int[][] popChar = new int[2][10];
        int maxNumb;
        int raw = 0;
        while (j++ < 10) {
            maxNumb = 0;
            for (i = 0; i < 65535; i++) {
                if (intArr[i] == 999)
                    PrintError("The maximum number of character occurrences is 999");
                if (intArr[i] > maxNumb) {
                    maxNumb = intArr[i];
                    popChar[0][raw] = i;
                }
            }
            popChar[1][raw] = maxNumb;
            intArr[popChar[0][raw]] = 0;
            raw++;
        }
        PrintResult(popChar);
    }

    public static int Scale(int line, int max, int current) {
        int scaled;

        scaled = (int) ((float) line / (float) max * (float) current);

        return scaled;
    }

    public static void PrintResult(int[][] popChar) {
        int line = 12;
        int raw = 0;

        for (int i = 0; i < 10; i++) {
            if (popChar[1][i] > 0) {
                raw++;
            }
        }
        if (popChar[1][0] < 10)
            line = popChar[1][0] + 2;
        for (int i = 0; i < line; i++) {
            for (int j = 0; j < raw; j++) {
                if (line - i == Scale(line - 2, popChar[1][0], popChar[1][j]) + 2) {
                    System.out.print(popChar[1][j] + "\t");
                }
                else if (line - i == 1)
                    System.out.print((char)popChar[0][j] + "\t");
                else if ((line - 1) - i <= Scale(line - 2, popChar[1][0], popChar[1][j]))
                    System.out.print("#" + "\t");
            }
            System.out.println();
        }
    }

    public static void PrintError(String message)
    {
        System.err.println(message);
        System.exit(-1);
    }
}


