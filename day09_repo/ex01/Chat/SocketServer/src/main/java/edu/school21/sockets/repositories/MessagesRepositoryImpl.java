package edu.school21.sockets.repositories;

import edu.school21.sockets.app.Main;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MessagesRepositoryImpl implements MessagesRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public MessagesRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);

        createTable();
    }

    private static class MessageRowMapper implements RowMapper<Message> {
        @Override
        public Message mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Message message = new Message();

            message.setContent(resultSet.getString("content"));
            message.setTime(resultSet.getTimestamp("time").toLocalDateTime());

            return message;
        }
    }

    @Override
    public Message findById(Long id) {
        System.out.println("Technical work is in progress...");
        return null;
    }

    @Override
    public List<Message> findAll() {
        final String query = "SELECT * FROM " + Main.SCHEMA_NAME + "." + Main.TABLE_NAME;

        return jdbcTemplate.query(query, new MessageRowMapper());
    }

    @Override
    public void save(Message newMessage) {
        String query = "INSERT INTO " + Main.SCHEMA_NAME + "." + Main.TABLE_NAME + " (content) VALUES (?)";

        int result = jdbcTemplate.update(query, newMessage.getContent());

        if (result == 0) {
            System.err.println("Message save failed!\n Content: " + newMessage.getContent() + " will be lost");
        }
    }

    @Override
    public void update(Message entity) {
        System.out.println("Technical work is in progress...");
    }

    @Override
    public void delete(Long id) {
        System.out.println("Technical work is in progress...");
    }

    private void createTable() {
        jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS service;");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS service.messages ("
                + "content TEXT NOT NULL, time TIMESTAMP DEFAULT CURRENT_TIMESTAMP);");
    }
}

