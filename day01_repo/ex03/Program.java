package ex03;

public class Program {

    public static void main(String[] args) {

        User one = new User("Paul", 100);
        User two = new User("Bob", 20000010);
        User three = new User("John", 5000);
        User four = new User("Pit", 90600);

        System.out.println(one.toString());
        System.out.println(two.toString());
        System.out.println(three.toString());
        System.out.println(four.toString());
        System.out.println("----------------------\n");

        TransactionsLinkedList commonList = new TransactionsLinkedList();
        System.out.println("commonList is created");
        System.out.println("length of Array after creating a list: " + commonList.listOfTransToArray().length);
        System.out.println(commonList.toString());
        System.out.println("-------");

        Transaction tr1 = new Transaction(three, four, Category.OUTCOME, -100);
        Transaction tr2 = new Transaction(one, two, Category.INCOME, 200);
        Transaction tr3 = new Transaction(one, two, Category.OUTCOME, -10);
        Transaction tr4 = new Transaction(one, two, Category.INCOME, 670);
        Transaction tr5 = new Transaction(one, two, Category.OUTCOME, -100);
        Transaction tr6 = new Transaction(one, two, Category.INCOME, 100);

        commonList.addTransaction(tr1);
        commonList.addTransaction(tr2);
        commonList.addTransaction(tr3);
        commonList.addTransaction(tr4);
        commonList.addTransaction(tr5);
        commonList.addTransaction(tr6);

        System.out.println("length of Array after adding six trans: " + commonList.listOfTransToArray().length);
        System.out.println(commonList.toString());
        System.out.println("-------");
        commonList.removeTransaction(tr3.getTransId());
        System.out.println("length of Array after removing 1 middle trans: " + commonList.listOfTransToArray().length);
        System.out.println(commonList.toString());
        commonList.removeTransaction(tr1.getTransId());
        System.out.println("length of Array after removing first trans: " + commonList.listOfTransToArray().length);
        System.out.println(commonList.toString());
        commonList.removeTransaction(tr6.getTransId());
        System.out.println("length of Array after removing last trans: " + commonList.listOfTransToArray().length);
        System.out.println(commonList.toString());
        System.out.println("----------------------\n");

        System.out.println("length of Array in user One list: " + one.getTransList().listOfTransToArray().length);
        System.out.println("-------");
        one.getTransList().addTransaction(tr2);
        System.out.println("length of Array after adding one trans: " + one.getTransList().listOfTransToArray().length);
        System.out.println(one.getTransList().toString());
        System.out.println("-------");
        one.getTransList().addTransaction(tr3);
        one.getTransList().addTransaction(tr4);
        System.out.println("length of Array after adding more: " + one.getTransList().listOfTransToArray().length);
        System.out.println(one.getTransList().toString());
    }
}
