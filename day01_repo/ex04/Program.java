package ex04;

public class Program {
    public static void main(String[] args) {
        TransactionsService service = new TransactionsService();
        User A = new User("Bob", 1000);
        User B = new User("John", 1000);

        service.addUser(A);
        service.addUser(B);

        System.out.println();
        System.out.println("Bob balance: " + service.retrieveUserBalance(A.getUserId()));
        System.out.println("John balance: " + service.retrieveUserBalance(B.getUserId()));

        System.out.println("\nTRANSACTIONS:");
        service.transferTransaction(A.getUserId(), B.getUserId(), 500);
        service.transferTransaction(B.getUserId(), A.getUserId(), 200);

        System.out.println("Bob: " + A + "\nBob list: \n" + A.getTransList());
        System.out.println("John: " + B + "\nJohn list: \n" + B.getTransList());

        System.out.println("\nGET TRANSACTIONS BY USER ID:");
        Transaction[] transA = service.retrieveUserTransfers(A.getUserId());
        for (Transaction tr : transA) {
            System.out.println(tr);
        }

        System.out.println("\nREMOVE TRANSACTIONS:");
        service.removeUserTransaction(A.getUserId(), transA[1].getTransId());
        System.out.println("Bob: " + A + "\nBob list: \n" + A.getTransList());

        System.out.println("\nCHECK UNPAIRED TRANSACTIONS:");
        transA = service.showUnpairedTransactions();

        for (Transaction transaction : transA) {
            System.out.println(transaction);
        }

        try {
            System.out.println("\nWRONG CASE:");
            service.transferTransaction(A.getUserId(), B.getUserId(), 1000000);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
