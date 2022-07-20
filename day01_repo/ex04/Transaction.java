package ex04;

import java.util.UUID;

enum Category
{
    OUTCOME,
    INCOME
}

public class Transaction {

    private UUID transID;
    private User recipient;
    private User sender;
    private Category transCategory;
    private long        transAmount;

    public Transaction(UUID id, User one, User two, Category category, long amount) {
        this.transID = id;
        this.transCategory = category;
        cmpUsersID(one, two);
        fillRecipientAndSender(one, two, category, amount);
        this.transAmount = amount;
    }

    public UUID getTransId() {
        return this.transID;
    }

    public User getRecipient() {
        return this.recipient;
    }

    public User getSender() {
        return this.sender;
    }

    public Category getCategory() {
        return this.transCategory;
    }

    public long getAmount() {
        return this.transAmount;
    }

    public void printTransactionInfo() {
        System.out.println("************************************");
        System.out.println("Transaction Info\n" + "Transaction ID: " + getTransId() +
                "\nRecipient: " + recipient.getUserId() + ", " + recipient.getUserName() +
                "\nSender: " + sender.getUserId() + ", " + sender.getUserName() +
                "\nCategory: " + getCategory() + "\nAmount: " + getAmount());
        System.out.println("************************************");
    }

    private void cmpUsersID(User userOne, User userTwo) {
        if (userOne.getUserId() == userTwo.getUserId()) {
            printError("Recipient and Sender can not be the same person (according to User ID)");
        }
    }

    private void fillRecipientAndSender(User one, User two, Category category, long amount) {
        if (transCategory == Category.INCOME) {
            if (amount > 0) {
                this.recipient = one;
                this.sender = two;
            } else {
                printError("Income transaction amount can be just a positive number");
            }
        }
        if (transCategory == Category.OUTCOME) {
            if (amount < 0) {
                this.recipient = two;
                this.sender = one;
            } else {
                printError("Outcome transaction amount can be just a negative number");
            }
        }
    }

    @Override
    public String toString() {
        String str = "Transaction{ ";

        if (this.transCategory == Category.INCOME) {
            str += this.recipient.getUserName() + " -> " + this.sender.getUserName() + ", +"
                    + this.transAmount + ", INCOME, " + this.transID + " }";
        } else {
            str += this.sender.getUserName() + " -> " + this.recipient.getUserName() + ", "
                    + this.transAmount + ", OUTCOME, " + this.transID + " }";
        }

        return str;
    }

    public static void printError (String message) {
        System.err.println(message);
        System.exit(-1);
    }
}