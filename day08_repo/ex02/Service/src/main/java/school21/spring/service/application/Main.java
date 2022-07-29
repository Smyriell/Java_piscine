package school21.spring.service.application;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.services.UsersService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static final String SCHEMA_NAME = "service";
    public static final String TABLE_NAME = "users";

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("school21.spring.service");

        uploadTable(context);

        UsersService usersService = context.getBean("usersService", UsersService.class);
        UsersRepository usersJdbc = context.getBean("jdbc", UsersRepository.class);
        UsersRepository usersJdbcTempl = context.getBean("jdbcTemplate", UsersRepository.class);

        System.out.println("\n----------INSERT NEW USERS WITH JDBC_TEMPLATE: CHECK PASSWORDS----------");
        System.out.println(usersService.signUp("sheriffmyriell@student.21-school.ru"));
        System.out.println(usersService.signUp("bobby@student.21-school.ru"));
        System.out.println(usersService.signUp("cobby@student.21-school.ru"));
        System.out.println(usersService.signUp("dobby@student.21-school.ru"));
        System.out.println(usersService.signUp("eobby@student.21-school.ru"));

        System.out.println("\n----------FIND_ALL----------");
        System.out.println("--JDBC--");
        System.out.println(usersJdbc.findAll());
        System.out.println("--JDBC_TEMPLATE--");
        System.out.println(usersJdbcTempl.findAll());

        System.out.println("\n----------FIND_BY_ID----------");
        System.out.println("--JDBC--");
        System.out.println(usersJdbc.findById(2l));
        System.out.println("--JDBC_TEMPLATE--");
        System.out.println(usersJdbcTempl.findById(2l));

        System.out.println("\n----------FIND_BY_EMAIL----------");
        System.out.println("--JDBC--");
        System.out.println(usersJdbc.findByEmail("dobby@student.21-school.ru"));
        System.out.println("--JDBC_TEMPLATE--");
        System.out.println(usersJdbcTempl.findByEmail("dobby@student.21-school.ru"));

        System.out.println("\n----------DELETE 1, 2 ----------");
        usersJdbc.delete(1l);
        usersJdbcTempl.delete(2l);

        System.out.println("\n----------CHECK AFTER DELETE----------");
        System.out.println("--JDBC--");
        System.out.println(usersJdbc.findAll());
        System.out.println("--JDBC_TEMPLATE--");
        System.out.println(usersJdbcTempl.findAll());

        context.getBean("hikariDataSource", HikariDataSource.class).close();
    }

    private static void uploadTable(ApplicationContext context) {
        DataSource dataSource = context.getBean("hikariDataSource", HikariDataSource.class);
        try (Connection connection = dataSource.getConnection();
             Statement st = connection.createStatement()) {
            st.executeUpdate("DROP TABLE IF EXISTS " + SCHEMA_NAME + "." + TABLE_NAME +" CASCADE;");
            st.executeUpdate("CREATE SCHEMA IF NOT EXISTS " + SCHEMA_NAME +";");
            st.executeUpdate("CREATE TABLE IF NOT EXISTS " + SCHEMA_NAME + "." + TABLE_NAME + " " +
                            "(id SERIAL PRIMARY KEY, email VARCHAR(100)NOT NULL UNIQUE, password VARCHAR(100));");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
