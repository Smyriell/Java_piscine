package school21.spring.service.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {
    private final String FIND_BY_ID_QUERY = "SELECT * FROM service.users WHERE id = ";
    private final String FIND_ALL_QUERY = "SELECT * FROM service.users";
    private final String FIND_BY_EMAIL_QUERY = "SELECT * FROM service.users WHERE email = ?";
    private final String SAVE_QUERY = "INSERT INTO service.users (email) VALUES (?)";
    private final String UPDATE_QUERY = "UPDATE service.users SET email = ? WHERE id = ?";
    private final String DELETE_QUERY = "DELETE FROM service.users WHERE id = ?";

    private final JdbcTemplate jdbcTemplateObject;

    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void save(User entity) {

    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }
}
