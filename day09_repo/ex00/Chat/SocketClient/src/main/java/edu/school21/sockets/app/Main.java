package edu.school21.sockets.app;

import edu.school21.sockets.models.Client;

import java.io.IOException;

public class Main {
    public static final String IP_NUMB = "127.0.0.1";

    static int getPort(String[] args) {
        if (args.length != 1) {
            System.err.println("Specify the server port using --server-port=%d");
            System.exit(1);
        }

        if (!args[0].matches("--server-port=\\d+")) {
            System.err.println("Invalid argument format. Specify the server port using --server-port=%d");
            System.exit(1);
        }

        String[] str = args[0].split("=");
        return Integer.parseInt(str[1]);
    }

    public static void main(String[] args) {
        try {
            int port = getPort(args);

            Client client = new Client(IP_NUMB, port);
            client.start();
        } catch (NumberFormatException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}