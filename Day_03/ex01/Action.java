package ex01;

public class Action {

    private static int status = 1;

    public void printMessage(Type activeThread, String message ) throws InterruptedException {
        synchronized (this) {
            if (activeThread == Type.PRODUCER) {
                while (status != 1)
                    wait();
                status = 0;
            } else {
                while (status != 0)
                    wait();
                status = 1;
            }
            System.out.println(message);
            notify();
        }
    }
}
