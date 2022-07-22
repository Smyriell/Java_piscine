package ex03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Url {
    public static final String FILE = "files_urls.txt";

    private boolean downloaded = false;
    private int key = 1;
    private Map<Integer, String> urlList = new TreeMap<>();

    public Url() {
        try {
            List<String> fileList = Files.readAllLines(Paths.get(FILE));

            if (fileList.size() == 0) {
                printError("File \"files_urls.txt\" does not consist any data");
            }

            fileList = fileList.stream().map(s -> s = s.trim()).filter(s -> !s.isEmpty()).collect(Collectors.toList());

            for (String str : fileList) {
                String[] keyNum = str.split("\\s+");
                urlList.put(Integer.parseInt(keyNum[0]), keyNum[1]);
            }
        } catch (IOException e) {
            printError ("Can not open the file with this path: " + e.getMessage());
        }
    }

    public boolean isDownloaded() {
        return this.downloaded;
    }

    public int getUrlsListKey(String str) {
        Set<Map.Entry<Integer, String>> tmpSet = urlList.entrySet();

        for (Map.Entry iter : tmpSet) {
            if (iter.getValue().equals(str)) {
                return (int) iter.getKey();
            }
        }
        return -1;
    }

    public synchronized String getLink() {
        String str;

        if (!urlList.containsKey(key)) {
            return null;
        }

        str = urlList.get(key);
        ++key;

        if (!urlList.containsKey(key)) {
            downloaded = true;
        }
        return str;
    }

    public static void printError(String message) {
        System.err.println(message);
        System.exit(1);
    }
}
