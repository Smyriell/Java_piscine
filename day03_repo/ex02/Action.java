package ex02;

public class Action {

    private int[] arr;
    private int sumByThreads;

    public Action(int[] arr) {
        this.arr = arr;
    }

    public synchronized void countSum(int n, int first, int last) {
        int sum = 0;
        for (int i = first; i <= last; ++i) {
            sum += arr[i];
        }
        System.out.println("Thread " + (n + 1) + ": from " + first + " to " + last + " sum is " + sum);
        sumByThreads += sum;
    }

    public int getSumByThreads() {
        return sumByThreads;
    }
}
