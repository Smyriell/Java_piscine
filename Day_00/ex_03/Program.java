package ex_03;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String userData = scanner.nextLine();
//        int grades = 0;
        int weekNumb = 1;
        int minGrade;
//        int gradeNumb = 5;

        while (!userData.equals("42") && weekNumb != 19) {
            if (userData.equals("Week " + weekNumb)) {
                if (!scanner.hasNextInt())
                    PrintError("Error!\nGrades should be as numbers");
                FindMinGrade(scanner);
//                grades = scanner.nextInt();
//                CheckGradeRange(grades);
//                minGrade = grades;
//                System.out.println("1 " + grades);// to do
//                for (int i = 0; i < gradeNumb - 1; ++i) {
//                    if (!scanner.hasNextInt())
//                        PrintError("Error!\nGrades should be as numbers");
//                    grades = scanner.nextInt();
//                    CheckGradeRange(grades);
//                    System.out.println((i + 2) + " " + grades); // to do
//                    if (grades < minGrade)
//                        minGrade = grades;
//                }
//                System.out.println("min " + minGrade);  // to do
                weekNumb++;
                System.out.println("Week nbr " + weekNumb); // to do
//                if (scanner.hasNextInt())
//                    PrintError("Error!\nMax number of grades: 5");
                scanner.nextLine();
                userData = scanner.nextLine();
                System.out.println("In user  data " + userData);
            }
            else
                 PrintError("theIllegalArgument");//  думай куда! еслине тот порядок недели
        }

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

    public static int FindMinGrade(Scanner scanner)
    {
        int grades;
        int minGrade;
        int gradeNumb = 5;

        grades = scanner.nextInt();
        CheckGradeRange(grades);
        minGrade = grades;
        System.out.println("1 " + grades);// to do
        for (int i = 0; i < gradeNumb - 1; ++i) {
            if (!scanner.hasNextInt())
                PrintError("Error!\nGrades should be as numbers");
            grades = scanner.nextInt();
            CheckGradeRange(grades);
            System.out.println((i + 2) + " " + grades); // to do
            if (grades < minGrade)
                minGrade = grades;
        }
        System.out.println("min " + minGrade);  // to do
        if (scanner.hasNextInt())
            PrintError("Error!\nMax number of grades: 5");
        return minGrade;
    }

}

