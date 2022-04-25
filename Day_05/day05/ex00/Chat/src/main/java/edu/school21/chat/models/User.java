package edu.school21.chat.models;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class User {
    private int id;
    private String login;
    private String password;
    private List<Chatroom> avaliableRooms;
    private List<Chatroom> rooms;

    public User(int id, String login, String password, List<Chatroom> avaliableRooms, List<Chatroom> rooms) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.avaliableRooms = avaliableRooms;
        this.rooms = rooms;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return this.login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public int hashCode() {
        return super.hashCode();
    }

    public String toString() {
        return "User{" + "id=" + id + ", login =" + login +
                ", password = " + password + ", avaliable rooms = " + avaliableRooms +
                ", rooms = " + rooms + '}';
    }
}
