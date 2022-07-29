package school21.spring.service.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school21.spring.service.application.Main;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component("jdbc")
public class UsersRepositoryJdbcImpl implements UsersRepository {
    private final Connection connection;

    @Autowired
    public UsersRepositoryJdbcImpl(@Qualifier("driverManagerDataSource")DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }

    @Override
    public User findById(Long id) {
        if (null == id || id < 1) {
            System.out.println("ID should have a positive Long type");
            return null;
        }
        User foundUser = null;

        try (Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM " + Main.SCHEMA_NAME + "." + Main.TABLE_NAME + " WHERE id = ";
            ResultSet resSet = statement.executeQuery(query + id);

            if (!resSet.next()) {
                System.out.println("No user with provided id = " + id + " found");
                return null;
            }

            foundUser = new User(id, resSet.getString("email"), resSet.getString("password"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return foundUser;
    }

    @Override
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resSet = statement.executeQuery("SELECT * FROM " + Main.SCHEMA_NAME + "." + Main.TABLE_NAME);

            while(resSet.next()) {
                userList.add(new User(resSet.getLong(1), resSet.getString(2), resSet.getString(3)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return userList;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        if (email == null || email.equals("")) {
            System.out.println("No email was provided!");
            return Optional.empty();
        }

        String query = "SELECT * FROM " + Main.SCHEMA_NAME + "." + Main.TABLE_NAME + " WHERE email = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet resSet = statement.executeQuery();

            if (!resSet.next()) {
                return Optional.empty();
            }

            User foundUser = new User(resSet.getLong("id"), email, resSet.getString("password"));

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

        String query = "INSERT INTO " + Main.SCHEMA_NAME + "." + Main.TABLE_NAME + " (email, password) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, newUser.getEmail());
            statement.setString(2, newUser.getPassword());
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
            System.out.println("Update failed! Use SAVE to add user into the table\n");
            return;
        }

        String query = "UPDATE " + Main.SCHEMA_NAME + "." + Main.TABLE_NAME + " SET email = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
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

        String query = "DELETE FROM " + Main.SCHEMA_NAME + "." + Main.TABLE_NAME + " WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
