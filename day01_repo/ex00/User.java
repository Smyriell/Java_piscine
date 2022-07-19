package ex00;

public class User {

    private static int globalId = 0;

    private int     userId;
    private String  userName;
    private long    userBalance;

    public User(String name, long balance) {
        userName = name;
        if (balance > 0)
            userBalance = balance;
        else
            printError("Initial user balance cannot be negative");
        userId = ++globalId;
    }

    public String getUserName() {
        return userName;
    }

    public long getBalance() {
        return userBalance;
    }

    public int getUserId() {
        return userId;
    }

    public void printUserData() {
        System.out.println("************************************");
        System.out.println("User Info\n" + "User ID: " +
                getUserId() + "\nUser name: " + getUserName() +
                "\nBalance: " + getBalance());
        System.out.println("************************************");
    }

    @Override
    public String toString() {
        return "User{ id=" + userId +
                ", name='" + userName + "\'" +
                ", balance=" + userBalance + " }";
    }

    public void setIncome(long amount) {
        userBalance += amount;
    }

    public void setOutcome(long amount) {
        if (userBalance + amount < 0) {
            printError("User " + getUserName() + " (User ID: " +
                    getUserId() + ") does not have enough balance for this transaction");
        }
        userBalance += amount;
    }

    public static void printError (String message) {
        System.out.println(message);
        System.exit(-1);
    }
}