package ex_01;

import java.util.Scanner;

public class Program {
    public static void main (String[] args) {
        Scanner userInput = new Scanner(System.in);
        if (userInput.hasNextInt()) {
            int number = userInput.nextInt();

            if (number < 2)
                PrintError();
            else {
                boolean result = true;
                int i;
                for (i = 2; i * i <= number; i++) {
                    if (number % i == 0) {
                        result = false;
                        break;
                    }
                }
                System.out.println(result + " " + --i);
            }
        }
        else
            System.err.println("Error!\nJust numbers are allowed");
    }

    public static void PrintError()
    {
        System.err.println("theIllegalArgument");
        System.exit(-1);
    }
}

