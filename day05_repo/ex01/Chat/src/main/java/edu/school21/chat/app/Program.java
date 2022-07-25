package edu.school21.chat.app;

import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.DataSourceJDBC;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    public static void main(String[] args)  {
        DataSourceJDBC dataSource = new DataSourceJDBC();
        updateData("schema.sql", dataSource);
        updateData("data.sql", dataSource);
        MessagesRepository repository = new MessagesRepositoryJdbcImpl(dataSource.getDataSource());
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter a message ID");

            try {
                String str = scanner.nextLine();

                if ("exit".equals(str)) {
                    System.exit(0);
                }
                long id = Long.parseLong(str);
                Optional<Message> message = repository.findById(id);

                if (message != null && message.isPresent()) {
                    System.out.println(message.get());
                } else {
                    System.out.println("Message was not found");
                }
            } catch (SQLException | NumberFormatException e) {
               if (e instanceof NumberFormatException) {
                   System.out.print("Wrong id");
               }
                System.out.println(e.getMessage());
            }
        }
    }

    private static void updateData(String file, DataSourceJDBC dataSource) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            InputStream input = Program.class.getClassLoader().getResourceAsStream(file);
            Scanner scanner = new Scanner(input).useDelimiter(";");

            while (scanner.hasNext()) {
                statement.executeUpdate(scanner.next().trim());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
