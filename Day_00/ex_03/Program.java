package ex_03;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String userData = scanner.nextLine();
        int weekNumb = 1;
        long allWeeksGrades = 0;

        while (!userData.equals("42") && weekNumb != 19) {
            if (userData.equals("Week " + weekNumb)) {
                if (!scanner.hasNextInt())
                    PrintError("Error!\nGrades should be as numbers");
                allWeeksGrades += FindMinGrade(scanner, weekNumb);
                weekNumb++;
                scanner.nextLine();
                userData = scanner.nextLine();
            }
            else
                 PrintError("theIllegalArgument");
        }
        PrintStatisticOfStudentProgres(allWeeksGrades, weekNumb);
    }

    public static void PrintError(String message)
    {
        System.err.println(message);
        System.exit(-1);
    }

    public static void CheckGradeRange(int grade)
    {
        if (grade < 1 || grade > 9)
            PrintError("Each test can be graded between 1 and 9");
    }

    public static int FindMinGrade(Scanner scanner, int weekNumb)
    {
        int grades;
        int minGrade;
        int gradeNumb = 5;

        grades = scanner.nextInt();
        CheckGradeRange(grades);
        minGrade = grades;
        for (int i = 1; i < gradeNumb; ++i) {
            if (!scanner.hasNextInt())
                PrintError("Error!\nThere should be five grades as numbers from 1 to 9");
            grades = scanner.nextInt();
            CheckGradeRange(grades);
            if (grades < minGrade)
                minGrade = grades;
        }
        for (int i = 1; i < weekNumb; ++i)
            minGrade *= 10;
        return minGrade;
    }

    public static void PrintStatisticOfStudentProgres(long sumGrades, int sumWeek)
    {
        long rest;

        if (sumGrades == 0)
            return;
        for (int i = 1; i < sumWeek; ++i) {
            rest = sumGrades % 10;
            sumGrades /= 10;
            System.out.print("Week " + i + " ");
            while (rest-- > 0)
                System.out.print("=");
            System.out.println(">");
        }
    }
}

