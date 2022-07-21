package ex00;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CheckedFile {
    private static final int BUF_SIZE = 8;

    private byte[] buf = new byte[BUF_SIZE];
    private String signature = "";

    public CheckedFile(String path) {
        try {
            FileInputStream inputStream = new FileInputStream(path);
            inputStream.read(buf, 0, BUF_SIZE);

            for (byte b : buf) {
                int symb = b;

                if (symb < 0) {
                    symb += 256;
                }

                if (Integer.toHexString(symb).length() == 1) {
                    signature += "0" + Integer.toHexString(symb).toUpperCase() + " ";
                } else {
                    signature += Integer.toHexString(symb).toUpperCase() + " ";
                }
            }
            signature = signature.trim();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getSignature() {
        return signature;
    }
}
