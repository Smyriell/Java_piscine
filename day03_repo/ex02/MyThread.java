package ex02;

public class MyThread extends Thread {

    private int numb;
    private int first;
    private int last;
    private Action action;

    public MyThread(int numb, int first, int last, Action action) {
        this.numb = numb;
        this.first = first;
        this.last = last;
        this.action = action;
    }

    @Override
    public void run() {
            action.countSum(numb, first, last);
        }
}
