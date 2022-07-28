package school21.spring.service.models;

import java.util.Objects;

public class User {
    private Long identifier;
    private String email;

    public User(Long identifier, String email) {
        this.identifier = identifier;
        this.email = email;
    }

    public Long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

        return Objects.equals(identifier, user.identifier) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "identifier=" + identifier +
                ", email='" + email + '\'' +
                '}' + '\n';
    }
}
