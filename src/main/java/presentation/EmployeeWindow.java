package presentation;

import javax.swing.*;
import java.awt.*;

public class EmployeeWindow extends JFrame{
    private JPanel mainPanel;
    private EmployeeController employeeController;

    public EmployeeWindow() {
        setContentPane(mainPanel);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setTitle("Employee Operations");
        employeeController = new EmployeeController(this);
    }
}
