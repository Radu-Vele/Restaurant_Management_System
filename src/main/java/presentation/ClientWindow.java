package presentation;

import javax.swing.*;

public class ClientWindow extends JFrame{
    private JPanel mainPanel;
    private ClientController clientController;

    public ClientWindow() {
        setContentPane(mainPanel);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setTitle("Client operations");
        clientController = new ClientController(this);
    }
}
