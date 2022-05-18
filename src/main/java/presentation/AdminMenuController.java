package presentation;

import businesslogic.BaseProduct;
import businesslogic.DeliveryService;
import businesslogic.MenuItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller class that deals with the Menu Wizard operations
 */
public class AdminMenuController implements ActionListener {

    AdminWindow adminWindow;
    DeliveryService deliveryService;
    private ActionEvent e;

    public AdminMenuController(AdminWindow adminWindow) {
        this.adminWindow = adminWindow;
        deliveryService = DeliveryService.getInstance();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        adminWindow.getNewItemSuccess().setVisible(false);

        if(e.getSource().equals(adminWindow.getRefreshTableButton())) {
            populateExistingItemsTable();
        }
        else if(e.getSource().equals(adminWindow.getAddSelectedItemsButton())) {
            addSelectedItems();
            populateChosenItemsTable();
        }
        else if(e.getSource().equals(adminWindow.getClearAllButton())) {
            clearAll();
        }
        else if(e.getSource().equals(adminWindow.getClearSelectedItemsButton())) {
            clearAllSelected();
        }
        else if (e.getSource().equals(adminWindow.getCreateNewMenuItemButton())) {
            createCompositeItem();
            adminWindow.getCompositeTitleField().setText("");
        }
    }

    public void populateExistingItemsTable() {
        String[][] data = deliveryService.returnAsTable(deliveryService.getMenuItemsCollection());
        String[] columnHeadings = {"Title", "Rating", "Calories", "Protein", "Fats", "Sodium", "Price"};
        DefaultTableModel defaultTableModel = new DefaultTableModel(data, columnHeadings);
        JTable table = adminWindow.getExistingProductsTable();
        table.setModel(defaultTableModel);
    }

    public void populateChosenItemsTable() {
        String[][] data = deliveryService.returnAsTable(deliveryService.getChosenMenuItems());
        String[] columnHeadings = {"Title", "Rating", "Calories", "Protein", "Fats", "Sodium", "Price"};
        DefaultTableModel defaultTableModel = new DefaultTableModel(data, columnHeadings);
        JTable table = adminWindow.getChosenProductsTable();
        table.setModel(defaultTableModel);
    }

    public void addSelectedItems() {
        try {
            int[] selectedRows = adminWindow.getExistingProductsTable().getSelectedRows();
            MenuItem[] menuItems = getBaseProductsAtSelection(adminWindow.getExistingProductsTable(), selectedRows);

            for(MenuItem menuItem : menuItems) {
                deliveryService.addChosenItem(menuItem);
            }

            adminWindow.getExistingProductsTable().clearSelection();

        } catch (Exception e) {
            ErrorPrompt errorPrompt = new ErrorPrompt(e.getMessage());
        }
    }

    public void clearAll() {
        deliveryService.getChosenMenuItems().clear();
        populateChosenItemsTable();
    }

    public void clearAllSelected() {
        try {
            MenuItem[] menuItems = getBaseProductsAtSelection(adminWindow.getChosenProductsTable(), adminWindow.getChosenProductsTable().getSelectedRows());
            for(MenuItem menuItem : menuItems) {
                deliveryService.getChosenMenuItems().remove(menuItem);
            }
            populateChosenItemsTable();
        } catch (Exception e){
            ErrorPrompt errorPrompt = new ErrorPrompt(e.getMessage());
        }


    }

    public static MenuItem[] getBaseProductsAtSelection(JTable table, int[] selectedRows) throws Exception {
        if (selectedRows.length == 0) {
            throw new Exception("Error! you must select at least an item");
        }
        MenuItem[] menuItems = new MenuItem[selectedRows.length];
        int i = 0;
        for (Integer row : selectedRows) {
            String productTitle = (String) table.getValueAt(row, 0);
            double rating = Double.parseDouble((String) table.getValueAt(row, 1));
            double calories = Double.parseDouble((String) table.getValueAt(row, 2));
            double proteins = Double.parseDouble((String) table.getValueAt(row, 3));
            double fats = Double.parseDouble((String) table.getValueAt(row, 4));
            double sodium = Double.parseDouble((String) table.getValueAt(row, 5));
            double price = Double.parseDouble((String) table.getValueAt(row, 6));

            MenuItem menuItem = new BaseProduct(productTitle, rating, calories, proteins, fats, sodium, price);
            menuItems[i] = menuItem;
            i++;
        }
        return menuItems;
    }

    public void createCompositeItem() {
        try {
            String title = adminWindow.getCompositeTitleField().getText();
            if (title.equals("")) {
                throw new Exception("You must give a name to your composite product!");
            }

            deliveryService.addCompositeProduct(title);
            adminWindow.getNewItemSuccess().setVisible(true);
        } catch (Exception e) {
            ErrorPrompt errorPrompt = new ErrorPrompt(e.getMessage());
        }
    }
}
