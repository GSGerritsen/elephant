package Message;

import User.User;

import java.sql.Date;
import java.sql.Time;
public class Message {

    private String from;
    private String[] to;
    private String body;
    private Time timeToLive;

    public Message(String from, String[] to, String body, Time timeToLive) {
        this.from = from;
        this.to = to;
        this.body = body;
        this.timeToLive = timeToLive;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String[] getTo() {
        return to;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Time getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(Time timeToLive) {
        this.timeToLive = timeToLive;
    }
}
