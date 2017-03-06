package Database;


import User.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Message.Message;

public class DBTests {


    public static void main(String[] args) {
        String req = "requester=gerrit@gmail.com&wants=inbox&keyword=hiii";
        String requester;
        String wants;
        String keyword;
        Pattern requesterPattern = Pattern.compile("requester=(.+?(?=&))");
        Pattern wantsPattern = Pattern.compile("wants=(.+?(?=&))");
        Pattern keywordPattern = Pattern.compile("keyword=(.+)");
        Matcher requesterPatternMatch = requesterPattern.matcher(req);
        Matcher wantsPatternMatch = wantsPattern.matcher(req);
        Matcher keywordPatternMatch = keywordPattern.matcher(req);

        if (requesterPatternMatch.find()) {
            requester = requesterPatternMatch.group(1);
            System.out.println(requester);

        }

    }

}
