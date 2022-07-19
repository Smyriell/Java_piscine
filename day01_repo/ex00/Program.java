package ex00;

public class Program {

    public static void main(String[] args) {
        User one = new User("Bob", 1000);
        User two = new User("Paul", 500);

        one.printUserData();
        System.out.println("\nOverloaded toString input:\n" + one.toString());
        System.out.println();

        two.printUserData();
        System.out.println("\nOverloaded toString input:\n" + two.toString());
        System.out.println();

        Transaction tr1 = new Transaction(one, two, Category.INCOME, 200);
        tr1.printTransactionInfo();

        one.printUserData();
        two.printUserData();
        System.out.println();

        Transaction tr2 = new Transaction(one, two, Category.OUTCOME, -300);
        tr2.printTransactionInfo();

        one.printUserData();
        two.printUserData();
        System.out.println();
    }
}
