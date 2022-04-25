package ex00;

public class MyThread extends Thread {

    private String message;
    private int count;


    public MyThread(String message, int count) {
        this.message = message;
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 0; i < this.count; i++) {
            System.out.println(this.message);
        }
    }
}
