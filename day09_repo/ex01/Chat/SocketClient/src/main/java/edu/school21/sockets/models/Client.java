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

    private class ReadingThread extends Thread {
        WritingThread writingThread;
        private Scanner reader;

        public ReadingThread(Scanner reader, WritingThread writingThread) {
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
            while (reader.hasNextLine()) {
                String message = reader.nextLine();
                System.out.println(message);

                if (null != message && message.equals("exit")) {
                    reader.close();
                    writingThread.isTyping = false;
                    break;
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

                if (null != message && message.equals("exit")) {
                    System.exit(0);
                }
            }
            writer.close();
        }
    }

    public void start() {
        WritingThread writingThread = new WritingThread(writer);
        ReadingThread readingThread = new ReadingThread(reader, writingThread);

        writingThread.start();
        readingThread.start();

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
            if (socket != null) {
                socket.close();
            }

            if (reader != null) {
                reader.close();
            }

            if (writer != null) {
                writer.close();
            }

        } catch (IllegalThreadStateException | IOException e) {
            System.out.println(e.getMessage());
        }
        System.exit(0);
    }
}
