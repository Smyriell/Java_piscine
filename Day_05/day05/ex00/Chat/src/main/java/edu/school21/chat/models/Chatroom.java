package edu.school21.chat.models;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class Chatroom {
    private int id;
    private String name;
    private User owner;
    private List<Message> allMessages;

    public Chatroom(int id, String name, User owner, List<Message> allMessages) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.allMessages = allMessages;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getOwner() {
        return this.owner;
    }

    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public int hashCode() {
        return super.hashCode();
    }

    public String toString() {
        return "Chatroom{" + "id=" + id + ", name =" + name +
                ", owner = " + owner + ", all messages =\"" + allMessages + '}';
    }
}
