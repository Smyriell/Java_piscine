package ex03;

public class Program {
    private static final int NUMB_OF_ARGS = 1;
    public static int numbOfThreads;
    private static MyThread[] threadsArr;
    private static Url url;


    public static void printError(String message) {
        System.err.println(message);
        System.exit(1);
    }

    public static void validateArgs(String[] args) {
        String[] str;

        if (args.length != NUMB_OF_ARGS) {
            printError("There should be one argument: --threadsCount=%d");
        }

        if (args[0].matches("--threadsCount=\\d+")) {
            str = args[0].split("=");
            numbOfThreads = Integer.parseInt(str[1]);
        } else {
            printError("Invalid argument");
        }

        if (numbOfThreads < 1) {
            printError("Invalid data. Number of threads should be > 1");
        }
    }

    public static void fillThreads() {
        threadsArr = new MyThread[numbOfThreads];
        url = new Url();
        int threadNum = 1;

        for (int i = 0; i < numbOfThreads; i++, threadNum++) {
            threadsArr[i] = new MyThread(url, threadNum);
        }
    }

    private static void runThreads() {
        for (int i = 0; i < numbOfThreads; i++) {
            threadsArr[i].start();
        }
    }

    public static void main(String[] args) {
        try {
            validateArgs(args);
            fillThreads();
            runThreads();
        } catch (NumberFormatException e) {
            printError("Invalid argument format.");
        } catch (RuntimeException e) {
            printError("Wrong path to the file.");
        }
    }
}
