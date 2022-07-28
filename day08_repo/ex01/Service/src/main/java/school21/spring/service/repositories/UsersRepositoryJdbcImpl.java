package school21.spring.service.repositories;

import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private final String FIND_BY_ID_QUERY = "SELECT * FROM service.users WHERE id = ";
    private final String FIND_ALL_QUERY = "SELECT * FROM service.users";
    private final String FIND_BY_EMAIL_QUERY = "SELECT * FROM service.users WHERE email = ?";
    private final String SAVE_QUERY = "INSERT INTO service.users (email) VALUES (?)";
    private final String UPDATE_QUERY = "UPDATE service.users SET email = ? WHERE id = ?";
    private final String DELETE_QUERY = "DELETE FROM service.users WHERE id = ?";

    private final Connection connection;

    public UsersRepositoryJdbcImpl(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }

    @Override
    public User findById(Long id) {
        User foundUser = null;

        try (Statement statement = connection.createStatement()) {
            ResultSet resSet = statement.executeQuery(FIND_BY_ID_QUERY + id);

            if (!resSet.next()) {
                System.out.println("No user with provided id = " + id + " found");
                return null;
            }

            foundUser = new User(id, resSet.getString("email"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return foundUser;
    }

    @Override
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resSet = statement.executeQuery(FIND_ALL_QUERY);

            while(resSet.next()) {
                userList.add(new User(resSet.getLong(1), resSet.getString(2)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return userList;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        if (email == null || email.equals("")) {
            System.out.println("No email was provided!\n");
            return Optional.empty();
        }

        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_EMAIL_QUERY)) {
            statement.setString(1, email);
            ResultSet resSet = statement.executeQuery();

            if (!resSet.next()) {
                return Optional.empty();
            }

            User foundUser = new User(resSet.getLong(1), email);

            return Optional.of(foundUser);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public void save(User newUser) {
        Optional<User> tmp = findByEmail(newUser.getEmail());

        if (tmp.isPresent()) {
            System.out.println("User with provided e-mail already exists. Save failed!\n");
            return;
        }

        try (PreparedStatement statement = connection.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, newUser.getEmail());
            statement.execute();
            ResultSet key = statement.getGeneratedKeys();
            key.next();
            newUser.setIdentifier(key.getLong("id"));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(User updUser) {
        if (findById(updUser.getIdentifier()) == null) {
            System.out.println("Update failed!\n");
            return;
        }

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, updUser.getEmail());
            statement.setLong(2, updUser.getIdentifier());
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        if (findById(id) == null) {
            System.out.println("User with provided to delete id does not exist. Delete failed");
            return;
        }

        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
