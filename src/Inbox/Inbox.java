package Inbox;


public class Inbox {

    private int messageId;
    private String body;
    private String sentAt;
    private int opened;
    private String fromFirstName;
    private String fromLastName;
    private String fromEmail;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSentAt() {
        return sentAt;
    }

    public void setSentAt(String sentAt) {
        this.sentAt = sentAt;
    }

    public int getOpened() {
        return opened;
    }

    public void setOpened(int opened) {
        this.opened = opened;
    }

    public String getFromFirstName() {
        return fromFirstName;
    }

    public void setFromFirstName(String fromFirstName) {
        this.fromFirstName = fromFirstName;
    }

    public String getFromLastName() {
        return fromLastName;
    }

    public void setFromLastName(String fromLastName) {
        this.fromLastName = fromLastName;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public Inbox(int messageId, String body, String sentAt, int opened, String fromFirstName, String fromLastName, String fromEmail) {
        this.messageId = messageId;
        this.body = body;
        this.sentAt = sentAt;
        this.opened = opened;
        this.fromFirstName = fromFirstName;
        this.fromLastName = fromLastName;
        this.fromEmail = fromEmail;
    }
}
