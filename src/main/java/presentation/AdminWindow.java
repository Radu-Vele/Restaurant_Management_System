package presentation;

import javax.swing.*;

public class AdminWindow extends JFrame{
    private JPanel mainPanel;
    private AdminController adminController;

    public AdminWindow() {
        setContentPane(mainPanel);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setTitle("Administrator operations");
        adminController = new AdminController(this);
    }
}
