import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Message.Message;
import Database.MessageStatements;
import Inbox.Inbox;


@WebServlet(name = "Messages")
public class Messages extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //String email = request.getParameter("email");
        try {
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = request.getReader().readLine()) != null) {
                sb.append(s);
            }

            JSONObject message = new JSONObject(sb.toString());
            String[] toArray = new String[1];
            toArray[0] = (String)message.get("to");
            Message insertMessage = new Message( (String)message.get("from"), toArray, (String) message.get("message"), new Time(60000 * 5));
            MessageStatements messageStatements = new MessageStatements();
            messageStatements.insertMessage(insertMessage);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1st regex: requester=(.+[&]) (chop off the last character)
        // 2nd regex: wants=(\w+)
        List<Inbox> inboxList = new ArrayList<Inbox>();
        String req = request.getQueryString();
        String requester;
        String wants;
        String keyword;
        Pattern requesterPattern = Pattern.compile("requester=(.+?(?=&))");
        Pattern wantsPattern = Pattern.compile("wants=(.+?(?=&))");
        Pattern keywordPattern = Pattern.compile("keyword=(.+)");
        Matcher requesterPatternMatch = requesterPattern.matcher(req);
        Matcher wantsPatternMatch = wantsPattern.matcher(req);
        Matcher keywordPatternMatch = keywordPattern.matcher(req);

        if (requesterPatternMatch.find() && wantsPatternMatch.find() && keywordPatternMatch.find()) {
            requester = requesterPatternMatch.group(1);
            wants = wantsPatternMatch.group(1);
            keyword = keywordPatternMatch.group(1);
            if (keyword.equals("ignore_search")) {
                keyword = "";
            }
            MessageStatements messageStatements = new MessageStatements();
            switch (wants) {
                case "inbox": inboxList = messageStatements.findInboxMessages(requester);
                    break;
                case "sent": inboxList = messageStatements.findSentMessages(requester);
                    break;
                case "searchedMessages": inboxList = messageStatements.findMessagesByKeyword(requester, keyword);
                    break;
            }

            // Writing the returned inboxList as JSON to the response
            response.getWriter().write("[");
            for (int i = 0; i < inboxList.size(); i++) {
                ObjectMapper mapper = new ObjectMapper();
                String jsonObj = mapper.writeValueAsString(inboxList.get(i));
                response.getWriter().write(jsonObj);
                // A check so that a comma isn't added for the last object, making the response writer contain valid JSON
                if (i != inboxList.size() - 1) {
                    response.getWriter().write(", ");
                }
            }
            response.getWriter().write("]");
            response.getWriter();
        }
    }
}
