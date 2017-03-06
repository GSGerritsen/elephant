import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.sql.Time;
import java.util.Date;

import Authentication.Password;
import Database.MessageStatements;
import Message.Message;
import User.User;
import Database.UserStatements;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

@WebServlet(name = "Signup")
public class Signup extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        byte[] key = Files.readAllBytes(Paths.get("/Users/gerritgerritsen/Documents/Java/experiments/keys/hmac.txt"));


        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String pw = request.getParameter("password");

        // Do some input checking here

        String hashedPass = Password.hashPassword(pw);
        User user = new User(firstName, lastName, email, hashedPass);

        UserStatements us = new UserStatements();
        us.insertUser(user);


        String compactJwt = Jwts.builder()
                .setSubject(user.getEmail())
                .signWith(SignatureAlgorithm.HS512, key)
                .setExpiration(new Date( System.currentTimeMillis() + 60000 * 5))
                .compact();
        HttpSession session = request.getSession();
        session.setAttribute("jwt", compactJwt);
        session.setAttribute("isLoggedIn", true);

        String[] toArray = new String[1];
        toArray[0] = email;

        Message insertMessage = new Message( "elephant@elephant.com", toArray, "Welcome to Elephant!", new Time(60000 * 60));
        MessageStatements messageStatements = new MessageStatements();
        messageStatements.insertMessage(insertMessage);


        response.sendRedirect("/vueHome");
    }
}
