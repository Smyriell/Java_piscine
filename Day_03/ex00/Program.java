package ex00;


public class Program {
    public static void main(String[] args) throws InterruptedException {
        if (args.length != 1 )
            printError("There should be one argument");
        int counter = 0;
        if (args[0].matches("--count=\\d+")) {
            counter = Integer.parseInt(args[0].split("=")[1]);
        }
        else {
            printError("Invalid argument");
        }
        MyThread henThread = new MyThread("Hen", counter);
        MyThread eggThread = new MyThread("Egg", counter);
        henThread.start();
        eggThread.start();
        henThread.join();
        eggThread.join();
        for (int i = 0; i < counter; i++) {
            System.out.println("Human");
        }
    }

    public static void printError(String message) {
        System.out.println(message);
        System.exit(-1);
    }
}

