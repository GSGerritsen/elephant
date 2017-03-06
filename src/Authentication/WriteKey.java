package Authentication;

// Need to figure out a better way to do this, but this will work for now. Writing key from MacProvider to a file
// to be used for JWT signing/verifying.

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
            /*
            File statText = new File("keys/hmac.txt");
            FileOutputStream is = new FileOutputStream(statText);
            OutputStreamWriter osw = new OutputStreamWriter(is);
            Writer w = new BufferedWriter(osw);
            w.write(hmacKey.getEncoded().toString());
            w.close();

            String encodedKey = Base64.getEncoder().encodeToString(hmacKey.getEncoded());
            System.out.println(encodedKey);
            */

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
