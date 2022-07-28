package school21.spring.service.application;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        uploadTable(context);

        UsersRepository usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);

        System.out.println("\n---INSERT NEW USERS WITH JDBC---");
        User user1 = new User(10L, "smyriell@student.21-school.ru");
        User user2 = new User(20L,"bobby@student.21-school.ru");
        User user3 = new User(30L,"cobby@student.21-school.ru");
        User user4 = new User(40L,"dobby@student.21-school.ru");
        User user5 = new User(50L,"eobby@student.21-school.ru");
        List<User> usersList = new ArrayList<User>(6);
        usersList.add(user1);
        usersList.add(user2);
        usersList.add(user3);
        usersList.add(user4);
        usersList.add(user5);
        usersList.forEach(user -> usersRepository.save(user));
        usersList.forEach(System.out::println);
        usersRepository.update(new User(100L,"fobby@student.21-school.ru"));
        System.out.println(usersRepository.findAll());
//        usersRepository = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
//        System.out.println(usersRepository.findAll());
    }

    private static void uploadTable(ApplicationContext context) {
        DataSource dataSource = context.getBean("hikariDataSource", HikariDataSource.class);
        try (Connection connection = dataSource.getConnection();
             Statement st = connection.createStatement()) {
            st.executeUpdate("DROP TABLE IF EXISTS service.users;");
            st.executeUpdate("CREATE SCHEMA IF NOT EXISTS service;");
            st.executeUpdate("CREATE TABLE IF NOT EXISTS service.users " +
                            "(id SERIAL PRIMARY KEY, email VARCHAR(30)NOT NULL UNIQUE);");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
