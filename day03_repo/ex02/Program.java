package ex02;

import java.util.Random;

public class Program {
    private static final int NUMB_OF_ARGS = 2;
    private static final int MAX_MODULO_VALUE = 1000;

    private static int arrLen;
    private static int threadsNumb;
    private static int[] arr;
    private static int checksum = 0;
    private static MyThread[] threadsArr;
    private static Action action;

    public static void validateArgs(String[] args) {
        String[] str;

        if (args.length != NUMB_OF_ARGS ) {
            printError("There should be two arguments: --arraySize=%d --threadsCount=%d");
        }

        for (int i = 0; i < NUMB_OF_ARGS; i++) {
            if (i == 0 && args[i].matches("--arraySize=\\d+")) {
                str = args[i].split("=");
                arrLen = Integer.parseInt(str[1]);
            } else if (i == 1 && args[i].matches("--threadsCount=\\d+")) {
                str = args[i].split("=");
                threadsNumb = Integer.parseInt(str[1]);
            } else {
                printError("Invalid argument format");
            }
        }

        if (arrLen > 2000000 || threadsNumb < 1 || threadsNumb > arrLen) {
            printError("Not valid data. Max size of array <= 2_000_000, Max number of threads is no\n" +
                    "greater than size of array and not less than 1");
        }
    }

    public static void printError(String message) {
        System.err.println(message);
        System.exit(1);
    }

    public static void fillArray() {
        Random random = new Random();
        arr = new int[arrLen];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(MAX_MODULO_VALUE) * (random.nextBoolean() ? 1 : -1);
        }

    }

    public static void getPrintChecksum() {
        for (int i = 0; i < arrLen; i++) {
            checksum += arr[i];
        }
        System.out.println("Sum: " + checksum);
    }

    public static void fillThreads() {
        int block;
        int first = 0;
        int last = 0;
        int numb = 0;

        action = new Action(arr);

        threadsArr = new MyThread[threadsNumb];

        if (threadsNumb == 1) {
            threadsArr[numb] = new MyThread(numb, first, arrLen - 1, action);
        } else {
            block = arrLen / threadsNumb;

            for (; numb < threadsNumb - 1; numb++) {
                first = block * numb;
                last = first + block - 1;
                threadsArr[numb] = new MyThread(numb, first, last, action);
            }

            first = block * numb;
            threadsArr[numb] = new MyThread(numb, first, arrLen - 1, action);
        }
    }

    public static void runThreads() {
        try {
            for (int i = 0; i < threadsArr.length; i++) {
                threadsArr[i].start();
            }
            for (int i = 0; i < threadsArr.length; i++) {
                threadsArr[i].join();
            }
        } catch (InterruptedException e) {
            printError(e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            validateArgs(args);
            fillArray();
            getPrintChecksum();
            fillThreads();
            runThreads();
        } catch (NumberFormatException e) {
            printError("Invalid argument format");
        } catch (IllegalThreadStateException e) {
            printError(e.getMessage());
        }

        System.out.println("Sum by threads: " + action.getSumByThreads());
    }
}
