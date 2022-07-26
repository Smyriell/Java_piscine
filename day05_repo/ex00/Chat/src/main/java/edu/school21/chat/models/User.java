package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class User {
    private int id;
    private String login;
    private String password;
    private List<Chatroom> allRooms;
    private List<Chatroom> relatedRooms;

    public User(int id, String login, String password, List<Chatroom> allRooms, List<Chatroom> relatedRooms) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.allRooms = allRooms;
        this.relatedRooms = relatedRooms;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAllRooms(List<Chatroom> allRooms) {
        this.allRooms = allRooms;
    }

    public void setRelatedRooms(List<Chatroom> relatedRooms) {
        this.relatedRooms = relatedRooms;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public List<Chatroom> getAllRooms() {
        return allRooms;
    }

    public List<Chatroom> getRelatedRooms() {
        return relatedRooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        return id == user.id && Objects.equals(login, user.login) && Objects.equals(password, user.password)
                && Objects.equals(allRooms, user.allRooms) && Objects.equals(relatedRooms, user.relatedRooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, allRooms, relatedRooms);
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", allRooms=" + allRooms +
                ", userUsedRooms=" + relatedRooms +
                '}';
    }
}
