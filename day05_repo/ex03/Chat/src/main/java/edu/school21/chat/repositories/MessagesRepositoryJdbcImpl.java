package edu.school21.chat.repositories;

import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private DataSource dataSource;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) throws SQLException {
        String mesFindQuery = "SELECT * FROM chat.message WHERE id = " + id;

        try (Connection myConnection = dataSource.getConnection();
             Statement myStatement = myConnection.createStatement()) {
            ResultSet myResSet = myStatement.executeQuery(mesFindQuery);

            if (!myResSet.next()) {
                return Optional.empty();
            }

            Long userId = myResSet.getLong("author");
            Long roomId = myResSet.getLong("room");

            User user = findUser(userId);
            Chatroom room = findChat(roomId);
            LocalDateTime localDateTime = myResSet.getTimestamp(5) == null
                    ? null : myResSet.getTimestamp(5).toLocalDateTime();

            Message message = new Message(myResSet.getLong("id"), user, room,
                    myResSet.getString("text"), localDateTime);

            return Optional.of(message);
        } catch (NumberFormatException | SQLException e) {
            System.err.println(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public void save(Message message) {
        validateMessageData(message);

        Long userId = message.getAuthor().getId();
        Long roomId = message.getRoom().getId();

        String mesSaveQuery = "INSERT INTO chat.message (author, room, text, localdatetime) VALUES ("
                + message.getAuthor().getId()
                + ", " + message.getRoom().getId()
                + ", '" + message.getText() + "', '"
                + message.getLocalDateTime() + "');";

        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM chat.user WHERE id = " + userId);
            if (!resultSet.next()) {
                throw new NotSavedSubEntityException("User with ID " + userId + " does not exist.");
            }

            resultSet = statement.executeQuery("SELECT * FROM chat.chatroom WHERE id = " + roomId);
            if (!resultSet.next()) {
                throw new NotSavedSubEntityException("Room with ID " + roomId + " does not exist.");
            }

            PreparedStatement prepStatement = con.prepareStatement(mesSaveQuery, Statement.RETURN_GENERATED_KEYS);

            prepStatement.execute();
            ResultSet key = prepStatement.getGeneratedKeys();
            key.next();
            message.setId(key.getLong("id"));
            prepStatement.close();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(Message message) {
        validateMessageData(message);

        String updateQuery = "UPDATE chat.message SET author = ?, room = ?," + " text = ?, localDateTime = ? WHERE id = ?";
        String messageQuery = "SELECT * FROM chat.message WHERE id = ";

        Timestamp localDateTime = null;

        try (Connection con = dataSource.getConnection()) {
            PreparedStatement statement = con.prepareStatement(messageQuery + message.getId());
            ResultSet rs = statement.executeQuery();

            if (!rs.next()) {
                save(message);
                return ;
            }

//            if (message.getText() == null) {
//                message.setText("");
//            }

            if (message.getLocalDateTime() != null) {
                localDateTime = Timestamp.valueOf(message.getLocalDateTime());
            }
            statement = con.prepareStatement(updateQuery);
            statement.setLong(1, message.getAuthor().getId());
            statement.setLong(2, message.getRoom().getId());
            statement.setString(3, message.getText());
            statement.setTimestamp(4, localDateTime);
            statement.setLong(5, message.getId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void validateMessageData(Message message) {
        if (message.getAuthor() == null || message.getAuthor().getId() == null) {
            throw new NotSavedSubEntityException("Author doesn't exist. Incorrect!");
        } else if (message.getRoom() == null || message.getRoom().getId() == null) {
            throw new NotSavedSubEntityException("Room doesn't exist. Incorrect!");
        } else if (message.getText() == null || message.getText().length() == 0) {
            throw new NotSavedSubEntityException("No text found. Incorrect!");
        } else if (message.getRoom().getOwner() == null || message.getRoom().getOwner().getId() == null) {
            throw new NotSavedSubEntityException("Room owner doesn't exist. Incorrect!");
        }
    }

    private Chatroom findChat(Long roomId) throws SQLException {
        String chatQuery = "SELECT * FROM chat.chatroom WHERE id = " + roomId;

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resSet = statement.executeQuery(chatQuery);

            if (!resSet.next()) {
                return null;
            }

            return new Chatroom(roomId, resSet.getString(2));
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

            return new User(userId, resSet.getString(2), resSet.getString(3));
        }
    }
}
