package edu.school21.chat.models;

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

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setAllMessages(List<Message> allMessages) {
        this.allMessages = allMessages;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

    public List<Message> getAllMessages() {
        return allMessages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Chatroom chatroom = (Chatroom) o;
        return id == chatroom.id && Objects.equals(name, chatroom.name) && Objects.equals(owner, chatroom.owner)
                && Objects.equals(allMessages, chatroom.allMessages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, owner, allMessages);
    }

    @Override
    public String toString() {
        return "Chatroom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                ", allMessages=" + allMessages +
                '}';
    }
}
