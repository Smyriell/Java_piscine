package ex03;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputData = scanner.nextLine();
        int weekNumb = 1;
        long allWeeksGrades = 0;

        while (!inputData.equals("42") && weekNumb != 19) {
            if (inputData.equals("Week " + weekNumb)) {
                if (!scanner.hasNextInt()) {
                    printError("Error!\nGrades should be as numbers");
                }
                allWeeksGrades += findMinGrade(scanner, weekNumb);
                weekNumb++;
                scanner.nextLine();
                inputData = scanner.nextLine();
            } else {
                printError("theIllegalArgument");
            }
        }
        printStatisticOfStudentProgress(allWeeksGrades, weekNumb);
    }

    public static void printError(String message) {
        System.err.println(message);
        System.exit(-1);
    }

    public static void checkGradeRange(int grade) {
        if (grade < 1 || grade > 9) {
            printError("Each test can be graded between 1 and 9");
        }
    }

    public static int findMinGrade(Scanner scanner, int weekNumb) {
        int grades;
        int minGrade;
        int gradeNumb = 5;

        grades = scanner.nextInt();
        checkGradeRange(grades);
        minGrade = grades;
        for (int i = 1; i < gradeNumb; ++i) {
            if (!scanner.hasNextInt()) {
                printError("Error!\nThere should be five grades as numbers from 1 to 9");
            }
            grades = scanner.nextInt();
            checkGradeRange(grades);
            if (grades < minGrade) {
                minGrade = grades;
            }
        }
        for (int i = 1; i < weekNumb; ++i) {
            minGrade *= 10;
        }
        return minGrade;
    }

    public static void printStatisticOfStudentProgress(long sumGrades, int sumWeek) {
        long rest;

        if (sumGrades == 0) {
            return;
        }
        for (int i = 1; i < sumWeek; ++i) {
            rest = sumGrades % 10;
            sumGrades /= 10;
            System.out.print("Week " + i + " ");
            while (rest-- > 0) {
                System.out.print("=");
            }
            System.out.println(">");
        }
    }
}
