package school21.spring.service.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import school21.spring.service.application.Main;
import school21.spring.service.config.TestApplicationConfig;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UsersServiceImplTest {
    private static EmbeddedDatabase dataSource;
    private static UsersService usersServiceJdbc;
    private static UsersService usersServiceJdbcTemplate;

    @BeforeAll
    static void before() {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
        dataSource = context.getBean("dataSource",EmbeddedDatabase.class);
        usersServiceJdbc = context.getBean("usersServiceJdbc", UsersService.class);
        usersServiceJdbcTemplate = context.getBean("usersServiceJdbcTemplate", UsersService.class);
    }


    @BeforeEach
    private void init() {
        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            st.executeUpdate("DROP TABLE IF EXISTS " + Main.SCHEMA_NAME + "." + Main.TABLE_NAME +" CASCADE;");
            st.executeUpdate("CREATE SCHEMA IF NOT EXISTS " + Main.SCHEMA_NAME +";");
            st.executeUpdate("CREATE TABLE IF NOT EXISTS " + Main.SCHEMA_NAME + "." + Main.TABLE_NAME + " " +
                    "(id INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY, " +
                    "email VARCHAR(30)NOT NULL UNIQUE, password VARCHAR(100));");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @AfterEach
    void close() {
        dataSource.shutdown();
    }

    @ParameterizedTest
    @ValueSource(strings = {"bobby@student.21-school.ru", "cobby@student.21-school.ru", "dobby@student.21-school.ru"})
    public void testDoesSignedUpOnJdbcReturnPassword(String email) {
        assertNotNull(usersServiceJdbc.signUp(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"bobby@student.21-school.ru", "cobby@student.21-school.ru", "dobby@student.21-school.ru"})
    public void testDoesSignedUpOnJdbcTemplateReturnPassword(String email) {
        assertNotNull(usersServiceJdbcTemplate.signUp(email));
    }
}