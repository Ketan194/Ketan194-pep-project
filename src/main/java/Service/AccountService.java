package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService {

    private AccountDAO accountDAO;
    /**
     * no-args constructor for creating a new AuthorService with a new AuthorDAO.
     * There is no need to change this constructor.
     */
    public AccountService(){
        accountDAO = new AccountDAO();
    } // end constructor

    public Account addAccount(Account account) {
        if (account.getUsername().isEmpty() || account.getPassword().length() < 4) { // Make sure username is not empty and password is at least 4 char long
            return null; // return null if bad account
        } // end if statement 
        if (accountDAO.getAccountByUsername(account.getUsername()) != null) { // If username and password meet basic requiremnts then check if the username already exists. 
            return null; // return null if username is already in use
        } // end else if statement 

        // If all requirements are met then add the account to the datbase and return an instance of the account with the filled in data.
        return accountDAO.insertAccount(account);
    } // end addAccount()

    public Account login(Account account) {
        Account temp = accountDAO.getAccountByUsername(account.getUsername());
        if (temp == null) {
            return null;
        }
        if (account.getPassword().equals(temp.getPassword())) {
            return temp;
        } // end if statement
        else {
            return null;
        } // end else statement
    } // end login()
    
}
