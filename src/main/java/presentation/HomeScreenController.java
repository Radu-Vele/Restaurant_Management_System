package presentation;

import businesslogic.AccountHolder;
import businesslogic.DeliveryService;
import businesslogic.LoginService;
import businesslogic.MenuItem;
import utils.IncorrectInputException;
import utils.NoSuchAccountException;

import javax.security.auth.login.AccountNotFoundException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.EOFException;
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
    }

    public static void main(String[] args) {
        HomeScreen homeScreen = new HomeScreen();
        loginService = new LoginService();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        try {
            loginService.loadData();
        } catch (Exception exception) {
            ErrorPrompt errorPrompt = new ErrorPrompt("There are no users registered");
        }
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
            String role = homeScreen.getRoleBox().getSelectedItem().toString();

            AccountHolder accountHolder = new AccountHolder(username, password, role);

            if(username.equals("") || password.equals("") || role.equals("Access Rights")) {
                throw new IncorrectInputException("You must complete all the fields");
            }

            if(!loginService.getAccountHolders().contains(accountHolder)) {
                if(loginService.searchUsername(accountHolder.getUsername())) {
                    throw new NoSuchAccountException("There is an account linked to the username, but the selected password and/or role are wrong");
                }
                throw new NoSuchAccountException("There is no user having the specified combination of username, password, and role");
            }

            switch (accountHolder.getRole()) {
                case "Administrator":
                    this.homeScreen.setVisible(false);
                    AdminWindow adminWindow = new AdminWindow();
                    break;
                case "Employee":
                    this.homeScreen.setVisible(false);
                    EmployeeWindow employeeWindow = new EmployeeWindow();
                    break;
                case "Client":
                    this.homeScreen.setVisible(false);
                    ClientWindow clientWindow = new ClientWindow();
                    break;
            }

        } catch (Exception e) {
            ErrorPrompt errorPrompt = new ErrorPrompt(e.getMessage());
        }
    }

    public void createAccountControl() {
        NewAccountWindow newAccountWindow = new NewAccountWindow();
    }
}
