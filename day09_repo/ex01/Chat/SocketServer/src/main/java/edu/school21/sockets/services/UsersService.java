package edu.school21.sockets.services;

import edu.school21.sockets.models.User;

public interface UsersService {
    void signUp(User user);
    boolean signIn(String name, String password);
    void createMessage(String message);
}

