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


    // Write a Filter
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Object jwt = session.getAttribute("jwt");
        if (jwt == null) {
            response.sendRedirect("index.jsp");
        }

        byte[] key = Files.readAllBytes(Paths.get("/Users/gerritgerritsen/Documents/Java/experiments/keys/hmac.txt"));
        String currentEmail = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt.toString()).getBody().getSubject();

        PrintWriter out = response.getWriter();
        out.append(currentEmail);
        out.close();
    }
}
