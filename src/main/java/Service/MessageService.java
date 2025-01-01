package Service;


import Model.Account;
import DAO.AccountDAO;
import Model.Message;
import DAO.MessageDAO;

import java.util.List;


public class MessageService {

    private AccountDAO accountDAO;
    private MessageDAO messageDAO;
    /**
     * no-args constructor for creating a new MessageService with a new AccountDAO and MessageDAO.
     * There is no need to change this constructor.
     */
    public MessageService(){
        accountDAO = new AccountDAO();
        messageDAO = new MessageDAO();
    } // end constructor

    /**
     * Checks if the message that needs to be added meets basic requirements, such as not being empty, being less than 255 chars long, and having an associtated account. 
     * If the message is valid then it is added to the database. 
     * 
     * @param message
     * @return An instance of the message that was added with all the fields filled in. 
     */
    public Message addMessage(Message message) {
        if (message.getMessage_text().isEmpty() || message.getMessage_text().length() > 255) { // Check if message text is valid
            return null; // Return null if not
        } // end if statement 
        if (accountDAO.getAccountById(message.getPosted_by()) == null) { // Check if corresponding Account exists 
            return null; // Return null if there isn't 
        } // end if statement 

        return messageDAO.insertMessage(message); // Insert message and return added Message object
    } // end addMessage()

    /**
     * Method to get a List of all the messages in the database. 
     * 
     * @return List of all messages in database
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    } // end getAllMessages()
    
}
