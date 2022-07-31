package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.MessagesRepository;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("usersService")
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private PasswordEncoder passwordEncoder;
    private MessagesRepository messagesRepository;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder, MessagesRepository messagesRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.messagesRepository = messagesRepository;
    }

    @Override
    public void signUp(User user) {
        if (null == user.getName() || user.getName().equals("")) {
            throw new RuntimeException("Field name is empty. SignUp failed!");
        } else if (null == user.getPassword() || user.getPassword().equals("")) {
            throw new RuntimeException("Field password is empty. SignUp failed!");
        } else if (usersRepository.findByName(user.getName()).isPresent()) {
            throw new RuntimeException("User with name " + user.getName() + " already exist. SignUp failed!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    @Override
    public void createMessage(String message) {
        messagesRepository.save(new Message(message));
    }

    @Override
    public boolean signIn(String name, String password) {
        Optional<User> tmp = usersRepository.findByName(name);

        if (tmp.isPresent() && passwordEncoder.matches(password, tmp.get().getPassword())) {
            return true;
        }

        return false;
    }
}
