package presentation;

import businesslogic.AccountHolder;
import utils.AlreadyExistingUsername;
import utils.IncorrectInputException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewAccountController implements ActionListener {
    NewAccountWindow newAccountWindow;

    public NewAccountController(NewAccountWindow newAccountWindow) {
        this.newAccountWindow = newAccountWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String username = newAccountWindow.getUsernameField().getText();
            String password = newAccountWindow.getPasswordField().getText();
            String password1 = newAccountWindow.getConfirmPasswordField().getText();
            String role = newAccountWindow.getRoleBox().getSelectedItem().toString();

            if(username.equals("") || password.equals("") || password1.equals("") || role.equals("What permissions do you want to have?")) {
                throw new IncorrectInputException("You must complete all the fields");
            }

            if(!password.equals(password1)) {
                throw new IncorrectInputException("You entered passwords that do not match");
            }

            AccountHolder accountHolder = new AccountHolder(username, password, role);

            if(HomeScreenController.loginService.searchUsername(username)) {
                throw new AlreadyExistingUsername("There already exists a user having that username");
            }

            HomeScreenController.loginService.addAccountHolder(accountHolder);
            this.newAccountWindow.getUserSuccessLabel().setVisible(true);

        } catch (Exception exception) {
            ErrorPrompt errorPrompt = new ErrorPrompt(exception.getMessage());
        }
    }

}
