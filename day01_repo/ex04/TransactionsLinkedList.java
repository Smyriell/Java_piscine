package ex04;

import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {

    private Node head;
    private Node tail;
    private int size;

    public TransactionsLinkedList() {
        this.size = 0;
    }

    private static class Node {
        Transaction data;
        Node prev;
        Node next;

        public Node(Node prev, Transaction transaction, Node next) {
            this.prev = prev;
            this.data = transaction;
            this.next = next;
        }
    }

    @Override
    public void addTransaction(Transaction trans) {
        if (size == 0) {
            Node newNode = new Node(null, trans, null);
            this.head = newNode;
            this.tail = newNode;
        } else {
            Node tmp = this.tail;
            Node newNode = new Node(tail, trans, null);
            this.tail = newNode;
            tmp.next = newNode;
        }
        ++size;
    }

    @Override
    public void removeTransaction(UUID transID) {
        Node tmp = head;
        for (; tmp != null; tmp = tmp.next) {
            if (this.size != 0 && transID.equals(tmp.data.getTransId())) {
                eraseFromList(tmp);
                return;
            }
        }
        throw new TransactionNotFoundException("Transaction with ID = " + transID + " has not been found!");
    }

    private void eraseFromList(Node node) {
        Node prevTmp = node.prev;
        Node nextTmp = node.next;

        if (this.size > 1) {

            if (nextTmp == null) {
                this.tail = prevTmp;
                prevTmp.next = null;
            } else if (prevTmp == null) {
                this.head = nextTmp;
                nextTmp.prev = null;
            } else {
                prevTmp.next = nextTmp;
                nextTmp.prev = prevTmp;
            }
        }

        --this.size;
    }

    @Override
    public Transaction[] listOfTransToArray() {
        Transaction[] arr = new Transaction[this.size];
        Node tmp = this.head;

        for (int i = 0; i < size; i++, tmp = tmp.next) {
            arr[i] = tmp.data;
        }

        return arr;
    }

//    @Override
//    public String toString() {
//        String str = "";
//        for (TransactionsLinkedList.Node temp = head; temp != null; temp = temp.next) {
//            if (temp.data.getCategory() == Category.OUTCOME) {
//                str += temp.data.getSender().getUserName() + " -> "
//                        + temp.data.getRecipient().getUserName() + " , "
//                        + temp.data.getAmount() + " , ID: " + temp.data.getCategory() + " , "
//                        + temp.data.getTransId() + "\n";
//            } else {
//                str += temp.data.getRecipient().getUserName() + " -> "
//                        + temp.data.getSender().getUserName() + " , "
//                        + temp.data.getAmount() + " , ID: " + temp.data.getCategory() + " , "
//                        + temp.data.getTransId() + "\n";
//            }
//        }
//        return str;
//    }

    @Override
    public String toString() {
        String str = "TransactionsList { " +
                "size: " + this.size + "\n";

        for (Node tmp = head; tmp != null; tmp = tmp.next) {
            str += "\t" + tmp.data + "\n";
        }
        str += "}";

        return str;
    }

    public String tooString() {
        String str = "TransList { size = " + this.size + "\n";
        for(Node temp = head; temp != null; temp = temp.next) {
            str += "\t" + temp.data + "\n";
        }
        str += " }";
        return str;
    }

}
