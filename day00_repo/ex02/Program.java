package ex02;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        if (userInput.hasNextInt()) {
            int numb = 0;
            int coffeeReq = 0;

            while (true) {
                if (!userInput.hasNextInt()) {
                    System.err.println("Error!\nOnly numbers are allowed");
                }
                numb = userInput.nextInt();
                if (numb == 42) {
                    break;
                } else if (numb < 2) {
                    printError();
                } else if (isSumPrimeDigit(numb)) {
                    ++coffeeReq;
                }
            }
            System.out.println("Count of coffee-request - " + coffeeReq);
        } else {
            System.err.println("Error!\nOnly numbers are allowed");
        }
    }

    public static void printError() {
        System.err.println("theIllegalArgument");
        System.exit(-1);
    }

    public static boolean isSumPrimeDigit(int number) {
        int sum = 0;
        while (number > 0) {
            sum += number % 10;
            number /= 10;
        }
        for (int i = 2; i * i <= sum; i++) {
            if (sum % i == 0) {
                return false;
            }
        }
        return true;
    }
}
