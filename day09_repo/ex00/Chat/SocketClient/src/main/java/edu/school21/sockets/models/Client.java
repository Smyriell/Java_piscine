package edu.school21.sockets.models;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private Scanner reader;
    private PrintWriter writer;
    private final Scanner scanner = new Scanner(System.in);

    public Client(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        reader = new Scanner(socket.getInputStream());
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public void receiveData() {
        String message;

        if (reader.hasNextLine()) {
            message = reader.nextLine();
            System.out.println(message);

            if (message.equals("Successful!")) {
                stop();
            }
        }
    }

    public void sendData() {
        if (scanner.hasNextLine()) {
            String message = scanner.nextLine();
            writer.println(message);
        }
    }

    public void start() {
        while (true) {
            receiveData();
            sendData();
        }
    }

    private void stop() {
        try {
            if (socket != null) {
                socket.close();
            }

            if (reader != null) {
                reader.close();
            }

            if (writer != null) {
                writer.close();
            }

            if (scanner != null) {
                scanner.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.exit(0);
    }
}
