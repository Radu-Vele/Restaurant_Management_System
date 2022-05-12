package presentation;

import businesslogic.BaseProduct;
import businesslogic.DeliveryService;
import businesslogic.MenuItem;
import utils.AlreadyImportedInitialProducts;
import utils.IncorrectInputException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.regex.Pattern;

public class AdminController implements ActionListener {
    AdminWindow adminWindow;
    DeliveryService deliveryService;

    public AdminController(AdminWindow adminWindow) {
        this.adminWindow = adminWindow;
        deliveryService = new DeliveryService();
        //import initial data
        try {
            deliveryService.loadData();
        } catch (Exception e) {
            ErrorPrompt errorPrompt = new ErrorPrompt("Error: unable to import initial data");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(adminWindow.getImportInitialSetOfButton())) {
            importProducts();
        }
        else if(e.getSource().equals(adminWindow.getAddProductButton())) {
            triggerProductPanel("ADD");
        }
        else if(e.getSource().equals(adminWindow.getDeleteProductButton())) {
            triggerProductPanel("DELETE");
        }
        else if(e.getSource().equals(adminWindow.getModifyProductButton())) {
            triggerProductPanel("MODIFY");
        }
        else if(e.getSource().equals(adminWindow.getAddDescribedProductButton())) {
            addProduct();
        }
        else if(e.getSource().equals(adminWindow.getDeleteDescribedProductButton())) {
            deleteProduct();
        }
        else if(e.getSource().equals(adminWindow.getModifyDescribedProductButton())) {
            modifyProduct();
        }
        else if (e.getSource().equals(adminWindow.getRefreshProductsTableButton())) {
            populateProductsTable();
        }
        else if(e.getSource().equals(adminWindow.getModifyTitleCheckBox()) ||
                e.getSource().equals(adminWindow.getModifyCaloriesCheckBox()) ||
                e.getSource().equals(adminWindow.getModifyFatCheckBox()) ||
                e.getSource().equals(adminWindow.getModifyPriceCheckBox()) ||
                e.getSource().equals(adminWindow.getModifySodiumCheckBox()) ||
                e.getSource().equals(adminWindow.getModifyProteinCheckBox()) ||
                e.getSource().equals(adminWindow.getModifyRatingCheckBox())){
            manageComboBox((JCheckBox) e.getSource());
        }
    }

    public void closingRoutine() {
        try {
            this.deliveryService.saveData();
        } catch (Exception e) {
            ErrorPrompt errorPrompt = new ErrorPrompt("Error: Unable to save the existing data in the menu");
        }
    }

    public void importProducts() {
        try {
            deliveryService.importProducts();
            adminWindow.getImportLabel().setText("Successfully imported the initial set of products");

        }catch(FileNotFoundException f) {
            ErrorPrompt errorPrompt = new ErrorPrompt(f.getMessage());
        } catch (AlreadyImportedInitialProducts f) {
            adminWindow.getImportLabel().setText(f.getMessage());
        }
    }

    /**
     * Sets visible the GUI panel of the operation given as param
     * @param operation
     */
    public void triggerProductPanel(String operation) {
        adminWindow.getSuccessLabel().setVisible(false);
        adminWindow.getImportLabel().setVisible(false);
        adminWindow.getOperationChoicePanel().setVisible(false);

        switch (operation) {
            case "ADD" :
                adminWindow.getAddPanel().setVisible(true);
                break;
            case "DELETE":
                adminWindow.getDeletePanel().setVisible(true);
                break;
            case "MODIFY":
                adminWindow.getEditPanel().setVisible(true);
                break;
        }
    }

