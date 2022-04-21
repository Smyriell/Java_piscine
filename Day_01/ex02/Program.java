package ex02;

public class Program {
    public static void main(String[] args) throws UserNotFoundException {

        UsersArrayList users = new UsersArrayList();

        int maxUsers = (int)(Math.random() * 100);
        System.out.println("Generated " + maxUsers + " users");
        for (int i = 0; i < maxUsers; i++) {
            User tmp;
            if (i % 2 == 0) {
                tmp = new User("Bob" + i, i * 100);
            }	else {
                tmp = new User("John" + i, i * 50);
            }
            users.addUser(tmp);
        }

        System.out.println("User in the middle of array: " + users.getUserByIndex(maxUsers / 2).toString());
        System.out.println("User with id 10: " + users.getUserById(10).toString());
        System.out.println("Total number of users: " + users.getUsersSum());
    }
}
