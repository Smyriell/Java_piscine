package ex01;

public class Program {
    public static void main(String[] args) {
        if (args.length != 1 )
            printError("There should be one argument");
        int counter = 0;
        if (args[0].matches("--count=\\d+")) {
            counter = Integer.parseInt(args[0].split("=")[1]);
        }
        else {
            printError("Invalid argument");
        }

        MyThread henThread = new MyThread("Hen", counter, Type.CONSUMER);
        MyThread eggThread = new MyThread("Egg", counter, Type.PRODUCER);
        henThread.start();
        eggThread.start();

    }

    public static void printError(String message) {
        System.out.println(message);
        System.exit(-1);
    }
}