    public void addProduct() {
        adminWindow.getAddPanel().setVisible(false);
        try {
            if(adminWindow.getTitleField().getText().equals("") ||
                    adminWindow.getRatingField().getText().equals("") ||
                    adminWindow.getCaloriesField().getText().equals("") ||
                    adminWindow.getProteinField().equals("") ||
                    adminWindow.getFatField().equals("") ||
                    adminWindow.getSodiumField().equals("") ||
                    adminWindow.getPriceField().equals("")) {
                throw new IncorrectInputException("Error! You must complete all the fields");
            }
            String title = adminWindow.getTitleField().getText();
            double rating = Double.parseDouble(adminWindow.getRatingField().getText());
            double calories = Double.parseDouble(adminWindow.getCaloriesField().getText());
            double proteins = Double.parseDouble(adminWindow.getProteinField().getText());
            double fats = Double.parseDouble(adminWindow.getFatField().getText());
            double sodium = Double.parseDouble(adminWindow.getSodiumField().getText());
            double price = Double.parseDouble(adminWindow.getPriceField().getText());

            MenuItem newMenuItem = new BaseProduct(title, rating, calories, proteins, fats, sodium, price);

            deliveryService.manageProduct("ADD", newMenuItem);

            adminWindow.getSuccessLabel().setVisible(true);

        } catch (IncorrectInputException e) {
            ErrorPrompt errorPrompt = new ErrorPrompt(e.getMessage());
        } catch (NumberFormatException e) {
            ErrorPrompt errorPrompt = new ErrorPrompt("Error! You must input real number values for all fields except the title!");
        } catch (Exception e) {
            ErrorPrompt errorPrompt = new ErrorPrompt(e.getMessage());
        }
        adminWindow.getOperationChoicePanel().setVisible(true);
    }

    public void deleteProduct() {
        try {
            MenuItem toDelete = getProductFromSelectedLine();
            if(deliveryService.getMenuItemsCollection().contains(toDelete)) {
                deliveryService.getMenuItemsCollection().remove(toDelete);
            }
            else
                throw new Exception("Error! The item you want to delete is not in the menu anymore. Try to refresh the table!");
            adminWindow.getSuccessLabel().setVisible(true);
        } catch (Exception e) {
            ErrorPrompt errorPrompt = new ErrorPrompt(e.getMessage());
        }
        adminWindow.getDeletePanel().setVisible(false);
        adminWindow.getOperationChoicePanel().setVisible(true);
    }

    public void populateProductsTable() {
        String[][] data = deliveryService.setToTable();
        String[] columnHeadings = {"Title", "Rating", "Calories", "Protein", "Fats", "Sodium", "Price"};
        DefaultTableModel defaultTableModel = new DefaultTableModel(data, columnHeadings);
        JTable table = adminWindow.getProductsTable();
        table.setModel(defaultTableModel);
    }

    /**
     * Manages whether a text field for inputting a new value should be visible or not based on the check box selection
     * @param checkBox
     */
    public void manageComboBox(JCheckBox checkBox) {
        JTextField textField = adminWindow.getTitleField();

        if(checkBox.equals(adminWindow.getModifyTitleCheckBox()))
            textField = adminWindow.getNewTitleField();
        else if (checkBox.equals(adminWindow.getModifyCaloriesCheckBox()))
            textField = adminWindow.getNewCaloriesField();
        else if (checkBox.equals(adminWindow.getModifyFatCheckBox()))
            textField = adminWindow.getNewFatField();
        else if (checkBox.equals(adminWindow.getModifyPriceCheckBox()))
            textField = adminWindow.getNewPriceField();
        else if (checkBox.equals(adminWindow.getModifySodiumCheckBox()))
            textField = adminWindow.getNewSodiumField();
        else if(checkBox.equals(adminWindow.getModifyProteinCheckBox()))
            textField = adminWindow.getNewProteinField();
        else if(checkBox.equals(adminWindow.getModifyRatingCheckBox()))
            textField = adminWindow.getNewRatingField();

        if(checkBox.isSelected()) {
            textField.setVisible(true);
        }
        else
            textField.setVisible(false);
    }

