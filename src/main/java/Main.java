import Controller.SocialMediaController;
import Model.Account;
import io.javalin.Javalin;

import Model.Account;
import Service.AccountService;

/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */
public class Main {
    public static void main(String[] args) {
        SocialMediaController controller = new SocialMediaController();
        AccountService accountService = new AccountService();
        Javalin app = controller.startAPI();
        app.start(8080);

        Account testAccount = new Account("testuser2", "password");
        Account addedAccount = accountService.addAccount(testAccount);

        if (addedAccount == null) {
            System.out.println("Nothing added!!");
        }
        else {
            System.out.println(addedAccount.toString());
        }

        Account loggedInAccount = accountService.login(testAccount);

        if (loggedInAccount == null) {
            System.out.println("Nothing added!!");
        }
        else {
            System.out.println(loggedInAccount.toString());
        }

        app.stop();
    }
}
