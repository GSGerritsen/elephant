package Authentication;

import org.mindrot.jbcrypt.BCrypt;

public class Password {

    public static String hashPassword(String pass) {
        String salt = BCrypt.gensalt(16);
        return BCrypt.hashpw(pass, salt);
    }

    public boolean verifyPassword(String pass, String hashedPass) {

        if (hashedPass == null || !hashedPass.startsWith("$2a$")) {
            throw new java.lang.IllegalArgumentException("Provided hash is invalid");
        }

        return BCrypt.checkpw(pass, hashedPass);
    }


}
