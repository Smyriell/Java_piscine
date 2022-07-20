package ex04;

import java.util.UUID;

public class TransactionsService {

    private UsersList usersList;

    public TransactionsService() {
        usersList = new UsersArrayList();
    }

    public void addUser(User newUser) {
        usersList.addUser(newUser);
    }

    public long retrieveUserBalance(int userID) {
        return usersList.retrieveUserByID(userID).getBalance();
    }

    public void transferTransaction(int senderID, int recipientID, long amount) {
        if (amount <= 0) {
            throw new IllegalTransactionException("Amount must be a positive number");
        }

        User sender = usersList.retrieveUserByID(senderID);
        User recipient = usersList.retrieveUserByID(recipientID);

        if (sender.getBalance() - amount >= 0) {
            UUID id = UUID.randomUUID();
            Transaction sending = new Transaction(id, sender, recipient, Category.OUTCOME, -amount);
            Transaction receiving = new Transaction(id, sender, recipient, Category.INCOME, amount);
            sender.setOutcome(-amount);
            sender.getTransList().addTransaction(sending);
            recipient.setIncome(amount);
            recipient.getTransList().addTransaction(receiving);

        } else {
            throw new IllegalTransactionException("User " + sender.getUserName()
                    + " does not have enough fund for this transfer\nAvailable Balance = " + sender.getBalance());
        }
    }

    public Transaction[] retrieveUserTransfers(int userID) {
        return usersList.retrieveUserByID(userID).getTransList().listOfTransToArray();
    }

    public void removeUserTransaction(int userID, UUID transID) {
        usersList.retrieveUserByID(userID).getTransList().removeTransaction(transID);
    }

    public Transaction[] showUnpairedTransactions() {
        int n = 0;
        int max = 10;
        Transaction[] result = new Transaction[max];
        int size = usersList.numbOfUsers();

        for (int index = 0; index < size; index++) {
            User user = usersList.retrieveUserByIndex(index);
            Transaction[] transactions = user.getTransList().listOfTransToArray();

            for (int j = 0; j < transactions.length; j++) {
                if (isUnpaired(index, transactions[j].getTransId(), size)) {
                    if (n == max) {
                        max *= 2;
                        Transaction[] tmp = result;
                        result = new Transaction[max];

                        for (int l = 0; l < tmp.length; l++) {
                            result[l] = tmp[l];
                        }
                    }
                    result[n++] = transactions[j];
                }
            }
        }
        Transaction[] updatedArr = updateArray(n, result);
        return updatedArr;
    }

    private boolean isUnpaired(int index, UUID id, int size) {
        for (int i = 0; i < size; i++) {
            if (index == i) {
                continue;
            }
            User user = usersList.retrieveUserByIndex(i);
            Transaction[] transactions = user.getTransList().listOfTransToArray();

            for (int j = 0; j < transactions.length; j++) {
                if (id.equals(transactions[j].getTransId())) {
                    return false;
                }
            }
        }
        return true;
    }

    private Transaction[] updateArray(int size, Transaction[] tmp) {
        Transaction[] res = new Transaction[size];

        for (int i = 0; i < size; i++) {
            res[i] = tmp[i];
        }
        return res;
    }

}
