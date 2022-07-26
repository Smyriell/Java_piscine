package edu.school21.chat.app;

import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.DataSourceConnection;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    public static final String PATH_TO_DATA = "src/main/resources/data.sql";
    public static final String PATH_TO_SCHEMA = "src/main/resources/schema.sql";

    public static void main(String[] args)  {
        DataSourceConnection dataSource = new DataSourceConnection();

        uploadSQLData(PATH_TO_SCHEMA, dataSource);
        uploadSQLData(PATH_TO_DATA, dataSource);

        MessagesRepository repository = new MessagesRepositoryJdbcImpl(dataSource.getDataSource());

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a message ID");
        String str = scanner.nextLine();

        while (!"exit".equals(str)) {
            try {
                long id = Long.parseLong(str);
                Optional<Message> message = repository.findById(id);

                if (message.isPresent()) {
                    System.out.println(message.get());
                } else {
                    System.out.println("Message was not found");
                }
            } catch (SQLException | NumberFormatException e) {
               if (e instanceof NumberFormatException) {
                   System.out.print("ID should have a Long type: ");
               }

                System.out.println(e.getMessage());
            }

            System.out.println("\nEnter a searching message ID:");
            str = scanner.nextLine();
        }
    }

    private static void uploadSQLData(String file, DataSourceConnection dataSource) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
            FileInputStream stream = new FileInputStream(file)) {
            Scanner scanner = new Scanner(stream).useDelimiter(";");

            while (scanner.hasNext()) {
                statement.executeUpdate(scanner.next().trim());
            }
        } catch (IOException | SecurityException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
