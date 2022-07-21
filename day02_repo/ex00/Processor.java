package ex00;

import java.util.Map;

public class Processor {
    private String result = "";

    public Processor() {}

    public void process(Map<String, String> tableOfFormats, String signature) {
        for (Map.Entry<String, String> pair : tableOfFormats.entrySet()) {
            if (signature.startsWith(pair.getValue())) {
                this.result += pair.getKey() + "\n";
            }
        }
    }

    public String getResult() {
        return result;
    }
}
