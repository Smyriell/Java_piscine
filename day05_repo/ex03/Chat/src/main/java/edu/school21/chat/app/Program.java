package edu.school21.chat.app;

import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.DataSourceConnection;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    public static final String PATH_TO_DATA = "src/main/resources/data.sql";
    public static final String PATH_TO_SCHEMA = "src/main/resources/schema.sql";

    public static void main(String[] args) {
        DataSourceConnection dataSource = new DataSourceConnection();

        uploadSQLData(PATH_TO_SCHEMA, dataSource);
        uploadSQLData(PATH_TO_DATA, dataSource);

        MessagesRepository messageRepo = new MessagesRepositoryJdbcImpl(dataSource.getDataSource());

        User maria = new User(1L, "Maria", "maria", new ArrayList<>(), new ArrayList<>());
        User paul = new User(2L, "Paul", "paul", new ArrayList<>(), new ArrayList<>());
        User user = new User(3L, "User", "user", new ArrayList<>(), new ArrayList<>());
        Chatroom room = new Chatroom(2L, "room2", paul, new ArrayList<>());
        Message message;
        long id = 1L;

        System.out.println("___EXAMPLE 1___");

        try {
            Optional<Message> optMessage = messageRepo.findById(id);

            if (optMessage.isPresent()) {
                message = optMessage.get();
                System.out.println("\nORIG message");
                System.out.println(message);
                message.setAuthor(paul);
                message.setRoom(room);
                message.setText("Text from Paul!");
                message.setLocalDateTime(null);
                System.out.println("\nUPDATED message");
                messageRepo.update(message);
                System.out.println(messageRepo.findById(message.getId()).get());
            }
        } catch (SQLException | NotSavedSubEntityException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n___EXAMPLE 2___");

        try {
            id = 2L;
            Optional<Message> optMessage = messageRepo.findById(id);
            room = new Chatroom(3L, "room3", user, new ArrayList<>());

            if (optMessage.isPresent()) {
                message = optMessage.get();
                System.out.println("\nORIG message");
                System.out.println(message);
                message.setAuthor(user);
                message.setRoom(room);
                message.setText("Hello from user");
                message.setLocalDateTime(LocalDateTime.now().minusDays(100));
                System.out.println("\nUPDATED message");
                messageRepo.update(message);
                System.out.println(messageRepo.findById(message.getId()).get());
            }
        } catch (SQLException | NotSavedSubEntityException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n___EXAMPLE 3___");

        try {
            id = 4L;
            Optional<Message> optMessage = messageRepo.findById(id);

            if (optMessage.isPresent()) {
                message = optMessage.get();
                System.out.println("\nORIG message");
                System.out.println(message);
                message.setAuthor(maria);
                message.setRoom(room);
                message.setText("Something written here by Maria");
                message.setLocalDateTime(LocalDateTime.now().minusDays(1));
                System.out.println("\nUPDATED message");
                messageRepo.update(message);
                System.out.println(messageRepo.findById(message.getId()).get());
            }
        } catch (SQLException | NotSavedSubEntityException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void uploadSQLData(String file, DataSourceConnection dataSource) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             FileInputStream stream = new FileInputStream(file)) {
            Scanner scanner = new Scanner(stream).useDelimiter(";");

            while (scanner.hasNext()) {
                statement.executeUpdate(scanner.next().trim());
            }
        } catch (IOException | SecurityException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
