import io.jsonwebtoken.Jwts;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;


@WebServlet(name = "Home")
public class Home extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // check if authenticated
    }

    // Write a Filter
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Object jwt = session.getAttribute("jwt");
        if (jwt == null) {
            response.sendRedirect("index.jsp");
        }
        // Need to verify the JWT here (need to find a way to store the key securely)
        // And then use response.setAttribute('userEmail') etc to set the current user's email
        // in the Vue component's data.
        // Then both Compose and Reply will use this part of the data as the "from" field.
        // The "to" field will use either a message being replied to, or just entering it into the To field from
        // Compose. This should all be enough to get messages inserted properly into message + message_details.
        byte[] key = Files.readAllBytes(Paths.get("/Users/gerritgerritsen/Documents/Java/experiments/keys/hmac.txt"));
        String currentEmail = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt.toString()).getBody().getSubject();
        System.out.println(currentEmail);

        PrintWriter out = response.getWriter();
        out.append(currentEmail);
        out.close();
    }
}
