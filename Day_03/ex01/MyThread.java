package ex01;

enum Type {
    PRODUCER,
    CONSUMER
}

public class MyThread extends Thread {

    private String message;
    private int count;
    private Type type;
    private static Action action = new Action();

    public MyThread(String message, int count, Type type) {
        this.message = message;
        this.count = count;
        this.type = type;
    }

    @Override
    public void run() {
        for (int i = 0; i < this.count; i++) {
            try {
                action.printMessage(type, message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
