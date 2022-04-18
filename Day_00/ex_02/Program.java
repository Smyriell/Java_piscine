package ex_02;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        if (userInput.hasNextInt()) {
            int numb = 0;
            int coffeeReq = 0;

            while (true) {
                numb = userInput.nextInt();
                if (numb == 42)
                        break;
                else if (numb < 2)
                    PrintError();
                else if (isSumPrimeDigit(numb) == 1)
                   ++coffeeReq;
            }
            System.out.println("Count of coffee-request - " + coffeeReq);
        }
        else
            System.err.println("Error!\nJust numbers are allowed");
    }

    public static void PrintError()
    {
        System.err.println("theIllegalArgument");
        System.exit(-1);
    }

    public static int isSumPrimeDigit(int number)
    {
        int sum = 0;
        while (number > 0) {
            sum += number % 10;
            number /= 10;
        }
        for (int i = 2; i * i <= sum; i++) {
            if (sum % i == 0)
                return 0;
            }
        return 1;
    }
}
