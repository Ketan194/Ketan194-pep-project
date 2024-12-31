package DAO;


import Model.Account;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    
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
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch()); // Create new Message object
            } // end if statement 
        } // end try block
        catch(SQLException e){
            System.out.println(e.getMessage());
        } // end catch block 

        return null;
    }
}
