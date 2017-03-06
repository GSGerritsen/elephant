package Authentication;



import io.jsonwebtoken.impl.crypto.MacProvider;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.Key;
import java.util.Base64;

public class WriteKey {

    public static void main(String[] args) {
        try {
            SecretKey hmacKey = KeyGenerator.getInstance("HmacSha512").generateKey();
            FileOutputStream fos = new FileOutputStream("keys/hmac.txt");
            fos.write(hmacKey.getEncoded());
            fos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
