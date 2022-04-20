package ex00;

public class Program {
    public static void main(String[] args) {
        User one = new User("Bob", 1000);
        User two = new User("Paul", 500);

        one.printUserData();
        System.out.println();

        two.printUserData();
        System.out.println();

        Transaction tr1 = new Transaction(one, two, Category.DEBIT, 200);
        tr1.transactionInfo();

        one.printUserData();
        two.printUserData();
        System.out.println();

        Transaction tr2 = new Transaction(one, two, Category.CREDIT, 300);
        tr2.transactionInfo();

        one.printUserData();
        two.printUserData();
        System.out.println();

        // wrong data examples:

        User three = new User("NoName", -42);// negative balance
        three.printUserData();
        System.out.println();

//        Transaction tr4 = new Transaction(one, two, Category.DEBIT, 1500); // user doesn't have enough balance
//        Transaction tr5 = new Transaction(one, two, Category.CREDIT, -500);// wrong amount
        Transaction tr6 = new Transaction(one, one, Category.CREDIT, 500);// same user
    }
}
