package ex_00;

public class Program {
    public static void main(String[] args) {
        int n = 479598;
        int res;

        res = n % 10;
        n /= 10;
        res += n % 10;
        n /= 10;
        res += n % 10;
        n /= 10;
        res += n % 10;
        n /= 10;
        res += n % 10;
        n /= 10;
        res += n % 10;

        System.out.println(res);
    }
}
