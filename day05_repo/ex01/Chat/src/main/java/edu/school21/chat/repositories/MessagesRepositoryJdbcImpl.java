package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private DataSource dataSource;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) throws SQLException {
        String mesQuery = "SELECT * FROM chat.message WHERE id = " + id;

        try (Connection myConnection = dataSource.getConnection();
             Statement myStatement = myConnection.createStatement()) {
            ResultSet myResSet = myStatement.executeQuery(mesQuery);

            if (!myResSet.next()) {
                return Optional.empty();
            }

            Long userId = myResSet.getLong("author");
            Long roomId = myResSet.getLong("room");

            User user = findUser(userId);
            Chatroom room = findChat(roomId);

            Message message = new Message(myResSet.getLong("id"), user, room,
                    myResSet.getString("text"), myResSet.getTimestamp("localdatetime").toLocalDateTime());

            return Optional.of(message);
        } catch (NumberFormatException | SQLException e) {
            System.err.println(e.getMessage());
        }

        return Optional.empty();
    }

    private Chatroom findChat(Long roomId) throws SQLException {
        String chatQuery = "SELECT * FROM chat.chatroom WHERE id = " + roomId;

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resSet = statement.executeQuery(chatQuery);

            if (!resSet.next()) {
                return null;
            }

            return new Chatroom(roomId, resSet.getString("name"));
        }
    }

    private User findUser(Long userId) throws SQLException {
        String userQuery = "SELECT * FROM chat.user WHERE id = " + userId;

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resSet = statement.executeQuery(userQuery);

            if (!resSet.next()) {
                return null;
            }

            return new User(userId, resSet.getString("name"), resSet.getString("password"));
        }
    }
}
