package presentation;

import javax.swing.*;

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
    private NewAccountController newAccountController;

    public NewAccountWindow() {
        setContentPane(mainPanel);
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        newAccountController = new NewAccountController(this);
        setTitle("Create a new account");
        joinNowButton.addActionListener(this.newAccountController);
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
}
