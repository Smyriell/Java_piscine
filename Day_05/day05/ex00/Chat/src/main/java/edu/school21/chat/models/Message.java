package edu.school21.chat.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Message {
    private int id;
    private User author;
    private Chatroom room;
    private String text;
    private LocalDateTime date;

    public Message(int id, User author, Chatroom room, String text, LocalDateTime date) {
        this.id = id;
        this.author = author;
        this.room = room;
        this.text = text;
        this.date = date;
    }


   public boolean equals(Object obj) {
        return super.equals(obj);
   }

    public int hashCode() {
        return super.hashCode();
    }

   public String toString() {
        return "Message{" + "id=" + id + ", author =" + author +
                ", room = " + room + ", text =\"" + text +
                "\", date=" + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + '}';
   }
}
