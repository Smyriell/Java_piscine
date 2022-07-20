package ex03;

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

    public Transaction(User userOne, User userTwo, Category category, long amount) {
        this.transID = UUID.randomUUID();
        this.transCategory = category;
        cmpUsersID(userOne, userTwo);
        fillRecipientAndSender(userOne, userTwo, category, amount);
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

    private void fillRecipientAndSender(User userOne, User userTwo, Category category, long amount) {
        if (transCategory == Category.INCOME) {
            if (amount > 0) {
                this.recipient = userOne;
                recipient.setIncome(amount);
                this.sender = userTwo;
                sender.setOutcome(-amount);
            } else {
                printError("Income transaction amount can be just a positive number");
            }
        }
        if (transCategory == Category.OUTCOME) {
            if (amount < 0) {
                this.recipient = userTwo;
                recipient.setIncome(-amount);
                this.sender = userOne;
                sender.setOutcome(amount);
            } else {
                printError("Outcome transaction amount can be just a negative number");
            }
        }
    }

    public static void printError (String message) {
        System.err.println(message);
        System.exit(-1);
    }
}