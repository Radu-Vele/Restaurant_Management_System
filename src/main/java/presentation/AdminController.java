package presentation;

import businesslogic.DeliveryService;
import utils.AlreadyImportedInitialProducts;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;

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
            addProductPanel();
        }
        else if(e.getSource().equals(adminWindow.getDeleteProductButton())) {

        }
        else if(e.getSource().equals(adminWindow.getModifyProductButton())) {

        }
        else if(e.getSource().equals(adminWindow.getAddDescribedProductButton())) {
            addProduct();
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

    public void addProductPanel() {
        adminWindow.getOperationChoicePanel().setVisible(false);
        adminWindow.getAddPanel().setVisible(true);
    }

    public void addProduct() {
        adminWindow.getAddPanel().setVisible(false);
        adminWindow.getOperationChoicePanel().setVisible(true);
    }

    public void deleteProduct() {
        adminWindow.getOperationChoicePanel().setVisible(false);
    }

    public void modifyProduct() {
        adminWindow.getOperationChoicePanel().setVisible(false);
    }
}
