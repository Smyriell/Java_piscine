package school21.spring.service.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import school21.spring.service.application.Main;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component("jdbcTemplate")
public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {

    private final JdbcTemplate jdbcTemplateObject;

    @Autowired
    public UsersRepositoryJdbcTemplateImpl(@Qualifier("hikariDataSource")DataSource dataSource) {
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    private static final class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            User user = new User();
            user.setIdentifier(resultSet.getLong("id"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));

            return user;
        }
    }

    @Override
    public User findById(Long id) {
        if (null == id || id < 1) {
            System.out.println("ID should have a positive Long type");
            return null;
        }

        User user = null;

        try {
            String query = "SELECT * FROM " + Main.SCHEMA_NAME + "." + Main.TABLE_NAME + " WHERE id = ";
            user = jdbcTemplateObject.queryForObject(query + id, new UserMapper());
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No user with provided id = " + id + " found\n" + e.getMessage());
            return null;
        }

        return user;
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplateObject.query("SELECT * FROM " + Main.SCHEMA_NAME + "." + Main.TABLE_NAME, new UserMapper());
    }

    @Override
    public Optional<User> findByEmail(String email) {
        if (email == null || email.equals("")) {
            System.out.println("No email was provided!");
            return Optional.empty();
        }

        User user = null;

        try {
            String query = "SELECT * FROM " + Main.SCHEMA_NAME + "." + Main.TABLE_NAME + " WHERE email = ?";
            user = jdbcTemplateObject.queryForObject(query, new UserMapper(), email);

            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {}

        return Optional.empty();
    }

    @Override
    public void save(User newUser) {
        Optional<User> tmp = findByEmail(newUser.getEmail());

        if (tmp.isPresent()) {
            System.out.println("User with provided e-mail already exists. Save failed!\n");
            return;
        }

        String query = "INSERT INTO " + Main.SCHEMA_NAME + "." + Main.TABLE_NAME + " (email, password) VALUES (?, ?)";
        jdbcTemplateObject.update(query, newUser.getEmail(), newUser.getPassword());
        Optional<User> user = findByEmail(newUser.getEmail());

        if (user.isPresent()) {
            newUser.setIdentifier(user.get().getIdentifier());
        } else {
            System.err.println("Save failed!\n");
        }
    }

    @Override
    public void update(User updUser) {
        if (findById(updUser.getIdentifier()) == null) {
            System.out.println("Update failed! Use SAVE to add user into the table\n");
            return;
        }

        String query = "UPDATE " + Main.SCHEMA_NAME + "." + Main.TABLE_NAME + " SET email = ? WHERE id = ?";
        jdbcTemplateObject.update(query, updUser.getEmail(), updUser.getIdentifier());
        Optional<User> user = findByEmail(updUser.getEmail());

        if (!user.isPresent()) {
            System.out.println("Update failed!\n");
        }
    }

    @Override
    public void delete(Long id) {
        if (findById(id) == null) {
            System.out.println("User with provided to delete id does not exist. Delete failed");
            return;
        }

        jdbcTemplateObject.update("DELETE FROM " + Main.SCHEMA_NAME + "." + Main.TABLE_NAME + " WHERE id = ?", id);
    }
}
