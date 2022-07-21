package ex01;

public class Action {

    private static boolean status = true;

    public synchronized void  printMessage(Type activeThread, String message ) {
            if (activeThread == Type.PRODUCER) {
                while (!status) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        printError(e.getMessage());
                    }
                }
                status = false;
            } else {
                while (status) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        printError(e.getMessage());
                    }
                }
                status = true;
            }
            System.out.println(message);
            notify();
    }

    public static void printError(String message) {
        System.err.println(message);
        System.exit(1);
    }
}
