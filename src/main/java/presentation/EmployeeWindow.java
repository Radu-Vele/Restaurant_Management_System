package presentation;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EmployeeWindow extends JFrame{
    private JPanel mainPanel;
    private JButton markAsDoneButton;
    private JList ordersList;
    private JLabel numberOfOrdersLabel;
    private EmployeeController employeeController;

    public EmployeeWindow() {
        setContentPane(mainPanel);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setTitle("Employee Operations");
        employeeController = new EmployeeController(this);

        markAsDoneButton.addActionListener(employeeController);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                AdminCRUDController.closingRoutine();
                dispose();
            }
        });
    }

    public JList getOrdersList() {
        return ordersList;
    }

    public JButton getMarkAsDoneButton() {
        return markAsDoneButton;
    }

    public JLabel getNumberOfOrdersLabel() {
        return numberOfOrdersLabel;
    }
}
