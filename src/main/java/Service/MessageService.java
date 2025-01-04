package Service;

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

    /**
     * Finds and returns a message from the database based on its message_id.
     *
     * @param id int representation of the message_id
     * @return The full message from the database
     */
    public Message getMessageById(int id) {
        return messageDAO.getMessageById(id);
    } // end getMessageById()

    /**
     * Finds a message in the database using its message_id, if it exists, and returns the Message.
     * 
     * @param id int representation of the message_id
     * @return The Message in the database or null if it did not exists. 
     */
    public Message deleteMessage(int id) {
        Message found = getMessageById(id);

        if (found == null) {
            return null;
        } // end if statement
        else {
            return found;
        } // end else statement 
    } // end deleteMessage()

    /**
     * Holds logic needed to update a message in the database with new message_text.
     * 
     * @param id int representation of message_id
     * @param text message_text that needs to replace hold message_text
     * @return Message object after message was updated
     */
    public Message updateMessage(int id, String text) {
        if (text.isEmpty() || text.length() > 255) { // Checks to make sure message isn't empty or too long
            return null;
        } // end if statement

        Message found = getMessageById(id); // Finds message using message_id

        if (found == null) { // If message isn't found return null
            return null;
        } // end if statement 
        else {
            found.setMessage_text(text); // Update message_text in Message object
            messageDAO.updateMessageText(id, text); // Update message_text in database
        } // end else statement 

        return found;
    } // end updateMessage()

    /**
     * Get all the messages that were posted by a specific account as a List of Message objects. 
     * 
     * @param account_id int representation of account_id
     * @return List<Message> holding all the messages that account posted, or null if there aren't any. 
     */
    public List<Message> getMessagesByAccount(int account_id) {
        return messageDAO.getMessagesByAccount(account_id);
    } // end getMessagesByAccount()
    
}
