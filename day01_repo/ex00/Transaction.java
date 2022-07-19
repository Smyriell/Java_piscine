package ex00;

import java.util.UUID;

enum Category
{
    OUTCOME,
    INCOME
}

public class Transaction {

    private UUID        transID;
    private User        recipient;
    private User        sender;
    private Category    transCategory;
    private long        transAmount;

    public Transaction(User userOne, User userTwo, Category category, long amount) {
        transID = UUID.randomUUID();
        transCategory = category;
        cmpUsersID(userOne, userTwo);
        fillRecipientAndSender(userOne, userTwo,category, amount);
        transAmount = amount;
    }

    public UUID getTransId() {
        return transID;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }

    public Category getCategory() {
        return transCategory;
    }

    public long getAmount() {
        return transAmount;
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
                recipient = userOne;
                recipient.setIncome(amount);
                sender = userTwo;
                sender.setOutcome(-amount);
            } else {
                printError("Income transaction amount can be just a positive number");
            }
        }
        if (transCategory == Category.OUTCOME) {
            if (amount < 0) {
                recipient = userTwo;
                recipient.setIncome(-amount);
                sender = userOne;
                sender.setOutcome(amount);
            } else {
                printError("Outcome transaction amount can be just a negative number");
            }
        }
    }

    public static void printError (String message) {
        System.out.println(message);
        System.exit(-1);
    }
}
