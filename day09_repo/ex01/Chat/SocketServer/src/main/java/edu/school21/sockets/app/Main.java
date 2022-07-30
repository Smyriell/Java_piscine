package edu.school21.sockets.app;

import edu.school21.sockets.server.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static final String SCHEMA_NAME = "service";
    public static final String TABLE_USER_NAME = "users";
    public static final String TABLE_NAME = "messages";

    static int getPort(String[] args) {
        if (args.length != 1) {
            System.err.println("Specify the server port using --port=%d");
            System.exit(1);
        }

        if (!args[0].matches("--port=\\d+")) {
            System.err.println("Invalid argument format. Specify the port using --port=%d");
            System.exit(1);
        }

        String[] str = args[0].split("=");
        return Integer.parseInt(str[1]);
    }

    public static void main(String[] args) {
        try {
            int port = getPort(args);

            ApplicationContext context = new AnnotationConfigApplicationContext("edu.school21.sockets");

            Server server = context.getBean(Server.class);
            server.start(port);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }
}
