package DAO;

import Model.Account;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    /**
     * Inserts an Account into the Account database using a prepared statement.
     *  
     * @param account An instance of the account that we want to be added 
     * @return A new instance of the account along with its account_id
     */
    public Account insertAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection(); // Make connection

        try {
            String sql = "insert into account (username, password) values (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // Write and make prepared statement 

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            // Set values in prepaared statement
            
            preparedStatement.executeUpdate(); // Execute sql query

            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys(); // Get result set

            if (pkeyResultSet.next()) {
                int generated_account_id = (int) pkeyResultSet.getLong(1); // Get unique account_id
                return new Account(generated_account_id, account.getUsername(), account.getPassword()); // Create new Account object
            } // end if statement 
        } // end try block
        catch (SQLException e) {
            System.out.println(e.getMessage());
        } // end catch block 

        return null;
    } // end insertAccount()

    /**
     * Helper function that finds if an accoount in the databse has a specific user name. 
     * Can be used when adding a new account to the data base as well as when _________. 
     * 
     * @param username The username to loook for in the database.
     * @return An Account object that is created from information in database  or  null if no such account exists. 
     */
    public Account getAccountByUsername(String username) {
        Connection connection = ConnectionUtil.getConnection(); // Make connection
        try {
            String sql = "select * from account where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery(); // Execute query and get result set

            while (rs.next()) { // When there is something in the result set it should be the Account in the database
                Account account = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password")); 
                // Create an Account instance based on information in database
                return account; // return Account instance
            } // end while loop
        } // end try block
        catch(SQLException e) {
            System.out.println(e.getMessage());
        } // end catch block

        return null; // If an account with that username does not exist then return null
    } // end getAccountByUsername()

    public Account getAccountById(int id) {
        Connection connection = ConnectionUtil.getConnection(); // Make connection

        try {
            String sql = "select * from account where account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery(); // Executre query and get result set

            while (rs.next()) {
                Account account = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                // Create an Account based on information in database
                return account; // return Account instance
            } // end while loop
        } // end try block
        catch (SQLException e) {
            System.out.println(e.getMessage());
        } // end catch block

        return null;
    } // end getAccountByID()

    /* 
    public Account getAccountByUsernameAndPassword(Account account) {
        Connection connection = ConnectionUtil.getConnection(); // Make connection

        try {
            String sql = "select * from account where username = ? and password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            
            ResultSet rs = preparedStatement.executeQuery(); // Execute query and get result set

            while(rs.next()){ // When there is something in the result set it should be the Account in the database
                Account foundAccount = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password")); 
                // Create an Account instance based on information in database
                return foundAccount; // return Account instance
            } // end while loop
        } // end try block
        catch(SQLException e){
            System.out.println(e.getMessage());
        } // end catch block

        return null; // If an account with that username does not exist then return null
    } // end getAccountByUsernameAndPassword()
    */
}
