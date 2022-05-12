package presentation;

import javax.swing.*;
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
    private JButton modifyDescribedProductButton;
    private JButton deleteDescribedProductButton;
    private JTable productsTable;
    private JButton refreshProductsTableButton;
    private JTextField textField1;
    private JCheckBox modifyTitleCheckBox;
    private JCheckBox modifyRatingCheckBox;
    private JCheckBox modifyCaloriesCheckBox;
    private JCheckBox modifyProteinCheckBox;
    private JCheckBox modifyFatCheckBox;
    private JCheckBox modifySodiumCheckBox;
    private JCheckBox modifyPriceCheckBox;
    private JTextField newTitleField;
    private JTextField newRatingField;
    private JTextField newCaloriesField;
    private JTextField newProteinField;
    private JTextField newFatField;
    private JTextField newSodiumField;
    private JTextField newPriceField;
    private JScrollPane tablePane;
    private AdminController adminController;

    public AdminWindow() {
        setContentPane(mainPanel);
        setSize(1000, 600);
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
        modifyDescribedProductButton.addActionListener(adminController);
        deleteDescribedProductButton.addActionListener(adminController);
        refreshProductsTableButton.addActionListener(adminController);

        this.successLabel.setVisible(false);

        //TODO: create more controllers
        //TODO: maybe print the existing products everytime
        modifyTitleCheckBox.addActionListener(adminController);
        modifyRatingCheckBox.addActionListener(adminController);
        modifyCaloriesCheckBox.addActionListener(adminController);
        modifyProteinCheckBox.addActionListener(adminController);
        modifyFatCheckBox.addActionListener(adminController);
        modifySodiumCheckBox.addActionListener(adminController);
        modifyPriceCheckBox.addActionListener(adminController);

        newTitleField.setVisible(false);
        newCaloriesField.setVisible(false);
        newRatingField.setVisible(false);
        newFatField.setVisible(false);
        newPriceField.setVisible(false);
        newProteinField.setVisible(false);
        newSodiumField.setVisible(false);
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

    public JButton getModifyDescribedProductButton() {
        return modifyDescribedProductButton;
    }

    public JButton getDeleteDescribedProductButton() {
        return deleteDescribedProductButton;
    }

    public JTextField getTitleField() {
        return titleField;
    }

    public JTextField getCaloriesField() {
        return caloriesField;
    }

    public JTextField getRatingField() {
        return ratingField;
    }

    public JTextField getProteinField() {
        return proteinField;
    }

    public JTextField getSodiumField() {
        return sodiumField;
    }

    public JTextField getFatField() {
        return fatField;
    }

    public JTextField getPriceField() {
        return priceField;
    }

    public JLabel getSuccessLabel() {
        return successLabel;
    }

    public JTable getProductsTable() {
        return productsTable;
    }

    public JButton getRefreshProductsTableButton() {
        return refreshProductsTableButton;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JTabbedPane getTabbedPane1() {
        return tabbedPane1;
    }

    public JTextField getNewTitleField() {
        return newTitleField;
    }

    public JTextField getNewRatingField() {
        return newRatingField;
    }

    public JTextField getNewCaloriesField() {
        return newCaloriesField;
    }

    public JTextField getNewProteinField() {
        return newProteinField;
    }

    public JTextField getNewFatField() {
        return newFatField;
    }

    public JTextField getNewSodiumField() {
        return newSodiumField;
    }

    public JTextField getNewPriceField() {
        return newPriceField;
    }

    public JCheckBox getModifyTitleCheckBox() {
        return modifyTitleCheckBox;
    }

    public JCheckBox getModifyRatingCheckBox() {
        return modifyRatingCheckBox;
    }

    public JCheckBox getModifyCaloriesCheckBox() {
        return modifyCaloriesCheckBox;
    }

    public JCheckBox getModifyProteinCheckBox() {
        return modifyProteinCheckBox;
    }

    public JCheckBox getModifyFatCheckBox() {
        return modifyFatCheckBox;
    }

    public JCheckBox getModifySodiumCheckBox() {
        return modifySodiumCheckBox;
    }

    public JCheckBox getModifyPriceCheckBox() {
        return modifyPriceCheckBox;
    }
}
