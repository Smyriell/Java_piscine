package ex03;

import java.io.*;
import java.net.URL;

public class MyThread extends Thread {
    public static final int MAX_LEN = 2048;

    private Url url;
    private int threadNumb;

    public MyThread(Url url, int n) {
        this.url = url;
        this.threadNumb = n;
    }

    @Override
    public void run() {
        while (!url.isDownloaded()) {
            String str = url.getLink();
            byte buf[] = new byte[MAX_LEN];
            int bytesRead;
            int fileNumber;
            File file;

            if (str != null) {
                file = getFile(str);

                try (BufferedInputStream inputStream = new BufferedInputStream(new URL(str).openStream());
                     FileOutputStream outputStream = new FileOutputStream(file)) {
                    fileNumber = url.getUrlsListKey(str);
                    System.out.println("Thread-" + threadNumb + " start download file number " + fileNumber);

                    while ((bytesRead = inputStream.read(buf)) != -1) {
                        outputStream.write(buf, 0, bytesRead);
                    }

                    System.out.println("Thread-" + threadNumb + " finish download file number " + fileNumber);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private File getFile(String str) {
        File file;
        String[] strings = str.split("/");

        file = new File(strings[strings.length - 1]);

        return file;
    }
}
