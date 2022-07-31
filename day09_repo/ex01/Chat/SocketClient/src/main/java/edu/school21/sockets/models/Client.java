package edu.school21.sockets.models;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private Scanner reader;
    private PrintWriter writer;

    public Client(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        reader = new Scanner(socket.getInputStream());
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public void start() {
        WritingThread writingThread = new WritingThread(writer);
        ReadFromServer readingThread = new ReadFromServer(reader, writingThread);

        readingThread.start();
        writingThread.start();

        try {
            readingThread.join();
            writingThread.join();

            stop();
        } catch (InterruptedException | IllegalThreadStateException e) {
            System.out.println(e.getMessage());
        }
    }

    private void stop() {
        try {
            socket.close();
        } catch (IllegalThreadStateException | IOException e) {
            System.out.println(e.getMessage());
        }
        System.exit(0);
    }

    private class ReadFromServer extends Thread {
        WritingThread writingThread;
        private Scanner reader;

        public ReadFromServer(Scanner reader, WritingThread writingThread) {
            this.reader = reader;
            this.writingThread = writingThread;
        }

        @Override
        public void run() {
            try {
                receiveData();
            } catch (IllegalThreadStateException | IOException e) {
                System.out.println(e.getMessage());
            }
        }

        private void receiveData() throws IOException {
            String message;
            while (reader.hasNextLine()) {
                message = reader.nextLine();
                System.out.println(message);

                if ("exit".equals(message)) {
                    reader.close();
                    writingThread.isTyping = false;
                    return;
                }
            }
        }
    }

    private class WritingThread extends Thread {
        private PrintWriter writer;
        private Scanner scanner = new Scanner(System.in);
        private boolean isTyping = true;

        public WritingThread(PrintWriter writer) {
            this.writer = writer;
        }

        @Override
        public void run() {
            try {
                sendData();
            } catch (IllegalThreadStateException e) {
                System.out.println(e.getMessage());
            }
        }

        private void sendData() {
            while (isTyping) {
                String message = scanner.nextLine();
                writer.println(message);

                if ("exit".equals(message)) {
                    System.out.println("You left the chat. Bye-bye!");
                    System.exit(0);
                }
            }
            writer.close();
        }
    }
}
