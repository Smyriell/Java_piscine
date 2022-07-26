package edu.school21.chat.app;

import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.Chatroom;
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
import java.util.Scanner;

public class Program {
    public static final String PATH_TO_DATA = "src/main/resources/data.sql";
    public static final String PATH_TO_SCHEMA = "src/main/resources/schema.sql";

    public static void main(String[] args) {
        DataSourceConnection dataSource = new DataSourceConnection();

        uploadSQLData(PATH_TO_SCHEMA, dataSource);
        uploadSQLData(PATH_TO_DATA, dataSource);

        MessagesRepository messageRepo = new MessagesRepositoryJdbcImpl(dataSource.getDataSource());

        User creator = new User(1L, "user", "user", new ArrayList(), new ArrayList());
        User author = creator;
        Chatroom room = new Chatroom(2L, "room", creator, new ArrayList());
        Message message = new Message(null, author, room, "Hello!", LocalDateTime.now());

        System.out.println("___Correct___");

        try {
            messageRepo.save(message);
            System.out.println(message.getId());
        } catch (NotSavedSubEntityException e) {
            System.out.println(e.getMessage() + " Message was not saved");
        }

        System.out.println("\n___Author is empty___");

        try {
            author = null;
            room = new Chatroom(1L, "room", author, new ArrayList());
            message = new Message(null, author, room, "He!", LocalDateTime.now());
            messageRepo.save(message);
            System.out.println(message.getId());
        } catch (NotSavedSubEntityException e) {
            System.out.println(e.getMessage() + " Message was not saved");
        }

        System.out.println("\n___Author does not exist___");

        try {
            creator = new User(1L, "user", "user", new ArrayList(), new ArrayList());
            author = new User(100L, "user", "user", new ArrayList(), new ArrayList());
            room = new Chatroom(1L, "room", creator, new ArrayList());
            message = new Message(null, author, room, "He!", LocalDateTime.now());
            messageRepo.save(message);
            System.out.println(message.getId());
        } catch (NotSavedSubEntityException e) {
            System.out.println(e.getMessage() + " Message was not saved");
        }

        System.out.println("\n___Room creator does not exist___");

        try {
            creator = null;
            author = new User(1L, "user", "user", new ArrayList(), new ArrayList());
            room = new Chatroom(1L, "room", creator, new ArrayList());
            message = new Message(null, author, room, "He!", LocalDateTime.now());
            messageRepo.save(message);
            System.out.println(message.getId());
        } catch (NotSavedSubEntityException e) {
            System.out.println(e.getMessage() + " Message was not saved");
        }

        System.out.println("\n___Room does not exist___");

        try {
            author = new User(1L, "user", "user", new ArrayList(), new ArrayList());
            creator = author;
            room = new Chatroom(100L, "room", creator, new ArrayList());
            message = new Message(null, author, room, "He!", LocalDateTime.now());
            messageRepo.save(message);
            System.out.println(message.getId());
        } catch (NotSavedSubEntityException e) {
            System.out.println(e.getMessage() + " Message was not saved");
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
