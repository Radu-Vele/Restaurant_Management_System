package presentation;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * GUI class for creating a window where the user can create an account
 */
public class NewAccountWindow extends JFrame{
    private JPanel mainPanel;
    private JPanel formPanel;
    private JTextField usernameField;
    private JTextField passwordField;
    private JTextField confirmPasswordField;
    private JButton joinNowButton;
    private JComboBox roleBox;
    private JLabel userSuccessLabel;
    private NewAccountController newAccountController;

    public NewAccountWindow() {
        setContentPane(mainPanel);
        setSize(450, 350);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    HomeScreenController.loginService.saveData();
                } catch (Exception exception) {
                    System.out.println("Unable to save login data");
                }
                setVisible(false);
            }
        });
        setVisible(true);
        newAccountController = new NewAccountController(this);
        setTitle("Create a new account");
        joinNowButton.addActionListener(this.newAccountController);
        userSuccessLabel.setVisible(false);
    }

    public JTextField getPasswordField() {
        return passwordField;
    }

    public JTextField getConfirmPasswordField() {
        return confirmPasswordField;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JComboBox getRoleBox() {
        return roleBox;
    }

    public JLabel getUserSuccessLabel() {
        return userSuccessLabel;
    }
}
