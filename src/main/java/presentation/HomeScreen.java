package presentation;

import javax.swing.*;
import java.awt.*;

public class HomeScreen extends JFrame{
    private JPanel mainPanel;
    private JTextField usernameField;
    private JTextField passwordField;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JButton logInButton;
    private JComboBox roleBox;
    private JButton noAccountYetClickButton;

    HomeScreenController homeScreenController;

    public HomeScreen() {
        setContentPane(mainPanel);
        setSize(350, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        noAccountYetClickButton.setBackground(Color.GRAY);
        setVisible(true);
        setTitle("Log in the application");
        homeScreenController = new HomeScreenController(this);
        noAccountYetClickButton.addActionListener(this.homeScreenController);
    }

    public JButton getLogInButton() {
        return logInButton;
    }

    public JButton getNoAccountYetClickButton() {
        return noAccountYetClickButton;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JTextField getPasswordField() {
        return passwordField;
    }

    public JComboBox getRoleBox() {
        return roleBox;
    }
}
