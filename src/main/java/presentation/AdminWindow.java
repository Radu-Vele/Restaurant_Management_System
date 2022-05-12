package presentation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AdminWindow extends JFrame{
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JButton importInitialSetOfButton;
    private JButton addProductButton;
    private JButton deleteProductButton;
    private JButton modifyProductButton;
    private JTabbedPane tabbedPane2;
    private JPanel operationChoicePanel;
    private JPanel operationsTab;
    private JLabel importLabel;
    private JScrollPane addPanel;
    private JScrollPane deletePanel;
    private JScrollPane editPanel;
    private JButton addDescribedProductButton;
    private JTextField titleField;
    private JTextField ratingField;
    private JTextField caloriesField;
    private JTextField proteinField;
    private JTextField fatField;
    private JTextField sodiumField;
    private JTextField priceField;
    private JLabel successLabel;
    private AdminController adminController;

    public AdminWindow() {
        setContentPane(mainPanel);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setTitle("Administrator operations");
        adminController = new AdminController(this);

        importInitialSetOfButton.addActionListener(adminController);
        importLabel.setText("");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                adminController.closingRoutine();
                dispose();
            }
        });

        this.addPanel.setVisible(false);
        this.deletePanel.setVisible(false);
        this.editPanel.setVisible(false);
        addProductButton.addActionListener(adminController);
        deleteProductButton.addActionListener(adminController);
        deleteProductButton.addActionListener(adminController);
        modifyProductButton.addActionListener(adminController);
        addDescribedProductButton.addActionListener(adminController);

        this.successLabel.setVisible(false);
        //TODO: create more controllers
        //TODO: maybe print the existing products everytime
    }

    public JButton getImportInitialSetOfButton() {
        return importInitialSetOfButton;
    }

    public JLabel getImportLabel() {
        return importLabel;
    }

    public JButton getAddProductButton() {
        return addProductButton;
    }

    public JButton getDeleteProductButton() {
        return deleteProductButton;
    }

    public JButton getModifyProductButton() {
        return modifyProductButton;
    }

    public JScrollPane getAddPanel() {
        return addPanel;
    }

    public JScrollPane getDeletePanel() {
        return deletePanel;
    }

    public JScrollPane getEditPanel() {
        return editPanel;
    }

    public JPanel getOperationChoicePanel() {
        return operationChoicePanel;
    }

    public JButton getAddDescribedProductButton() {
        return addDescribedProductButton;
    }
}
