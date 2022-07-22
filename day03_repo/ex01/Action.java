package ex01;

public class Action {

    private static boolean status = true;

    public synchronized void  printMessage(Type activeThread, String message ) {
        try {
            if (activeThread == Type.PRODUCER) {
                while (!status) {
                    wait();
                }
                status = false;
            } else {
                while (status) {
                    wait();
                }
                status = true;
            }
            System.out.println(message);
            notify();
        } catch (InterruptedException | IllegalThreadStateException e ) {
            printError(e.getMessage());
        }
    }

    public static void printError(String message) {
        System.err.println(message);
        System.exit(1);
    }
}
