package ex04;

public class User {

    private int                 userId;
    private String              userName;
    private long                userBalance;
    private TransactionsList transList;

    public User(String name, long balance) {
        this.userName = name;
        if (balance > 0)
            this.userBalance = balance;
        else
            this.userBalance = 0;
        this.userId = UserIdsGenerator.getUsersIdsGenerator().generateID();
        this.transList = new TransactionsLinkedList();
    }

    public String getUserName() {
        return this.userName;
    }

    public long getBalance() {
        return this.userBalance;
    }

    public int getUserId() {
        return this.userId;
    }

    public TransactionsList getTransList() {
        return transList;
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
        return "User{ id=" + getUserId() +
                ", name='" + getUserName() + "'" +
                ", balance=" + getBalance() + " }";
    }

    public void setIncome(long amount) {
        this.userBalance += amount;
    }

    public void setOutcome(long amount) {
        if (this.userBalance + amount < 0) {
            printError("User " + getUserName() + " (User ID: " +
                    getUserId() + ") does not have enough balance for this transaction");
        }
        this.userBalance += amount;
    }

    public void setUserBalance(long amount) {
        this.userBalance += amount;
    }

    public static void printError (String message) {
        System.err.println(message);
        System.exit(-1);
    }
}
