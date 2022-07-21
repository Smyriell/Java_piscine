package ex00;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;


public class Program {

    private static final String PATH = "/Users/smyriell/repo/java_psc/day02/src/ex00/signatures.txt";
    private static Processor processor = new Processor();

    public static void printError(String message) {
        System.err.println(message);
        System.exit(1);
    }
    public static void main(String[] args) {
        try {
            FileOutputStream resultFile = new FileOutputStream("result.txt");
            Parser.parseFile(Files.readAllLines(Paths.get(PATH)));
            Scanner scanner = new Scanner(System.in);

            while (true) {
                String pathToUserFile = scanner.nextLine();

                if ("42".equals(pathToUserFile)) {
                    resultFile.write(processor.getResult().getBytes(), 0, processor.getResult().length());
                    break;
                }
                System.out.println("PROCESSED");
                CheckedFile checkedFile = new CheckedFile(pathToUserFile);

                if (checkedFile.getSignature().length() > 0) {
                    processor.process(Parser.getFormatsTable(), checkedFile.getSignature());
                }
            }
        } catch (IOException e) {
            printError(e.getMessage());
        }
    }
}
