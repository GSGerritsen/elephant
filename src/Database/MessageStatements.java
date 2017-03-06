package Database;

import Message.Message;
import User.User;
import Inbox.Inbox;
import com.mysql.cj.api.jdbc.Statement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageStatements {

    // This is probably very bad in some way
    public void insertMessage(Message message) {
        String sqlMessage = "INSERT INTO message (body, sent_at, time_to_live, sent_by) VALUES (?, ?, ?, ?)";
        String sqlMessageDetails = "INSERT INTO message_details (message_id, sent_to, opened) VALUES (?, ?, ?)";
        String sqlUserId = "SELECT id FROM User WHERE email = ?";
        ConnectionHandling connHandler = new ConnectionHandling();
        Connection connection = connHandler.openConnection();
        try {
            // get the sending user's id:
            PreparedStatement statement = connection.prepareStatement(sqlUserId);
            statement.setString(1, message.getFrom());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("id");

                PreparedStatement statementMessage = connection.prepareStatement(sqlMessage, Statement.RETURN_GENERATED_KEYS);
                statementMessage.setString(1, message.getBody());
                statementMessage.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                statementMessage.setTime(3, message.getTimeToLive());
                statementMessage.setInt(4, userId);
                statementMessage.executeUpdate();
                ResultSet generatedId = statementMessage.getGeneratedKeys();
                if (generatedId.next()) {

                    for (int i = 0; i < message.getTo().length; i++) {
                        PreparedStatement statementId = connection.prepareStatement(sqlUserId);
                        statementId.setString(1, message.getTo()[i]);
                        ResultSet resultSet = statementId.executeQuery();
                        if (resultSet.next()) {

                            PreparedStatement statementMessageDetails = connection.prepareStatement(sqlMessageDetails);
                            statementMessageDetails.setInt(1, (int)generatedId.getLong(1));
                            statementMessageDetails.setInt(2, resultSet.getInt("id"));
                            statementMessageDetails.setBoolean(3, false);
                            statementMessageDetails.execute();

                        }
                        else {
                            System.out.println("No user found");
                        }
                    }
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connHandler.closeConnection(connection);
        }
    }

    public List<Inbox> findInboxMessages(String email) {
        List<Inbox> ret = new ArrayList<>();
        String sql = "SELECT m.id, m.body, m.sent_at, md.opened, u.first_name, u.last_name, u.email FROM message AS m JOIN message_details AS md ON m.id = md.message_id JOIN user AS u ON m.sent_by = u.id WHERE md.sent_to = ?";
        String sqlUserId = "SELECT id FROM User WHERE email = ?";
        ConnectionHandling connHandler = new ConnectionHandling();
        Connection connection = connHandler.openConnection();
        try {
            PreparedStatement statementUserId = connection.prepareStatement(sqlUserId);
            statementUserId.setString(1, email);
            ResultSet rs = statementUserId.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("id");
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, userId);
                ResultSet resultSet = statement.executeQuery();
                while(resultSet.next()) {
                    Inbox inbox = new Inbox(resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getInt(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7));
                    ret.add(inbox);
                }
                return ret;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connHandler.closeConnection(connection);
        }
        return ret;
    }

    public List<Inbox> findSentMessages(String email) {
        List<Inbox> ret = new ArrayList<>();
        String sql = "SELECT m.id, m.body, m.sent_at, md.opened, u.first_name, u.last_name, u.email FROM message AS m JOIN message_details AS md ON m.id = md.message_id JOIN user AS u ON md.sent_to = u.id WHERE m.sent_by = ?";
        String sqlUserId = "SELECT id FROM User WHERE email = ?";
        ConnectionHandling connHandler = new ConnectionHandling();
        Connection connection = connHandler.openConnection();
        try {
            PreparedStatement statementUserId = connection.prepareStatement(sqlUserId);
            statementUserId.setString(1, email);
            ResultSet rs = statementUserId.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("id");
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, userId);
                ResultSet resultSet = statement.executeQuery();
                while(resultSet.next()) {
                    Inbox inbox = new Inbox(resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getInt(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7));
                    ret.add(inbox);
                }
                return ret;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connHandler.closeConnection(connection);
        }
        return ret;
    }

    public List<Inbox> findMessagesByKeyword(String email, String keyword) {
        List<Inbox> ret = new ArrayList<>();
        String sql = "SELECT m.id, m.body, m.sent_at, md.opened, u.first_name, u.last_name, u.email FROM message AS m JOIN message_details AS md ON m.id = md.message_id JOIN user AS u ON m.sent_by = u.id WHERE md.sent_to = ? AND m.body LIKE ?";
        String sqlUserId = "SELECT id FROM User WHERE email = ?";
        ConnectionHandling connHandler = new ConnectionHandling();
        Connection connection = connHandler.openConnection();
        try {
            PreparedStatement statementUserId = connection.prepareStatement(sqlUserId);
            statementUserId.setString(1, email);
            ResultSet rs = statementUserId.executeQuery();
            if (rs.next()) {
                keyword = "%" + keyword + "%";
                int userId = rs.getInt("id");
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, userId);
                statement.setString(2, keyword);
                ResultSet resultSet = statement.executeQuery();
                while(resultSet.next()) {
                    Inbox inbox = new Inbox(resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getInt(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7));
                    ret.add(inbox);
                }
                return ret;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connHandler.closeConnection(connection);
        }
        return ret;
    }

    public void markAsRead(int messageDetailsId, String email) {
        String sql = "UPDATE message_details SET opened = 1, opened_at = ? WHERE message_id = ? AND sent_to = ?";
        String sqlUserId = "SELECT id FROM User WHERE email = ?";
        ConnectionHandling connHandler = new ConnectionHandling();
        Connection connection = connHandler.openConnection();
        try {
            PreparedStatement statementUserId = connection.prepareStatement(sqlUserId);
            statementUserId.setString(1, email);
            ResultSet rs = statementUserId.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("id");
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                statement.setInt(2, messageDetailsId);
                statement.setInt(3, userId);
                statement.execute();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
