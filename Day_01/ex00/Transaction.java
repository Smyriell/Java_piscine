package ex00;

import java.util.UUID;

enum Category
{
    DEBIT,
    CREDIT
}

public class Transaction {

    private UUID        transID;
    private User        recipient;
    private User        sender;
    private Category    transCategory;
    private int         transAmount;

    public Transaction(User recipient, User sender, Category category, int amount) {
        this.transID = UUID.randomUUID();;
        this.recipient = recipient;
        this.sender = sender;
        if (recipient.getUserId() == sender.getUserId())
            printError("Recipient and Sender can not be the same person (according to User ID)");

        this.transCategory = category;

        if (amount < 0)
            printError("Transaction amount can be just a positive number");
        else
            this.transAmount = amount;

        if (transCategory == Category.CREDIT) {
            recipient.setIncome(amount);
            sender.setOutgoing(amount);
        }

        if (transCategory == Category.DEBIT) {
            recipient.setOutgoing(amount);
            sender.setIncome(amount);
        }
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

    public int getAmount() {
        return transAmount;
    }

    public void transactionInfo()
    {
        System.out.println("************************************");
        System.out.println("Transaction Info\n" + "Transaction ID: " + getTransId() +
                "\nRecipient: " + recipient.getUserId() + ", " + recipient.getUserName() +
                "\nSender: " + sender.getUserId() + ", " + sender.getUserName() +
                "\nCategory: " + getCategory() + "\nAmount: " + getAmount());
        System.out.println("************************************");
    }

    static public void printError (String message) {
        System.out.println(message);
        System.exit(-1);
    }
}
