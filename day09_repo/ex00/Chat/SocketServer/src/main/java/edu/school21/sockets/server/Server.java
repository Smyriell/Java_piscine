package edu.school21.sockets.server;

import edu.school21.sockets.models.User;
import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

@Component
public class Server {
    private UsersService usersService;
    private ServerSocket serverSocket;
    private Socket socket;
    private Scanner reader;
    private PrintWriter writer;

    @Autowired
    public Server(UsersService usersService) {
        this.usersService = usersService;
    }

    public void start(int port) {

        try {
            checkPort(port);

            serverSocket = new ServerSocket(port);
            socket = serverSocket.accept();
            reader = new Scanner(socket.getInputStream());
            writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("Hello from Server!");
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
            stop();
        }

        String message;
        String userName = null;
        String password = null;

        while (true) {
            try {
                if (reader.hasNextLine()) {
                    message = reader.nextLine().trim();

                    if (!"signUp".equals(message)) {
                        throw new RuntimeException("Available commands: signUp. Try again:");
                    }
                }

                writer.println("Enter username: ");

                if (reader.hasNextLine()) {
                    userName = reader.nextLine().trim();
                }

                writer.println("Enter password: ");

                if (reader.hasNextLine()) {
                    password = reader.nextLine().trim();
                }

                usersService.signUp(new User(userName, password));

                writer.println("Successful!");
                stop();
            } catch (RuntimeException e) {
                System.err.println(e.getMessage());
                writer.println(e.getMessage());
            }
        }
    }

    private void checkPort(int port) {
        if (port < 1024 || port > 65536) {
            System.err.println("Invalid port number. Must be an Integer type > 1023, max is 65536");
            System.exit(1);
        }
    }

    public void stop() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }

            if (reader != null) {
                reader.close();
            }

            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.exit(0);
    }
}
