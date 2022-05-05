package presentation;

import businesslogic.AccountHolder;

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

            if(password.equals(password1)) {
                //TODO: custom exception for not matching password
                throw new Exception();
            }

            if(username.equals("") || password.equals("") || password1.equals("")) {
                //TODO: custom exception
                throw new Exception();
            }

            String role = newAccountWindow.getRoleBox().getSelectedItem().toString();

            AccountHolder accountHolder = new AccountHolder(username, password, role);
            HomeScreenController.loginService.addAccountHolder(accountHolder);

        } catch (Exception exception) {
            //Prompt
            exception.printStackTrace();
        }
    }

}