    public void modifyProduct() {
        try {
            if (adminWindow.getModifyTitleCheckBox().isSelected() && adminWindow.getNewTitleField().getText().equals("Set new title value") ||
                    adminWindow.getModifyRatingCheckBox().isSelected() && adminWindow.getNewRatingField().getText().equals("Set new rating value") ||
                    adminWindow.getModifyCaloriesCheckBox().isSelected() && adminWindow.getNewCaloriesField().getText().equals("Set new calories value") ||
                    adminWindow.getModifyProteinCheckBox().isSelected() && adminWindow.getNewProteinField().getText().equals("Set new protein value") ||
                    adminWindow.getModifySodiumCheckBox().isSelected() && adminWindow.getNewSodiumField().getText().equals("Set new sodium value") ||
                    adminWindow.getModifyFatCheckBox().isSelected() && adminWindow.getNewFatField().getText().equals("Set new fat value") ||
                    adminWindow.getModifyPriceCheckBox().isSelected() && adminWindow.getNewPriceField().getText().equals("Set new price value")) {
                throw new IncorrectInputException("Error! You must complete all checked fields!");
            }

            MenuItem dummy = getProductFromSelectedLine();

            String productTitle = dummy.getTitle();
            double calories = dummy.getCalories();
            double proteins = dummy.getProteins();
            double sodium = dummy.getSodium();
            double fats = dummy.getFats();
            double rating = dummy.getRating();
            double price = dummy.getPrice();

            if(adminWindow.getModifyTitleCheckBox().isSelected()) {
                productTitle = adminWindow.getNewTitleField().getText();
            }
            if(adminWindow.getModifyCaloriesCheckBox().isSelected()) {
                calories = Double.parseDouble(adminWindow.getNewCaloriesField().getText());
            }
            if(adminWindow.getModifyRatingCheckBox().isSelected()) {
                rating = Double.parseDouble(adminWindow.getNewRatingField().getText());
            }
            if(adminWindow.getModifyProteinCheckBox().isSelected()) {
                proteins = Double.parseDouble(adminWindow.getNewProteinField().getText());
            }
            if(adminWindow.getModifyFatCheckBox().isSelected()) {
                fats = Double.parseDouble(adminWindow.getNewFatField().getText());
            }
            if(adminWindow.getModifySodiumCheckBox().isSelected()) {
                sodium = Double.parseDouble(adminWindow.getNewSodiumField().getText());
            }
            if(adminWindow.getModifyPriceCheckBox().isSelected()) {
                price = Double.parseDouble(adminWindow.getNewPriceField().getText());
            }

            if (deliveryService.getMenuItemsCollection().contains(dummy))
                deliveryService.getMenuItemsCollection().remove(dummy);
            else
                throw new Exception("Error! The item doesn't exist anymore in the table. Try to refresh it!");


            MenuItem toAdd = new BaseProduct(productTitle, calories, rating, proteins, fats, sodium, price);

            deliveryService.getMenuItemsCollection().add(toAdd);

            adminWindow.getSuccessLabel().setVisible(true);

        } catch (NumberFormatException e) {
            ErrorPrompt errorPrompt = new ErrorPrompt("Error! You must input real number values for all fields except the title!");
        } catch (Exception e) {
            ErrorPrompt errorPrompt = new ErrorPrompt(e.getMessage());
        }


        adminWindow.getEditPanel().setVisible(false);
        adminWindow.getOperationChoicePanel().setVisible(true);
    }

    public MenuItem getProductFromSelectedLine() throws Exception{

        int row = adminWindow.getProductsTable().getSelectedRow();
        if(row == -1) {
            throw new Exception("Error! You must select the product you wish to edit from the table");
        }

        String productTitle = (String) adminWindow.getProductsTable().getValueAt(row, 0);
        double rating = Double.parseDouble((String) adminWindow.getProductsTable().getValueAt(row, 1));
        double calories = Double.parseDouble((String) adminWindow.getProductsTable().getValueAt(row, 2));
        double proteins = Double.parseDouble((String) adminWindow.getProductsTable().getValueAt(row, 3));
        double fats = Double.parseDouble((String) adminWindow.getProductsTable().getValueAt(row, 4));
        double sodium = Double.parseDouble((String) adminWindow.getProductsTable().getValueAt(row, 5));
        double price = Double.parseDouble((String) adminWindow.getProductsTable().getValueAt(row, 6));

        MenuItem dummy = new BaseProduct(productTitle, rating, calories, proteins,fats, sodium, price);

        return  dummy;
    }

}
