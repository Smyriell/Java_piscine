package ex01;

public class Program {
    public static void main(String[] args) {
        User one = new User("Bob", 1000);
        User two = new User("Paul", 500);

        one.printUserData();
        System.out.println();

        two.printUserData();
        System.out.println();

        System.out.println(UserIdsGenerator.getUsersIdsGenerator().generateID());

        for (int i = 0; i < 50; i++) {
            User example;
            if (i % 2 == 0) {
                example = new User("Bob", i * 10);
            }
            else {
                example = new User("Paul", i * 11);
            }
            System.out.println("User " + i + ", his unique ID is: " + example.getUserId());
        }
    }
}
