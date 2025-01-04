package DAO;

import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    
    /**
     * Adds a message to the database. 
     * 
     * @param message A Message object containing the information that needs to be added to the database. 
     * @return A Message object will all the information that was added to the database, or null if inserting did not work. 
     */
    public Message insertMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection(); // Make connection

        try {
            String sql = "insert into message (posted_by, message_text, time_posted_epoch) values (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // Write and make prepared statement 

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            // Set values in prepaared statement
            
            preparedStatement.executeUpdate(); // Execute sql query

            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys(); // Get result set

            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1); // Get unique message_id
                return new Message(generated_message_id, 
                            message.getPosted_by(), 
                            message.getMessage_text(), 
                            message.getTime_posted_epoch()); // Create new Message object
            } // end if statement 
        } // end try block
        catch(SQLException e){
            System.out.println(e.getMessage());
        } // end catch block 

        return null;
    } // end insertMessage()

    /**
     * Retruns all the messages in the database as a List of Message objects. 
     * 
     * @return All the messages in the database as a List of Message objects, or null if there aren't any. 
     */
    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection(); // Make connection

        List<Message> messages = new ArrayList<>(); // Make list of Message objects

        try {
            String sql = "select * from message";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch")); // Create Message object
                messages.add(message); // Add object to list
            } // end while loop
        } // end try block
        catch (SQLException e) {
            System.out.println(e.getMessage());
        } // end catch block

        return messages;
    } // end getAllMessages()

    /**
     * Finds a message in the database based of its message_id. 
     * 
     * @param id int representation of message_id. 
     * @return A Message object will all the information that was added to the database, or null if there isn't a message with that id. 
     */
    public Message getMessageById(int id) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "select * from message where message_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                return new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch")); // Create Message object
            } // end while loop
        } // end try block
        catch (SQLException e) {
            System.out.println(e.getMessage());
        } // end catch block

        return null;
    } // end getMessageById()

    /**
     * Removes a message from the database using its message_id to find it in the database. 
     * 
     * @param id  int representation of message_id.
     */
    public void deleteMessage(int id) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "delete from message where message_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            preparedStatement.executeQuery();
            return;
        } // end try block
        catch (SQLException e) {
            System.out.println(e.getMessage());
        } // end catch block

        return;
    } // end deleteMessage()

    /**
     * Updates a message in the database by replacing its message_text with new text, finds the message using the message_id. 
     * 
     * @param id int representation of message_id.
     * @param text String of new message_text
     */
    public void updateMessageText(int id, String text) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "update message message_text = ? where message_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, text);
            preparedStatement.setInt(2, id);

            preparedStatement.executeQuery();
            return;
        } // end try block
        catch (SQLException e) {
            System.out.println(e.getMessage());
        } // end catch block

        return;
    } // end updateMessageText()

    /**
     * Retruns all the messages in the database that were posted by a specific user as a List of Message objects. 
     * 
     * @param account_id int representation of the acount_id
     * @return All the messages in the database as a List of Message objects, or null if there aren't any. 
     */
    public List<Message> getMessagesByAccount(int account_id) {
        Connection connection = ConnectionUtil.getConnection(); // Make connection

        List<Message> messages = new ArrayList<>(); // Make list of Message objects

        try {
            String sql = "select * from message where posted_by = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, account_id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch")); // Create Message object
                messages.add(message); // Add object to list
            } // end while loop
        } // end try block
        catch (SQLException e) {
            System.out.println(e.getMessage());
        } // end catch block

        return messages;
    } // end getMessagesByAccount()
}
