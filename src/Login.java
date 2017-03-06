import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import java.security.Key;
import Database.UserStatements;
import Authentication.Password;

@WebServlet(name = "Login")
public class Login extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Key key = MacProvider.generateKey();
        byte[] key = Files.readAllBytes(Paths.get("/Users/gerritgerritsen/Documents/Java/experiments/keys/hmac.txt"));

        String email = request.getParameter("email");
        String pass = request.getParameter("password");

        // checking credentials against database here
        UserStatements us = new UserStatements();
        Password pw = new Password();
        String hashedPass = us.findUserHashedPass(email);

        if (pw.verifyPassword(pass, hashedPass)) {
            String compactJwt = Jwts.builder()
                    .setSubject(email)
                    .signWith(SignatureAlgorithm.HS512, key)
                    .setExpiration(new Date( System.currentTimeMillis() + 60000 * 60))
                    .compact();
            HttpSession session = request.getSession();
            session.setAttribute("jwt", compactJwt);
            session.setAttribute("isLoggedIn", true);
            response.sendRedirect("/vueHome");
        }
        else {
            System.out.println("Invalid user credentials, please see therapist");
            response.sendRedirect("login.jsp");
        }
    }

}
