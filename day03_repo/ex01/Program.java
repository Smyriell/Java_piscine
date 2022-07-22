package ex01;

public class Program {
    private static final int NUMB_OF_ARGS = 1;
    private static int counter;

    public static void validateArgs(String[] args) {
        if (args.length != NUMB_OF_ARGS) {
            printError("There should be one argument: --count=%d");
        }

        if (args[0].matches("--count=\\d+")) {
            String[] str = args[0].split("=");
            counter = Integer.parseInt(str[1]);
        } else {
            printError("Invalid argument format");
        }
    }

    public static void printError(String message) {
        System.err.println(message);
        System.exit(1);
    }

    public static void main(String[] args) {
        try {
            validateArgs(args);

            MyThread henThread = new MyThread("Hen", counter, Type.CONSUMER);
            MyThread eggThread = new MyThread("Egg", counter, Type.PRODUCER);

            henThread.start();
            eggThread.start();
        } catch (NumberFormatException e) {
            printError("Invalid argument format");
        } catch (IllegalThreadStateException e) {
            printError(e.getMessage());
        }
    }
}

