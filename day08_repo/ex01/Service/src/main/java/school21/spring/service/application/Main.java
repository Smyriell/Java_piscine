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
import java.util.List;

public class Main {
    public static final String SCHEMA_NAME = "service";
    public static final String TABLE_NAME = "users";

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

        uploadTable(context);

        UsersRepository usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);
        UsersRepository usersRepositoryT = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);

        System.out.println("\n----------TEST NO EXIST USER UPDATE----------\n");
        System.out.println("--JDBC--");
        usersRepository.update(new User(100L,"fobby@student.21-school.ru"));
        System.out.println("--JDBC_TEMPLATE--");
        usersRepositoryT.update(new User(100L,"fobby@student.21-school.ru"));

        System.out.println("\n----------INSERT NEW USERS WITH JDBC----------");
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
        usersList.forEach(usersRepository::save);

        System.out.println("\n----------FIND_ALL AND RESULT OF INSERT----------");
        System.out.println("--JDBC--");
        System.out.println(usersRepository.findAll());
        System.out.println("--JDBC_TEMPLATE--");
        System.out.println(usersRepository.findAll());

        System.out.println("\n----------INSERT NEW USERS WITH JDBC_TEMPLATE----------");
        User user6 = new User(10L, "gobby@student.21-school.ru");
        User user7 = new User(20L,"hobby@student.21-school.ru");
        User user8 = new User(30L,"iobby@student.21-school.ru");

        usersList = new ArrayList<User>(5);
        usersList.add(user6);
        usersList.add(user7);
        usersList.add(user8);
        usersList.forEach(System.out::print);
        usersList.forEach(usersRepositoryT::save);

        System.out.println("\n----------RESULT OF INSERT----------");
        System.out.println("--JDBC--");
        System.out.println(usersRepository.findAll());
        System.out.println("--JDBC_TEMPLATE--");
        System.out.println(usersRepository.findAll());

        System.out.println("\n----------UPDATE EXIST USERS WITH ID: 1 7----------");
        user1.setEmail("UPDATED_EMAIL1");
        user7.setEmail("UPDATED_EMAIL7");
        usersRepository.update(user1);
        usersRepositoryT.update(user7);

        System.out.println("\n----------RESULT OF UPDATE----------");
        System.out.println("--JDBC--");
        System.out.println(usersRepository.findAll());
        System.out.println("--JDBC_TEMPLATE--");
        System.out.println(usersRepository.findAll());

        System.out.println("\n----------FIND BY ID: 3 4----------");
        System.out.println("exist JDBC:\n" + usersRepository.findById(3l));
        System.out.println("exist JDBC_TEMPLATE:\n" + usersRepositoryT.findById(4l));
        System.out.print("doesn't exist id JDBC: ");
        System.out.println(usersRepository.findById(30l));
        System.out.print("doesn't exist id JDBC_TEMPL: ");
        System.out.println(usersRepositoryT.findById(30l) + "\n");
        System.out.print("negative value id JDBC: ");
        System.out.println(usersRepository.findById(-3l));
        System.out.print("negative value id JDBC_TEMP: ");
        System.out.println(usersRepositoryT.findById(-3l) + "\n");
        System.out.print("NULL JDBC: ");
        System.out.println(usersRepository.findById(null));
        System.out.print("NULL JDBC_TEMPL: ");
        System.out.println(usersRepositoryT.findById(null)+ "\n");

        System.out.println("----------DELETE USERS WITH ID: 3 8----------\n");
        usersRepository.delete(3L);
        usersRepositoryT.delete(8L);

        System.out.println("----------RESULT OF DELETE----------");
        System.out.println("--JDBC--");
        System.out.println(usersRepository.findAll());
        System.out.println("--JDBC_TEMPLATE--");
        System.out.println(usersRepository.findAll());

        System.out.println("\n---FIND BY EMAIL(bobby, UPDATED_EMAIL1)---");
        System.out.println("exists JDBC:\n" + usersRepository.findByEmail("bobby@student.21-school.ru"));
        System.out.println("exists JDBC_TEMPLATE:\n" + usersRepositoryT.findByEmail("UPDATED_EMAIL1") + "\n");
        System.out.print("empty JDBC: ");
        System.out.println(usersRepository.findByEmail(""));
        System.out.print("null JDBC_TEMPLATE: ");
        System.out.println(usersRepositoryT.findByEmail(null) + "\n");
        System.out.print("wrong email JDBC: ");
        System.out.println(usersRepository.findByEmail("wrong email"));
        System.out.print("wrong email JDBC_TEMPLATE: ");
        System.out.println(usersRepositoryT.findByEmail("wrong email"));

    }

    private static void uploadTable(ApplicationContext context) {
        DataSource dataSource = context.getBean("hikariDataSource", HikariDataSource.class);
        try (Connection connection = dataSource.getConnection();
             Statement st = connection.createStatement()) {
            st.executeUpdate("DROP TABLE IF EXISTS " + SCHEMA_NAME + "." + TABLE_NAME +" CASCADE;");
            st.executeUpdate("CREATE SCHEMA IF NOT EXISTS " + SCHEMA_NAME +";");
            st.executeUpdate("CREATE TABLE IF NOT EXISTS " + SCHEMA_NAME + "." + TABLE_NAME + " " +
                            "(id SERIAL PRIMARY KEY, email VARCHAR(30)NOT NULL UNIQUE);");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
