package presentation;

import businesslogic.AccountHolder;
import businesslogic.DeliveryService;
import businesslogic.LoginService;
import businesslogic.MenuItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Controller class for the Log in Window
 */
public class HomeScreenController implements ActionListener {
    private HomeScreen homeScreen;
    public static LoginService loginService;

    public HomeScreenController(HomeScreen homeScreen) {
        this.homeScreen = homeScreen;
        try {
            //TODO check if there is such a file, otherwise create it
            loginService = new LoginService(); //reinitialize the login service
            loginService.loadData();
        } catch (Exception e) {
            //TODO: prompt
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HomeScreen homeScreen = new HomeScreen();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (homeScreen.getLogInButton().equals(source)) {
            logInControl();
        } else if (homeScreen.getNoAccountYetClickButton().equals(source)) {
            createAccountControl();
        }
    }

    /**
     * Perform login operations based on the existing data loaded into the system.
     */
    public void logInControl() {
        try {
            String username = homeScreen.getUsernameField().getText();
            String password = homeScreen.getPasswordField().getText();
            String role = homeScreen.getPasswordField().getText();

            AccountHolder accountHolder = new AccountHolder(username, password, role);


        } catch (Exception e) {

        }
    }

    public void createAccountControl() {
        NewAccountWindow newAccountWindow = new NewAccountWindow();
    }
}
