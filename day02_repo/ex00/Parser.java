package ex00;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {
    private static final Map<String, String> tableOfFormats = new HashMap<>();

    public static void parseFile(List<String> signatures) {
        for (String n : signatures) {
            if (!n.isEmpty()) {
                String[] line = n.split(",");
                tableOfFormats.put(line[0].trim(), line[1].trim());
            }
        }
    }

    public static Map<String, String> getFormatsTable() {
        return tableOfFormats;
    }


}
