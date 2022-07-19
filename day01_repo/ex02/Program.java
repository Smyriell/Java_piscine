package ex02;

public class Program {
    public static void main(String[] args) {
        User one = new User("Paul", 100);
        User two = new User("Bob", 200);
        User three = new User("John", 50);
        User four = new User("Pit", 90);

        System.out.println(one.toString());
        System.out.println(two.toString());
        System.out.println(three.toString());
        System.out.println(four.toString());
        System.out.println();

        UsersArrayList all = new UsersArrayList();
        System.out.println("NumbOfUsers in the list: " + all.NumbOfUsers());
        all.addUser(one);
        System.out.println("NumbOfUsers in the list: " + all.NumbOfUsers());
        all.addUser(two);
        System.out.println("NumbOfUsers in the list: " + all.NumbOfUsers());
        all.addUser(three);
        System.out.println("NumbOfUsers in the list: " + all.NumbOfUsers());
        System.out.println();
        System.out.println("retrieveUserByID result: \n" + all.retrieveUserByID(3).toString());
        System.out.println();
        System.out.println("retrieveUserByIndex result: \n" + all.retrieveUserByIndex(1).toString());
        System.out.println();
        System.out.println("Exceptions:\n");
        all.addUser(one);
        all.retrieveUserByID(4);
        all.retrieveUserByIndex(6);





    }
}
