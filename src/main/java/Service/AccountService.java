package Service;

import Model.Account;
import DAO.AccountDAO;
public class AccountService {

    private AccountDAO accountDAO;
    /**
     * no-args constructor for creating a new AccountService with a new AccountDAO and MessageDAO.
     * There is no need to change this constructor.
     */
    public AccountService(){
        accountDAO = new AccountDAO();
    } // end constructor

    /**
     * Holds logic for adding an account to the database. 
     * Checks if the account username is empty or already in use. 
     * Checks if password is at least 4 char long. 
     * If account cannot be added to the database for any reason returns null, otherwise returns the account that was added as an Account. 
     * 
     * @param account Account that needs to be added
     * @return Account that was added, or null if account cannot be added. 
     */
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

    /**
     * Gives user access to an account. 
     * User can only access account if both the username and password match an account in the database exactly. 
     * 
     * @param account Account that user is trying to access.
     * @return Account that user accessed, or null if user cannot access acount. 
     */
    public Account login(Account account) {
        Account temp = accountDAO.getAccountByUsername(account.getUsername());
        if (temp == null) { // Checks if account exists in database
            return null; // Returns null if account does not exists
        } // end if statement 
        if (account.getPassword().equals(temp.getPassword())) { // Checks if password matches
            return temp; // Returns account if password matches
        } // end if statement
        else {
            return null; // Returns null if account does not match
        } // end else statement
    } // end login()
    
}
