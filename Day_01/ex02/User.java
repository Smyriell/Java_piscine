package ex02;

public class User {

    private int     userId;
    private String  userName;
    private int     userBalance;

    public User(String name, int balance) {
        this.userName = name;
        if (balance > 0)
            this.userBalance = balance;
        else
            this.userBalance = 0;
        this.userId = UserIdsGenerator.getUsersIdsGenerator().generateID();
    }

    public String getUserName() {
        return userName;
    }

    public int getBalance() {
        return userBalance;
    }

    public int getUserId() {
        return userId;
    }

    public String toString() {
        return ("\n****************\n" + "User ID: " + getUserId() + "\nUser name: " + getUserName() +
                "\nBalance: " + getBalance() + "\n****************\n");
    }

    public void setIncome(int amount) {
        this.userBalance += amount;
    }

    public void setOutgoing(int amount) {
        if (this.userBalance - amount < 0) {
            printError("User " + getUserName() + " (User ID: " +
                    getUserId() + ") does not have enough balance for this transaction");
        }
        this.userBalance -= amount;
    }

    static public void printError (String message) {
        System.out.println(message);
        System.exit(-1);
    }


}
