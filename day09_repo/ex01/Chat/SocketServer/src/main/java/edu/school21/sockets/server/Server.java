package edu.school21.sockets.server;

import edu.school21.sockets.models.User;
import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class Server {
    private UsersService usersService;
    private ServerSocket serverSocket;
    private List<Client> clientsList = new ArrayList<>();
    private int num = 1;

    @Autowired
    public Server(UsersService usersService) {
        this.usersService = usersService;
    }

    private class Client extends Thread {
        private PrintWriter writer;
        private Scanner reader;
        private Socket socket;
        private String username;
        private String password;
        private boolean isClientActive;

        Client(Socket socket) {
            try {
                this.socket = socket;
                reader = new Scanner(socket.getInputStream());
                writer = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public void run() {
            writer.println("Hello from Server!");

            while (true) {
                writer.println("Available commands:\nsignUp, signIn, exit");

                try {
                    if (reader.hasNextLine()) {
                        String message = reader.nextLine().trim();

                        if ("signUp".equalsIgnoreCase(message)) {
                            if (!acceptUser()) {
                                exitChat();
                                break;
                            }

                            usersService.signUp(new User(username, password));
                            writer.println("User: " + username + " was successfully created!");
                            System.out.println("User: " + username + " was added");
//                        } else if (message.equals("signIn")) {
                        } else if ("signIn".equalsIgnoreCase(message)) {
                            if (!acceptUser()) {
                                exitChat();
                                break;
                            }

                            if (usersService.signIn(username, password)) {
                                writer.println("Authorization successful!");
                                System.out.println("Authorization successful for user: " + username);
                                writer.println("Start messaging");
                                talk();
                                break;
                            } else {
                                writer.println("Authorization failed!");
                                System.out.println("Authorization for user: " + username + " failed");
                            }

                        } else if ("".equals(message)) {
                            continue;
                        } else if ("exit".equals(message)) {
                            exitChat();
                            break;
                        } else {
                            writer.println("Invalid command! Try again:");
                        }
                    }
                } catch (IllegalThreadStateException e) {
                    System.err.println(e.getMessage());
                    writer.println(e.getMessage());
                }
            }
        }

        private boolean acceptUser() {
            writer.println("Enter username: ");
            username = reader.nextLine().trim();

            while (username.equals("")) {
                username = reader.nextLine().trim();
            }

            if (username.equals("exit")) {
                return false;
            }

            writer.println("Enter password: ");
            password = reader.nextLine().trim();

            while (password.equals("")) {
                password = reader.nextLine().trim();
            }

            if (password.equals("exit")) {
                return false;
            }
            return true;
        }

        private void talk() {
            while (true) {
                isClientActive = true;
                String message = reader.nextLine().trim();

                if (message.equals("exit")) {
                    String leavingUserName = this.username;
                    exitChat();
                    clientsList.stream().filter(client -> client.isClientActive).forEach(client ->
                            client.writer.println("User " + leavingUserName + " left us"));
                    break;
                }
                sendMessageToClients(username + ": " + message);
            }
        }

        private void exitChat() {
            try {
                writer.println("You left the chat. Bye-bye!");
                removeClient(this);
                reader.close();
                writer.close();
                socket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void start(int port) {

        try {
            checkPort(port);

            serverSocket = new ServerSocket(port);

            while (true) {
                Socket socket = serverSocket.accept();
                Client client = new Client(socket);
                clientsList.add(client);
                System.out.println("New client connected! Number of clients: " + num++);
                client.start();
            }
        } catch (IllegalThreadStateException | NumberFormatException | IOException e) {
            System.out.println(e.getMessage());
            stop();
        }
    }

    private synchronized void sendMessageToClients(String message) {
        usersService.createMessage(message);
        for (Client client : clientsList) {
            if (client.isClientActive) {
                client.writer.println(message);
            }
        }
//        clientsList.stream().filter(client -> client.isClientActive).forEach(client -> client.writer.println(message));
    }

    private void removeClient(Client client) {
        clientsList.remove(client);
        System.out.println("The user " + client.username + " left chat");

        if (clientsList.isEmpty()) {
            System.out.println("No users in the Chat! Bye-bye!");
            stop();
        }
    }

    private void stop() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.exit(0);
    }

    private void checkPort(int port) {
        if (port < 1024 || port > 65536) {
            System.err.println("Invalid port number. Must be an Integer type > 1023, max is 65536");
            System.exit(1);
        }
    }
}
