package Database;

import User.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class UserStatements {

    public void insertUser(User user) {
        String sql = "INSERT INTO User (first_name, last_name, email, hashed_pass) VALUES (?, ?, ?, ?)";
        ConnectionHandling connHandler = new ConnectionHandling();
        Connection connection = connHandler.openConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getHashedPassword());
            statement.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            connHandler.closeConnection(connection);
        }

    }

    public String findUserHashedPass(String email) {
        String sql = "SELECT hashed_pass FROM User WHERE email = ?";
        ConnectionHandling connHandler = new ConnectionHandling();
        Connection connection = connHandler.openConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getString("hashed_pass");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            connHandler.closeConnection(connection);
        }
        return "No user hashed_pass found";
    }

    public int findUserId(String email) {
        String sql = "SELECT id FROM User WHERE email = ?";
        ConnectionHandling connHandler = new ConnectionHandling();
        Connection connection = connHandler.openConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            connHandler.closeConnection(connection);
        }
        return -1;
    }



}
