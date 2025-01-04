package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    } // end constructor

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::patchMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByAccountHandler);

        return app;
    }

    /**
     * Account post handler. 
     * 
     * Mathod defines how to add an account anytime a post request is made to 'localhost:8080/regiseter'
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * 
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void postAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        Account account = mapper.readValue(ctx.body(), Account.class);
        Account newAccount = accountService.addAccount(account);

        if (newAccount != null) { // Upon success return the added account as a json
            ctx.json(mapper.writeValueAsString(newAccount));
            ctx.status(200);
        } // end if statement 
        else { // Upon failure return 400
            ctx.status(400);
        } // end else statement 
    } // end postAccountHandler()

    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        Account account = mapper.readValue(ctx.body(), Account.class);
        Account foundAccount = accountService.login(account);

        if (foundAccount != null) { // If account is found return as json
            ctx.json(mapper.writeValueAsString(foundAccount));
            ctx.status(200);
        } // end if statement
        else { // If account is not found return status code 401
            ctx.status(401);
        } // end else statement 
    } // end postLoginHandler()

    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);

        if (addedMessage != null) {
            ctx.json(mapper.writeValueAsString(addedMessage));
            ctx.status(200);
        } // end if statement 
        else {
            ctx.status(400);
        } // end else statement 
    } // end postMessageHandler()

    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    } // end getAllMessagesHandler()

    private void getMessageByIdHandler(Context ctx) {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(message_id);

        if (message != null) {
            ctx.json(message);
        } // end if statement
        else {
            ctx.status(200);
        } // end else statement 
    } // end getMessageByIdHandler()

    private void deleteMessageHandler(Context ctx) throws JsonProcessingException {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.deleteMessage(message_id);

        if (message != null) {
            ctx.json(message);
        } // end if statement
        else {
            ctx.status(200);
        } // end else statement 
    } // end deleteMessageHandler()

    private void patchMessageHandler(Context ctx) throws JsonProcessingException {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> body = mapper.readValue(ctx.body(), Map.class);
        // Use ObjectMapper to convert the json line into a Map where the key and value are both strings. 


        Message message = messageService.updateMessage(message_id, body.get("message_text"));

        if (message != null) {
            ctx.json(message);
        } // end if statement 
        else {
            ctx.status(400);
        } // end else statement

    } // end patchMessageHandler()

    private void getMessagesByAccountHandler(Context ctx) throws JsonProcessingException {
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByAccount(account_id);

        if (messages != null) {
            ctx.json(messages);
        } // end if statement
        else {
            ctx.status(200);
        } // end else statement
    } // end getMessagesByAccountHandler()


}