package presentation;

import javax.swing.*;

public class ErrorPrompt extends JFrame{
    private JPanel mainPanel;
    private JLabel errorMessageLabel;

    public ErrorPrompt(String message) {
        setContentPane(mainPanel);
        setSize(600, 100);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        errorMessageLabel.setText(message);
    }
}
