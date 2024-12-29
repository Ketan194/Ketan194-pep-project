package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

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

        app.start(8080);

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


}