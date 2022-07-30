package edu.school21.sockets.models;

import java.util.Objects;

public class User {
    private static final Long DEFAULT_ID = 0L;

    private Long identifier;
    private String name;
    private String password;

    public User(Long identifier, String name) {
        this.identifier = identifier;
        this.name = name;
    }

    public User(Long identifier, String name, String password) {
        this.identifier = identifier;
        this.name = name;
        this.password = password;
    }

    public User(String name, String password) {
        this.identifier = DEFAULT_ID;
        this.name = name;
        this.password = password;
    }

    public User() {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

        return Objects.equals(identifier, user.identifier) && Objects.equals(name, user.name) && Objects.equals(password, user.password);
    }


    @Override
    public int hashCode() {
        return Objects.hash(identifier, name, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "identifier=" + identifier +
                ", email='" + name + '\'' +
                ", password='" + password + '\'' +
                "}\n";
    }
}
