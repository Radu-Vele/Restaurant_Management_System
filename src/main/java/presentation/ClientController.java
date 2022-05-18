package presentation;

import businesslogic.DeliveryService;
import businesslogic.MenuItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

public class ClientController implements ActionListener {

    ClientWindow clientWindow;
    DeliveryService deliveryService;

    public ClientController(ClientWindow clientWindow) {
        this.clientWindow = clientWindow;
        deliveryService = DeliveryService.getInstance();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(clientWindow.getGoToCartButton())) {
            clientWindow.getSearchAndOrderPanel().setVisible(false);
            clientWindow.getMyCartPanel().setVisible(true);
        }
        else if(e.getSource().equals(clientWindow.getAddMoreItemsButton())) {
            clientWindow.getSearchAndOrderPanel().setVisible(true);
            clientWindow.getMyCartPanel().setVisible(false);
        }
        else if(e.getSource().equals(clientWindow.getSearchButton())) {
            populateSearchTable();
        }
        else if(e.getSource().equals(clientWindow.getAddSelectedButton())) {
            addItemsToOrder();
            populateChosenProductTable();
            clientWindow.getProductsTable().clearSelection();
            computePrice();
        }

        else if(e.getSource().equals(clientWindow.getDiscardOrderButton())) {
            clearAllOrderItems();
            populateChosenProductTable();
            computePrice();
        }
        else if(e.getSource().equals(clientWindow.getDeleteSelectedProductButton())) {
            deleteSelectedProducts();
            populateChosenProductTable();
            computePrice();
        }
        else if(e.getSource().equals(clientWindow.getFinishOrderButton())) {
            finishOrder();
            clearAllOrderItems();
            populateChosenProductTable();
            computePrice();
            //TODO: make it work to make multiple orders in one instance
        }
    }

    /**
     * Populates the search table based on the selected filters
     */
    public void populateSearchTable() {
        try {
            Collection<MenuItem> menuItems = deliveryService.getMenuItemsCollection();
            if (clientWindow.getTitleCheckBox().isSelected()) {
                if (clientWindow.getSearchTitleField().getText().equals("")) {
                    throw new Exception("Error! If you select a filter, you must input some text!");
                }
                menuItems = deliveryService.filterByTitle(menuItems, clientWindow.getSearchTitleField().getText());
            }

            if (clientWindow.getRatingCheckBox().isSelected()) {
                if (clientWindow.getSearchRatingField().getText().equals("")) {
                    throw new Exception("Error! If you select a filter, you must input some text!");
                }
                double wantedRating = Double.parseDouble(clientWindow.getSearchRatingField().getText());
                menuItems = deliveryService.filterGreaterThanDouble(menuItems, wantedRating, "RATING");
            }

            if (clientWindow.getCaloriesCheckBox().isSelected()) {
                if (clientWindow.getRatingCheckBox().getText().equals("")) {
                    throw new Exception("Error! If you select a filter, you must input some text!");
                }
                double wantedCalories = Double.parseDouble(clientWindow.getSearchCaloriesField().getText());
                menuItems = deliveryService.filterGreaterThanDouble(menuItems, wantedCalories, "CALORIES");
            }

            if (clientWindow.getProteinCheckBox().isSelected()) {
                if (clientWindow.getSearchProteinField().getText().equals("")) {
                    throw new Exception("Error! If you select a filter, you must input some text!");
                }
                double wantedProtein = Double.parseDouble(clientWindow.getSearchProteinField().getText());
                menuItems = deliveryService.filterGreaterThanDouble(menuItems, wantedProtein, "PROTEIN");
            }

            if (clientWindow.getFatsCheckBox().isSelected()) {
                if (clientWindow.getSearchFatField().getText().equals("")) {
                    throw new Exception("Error! If you select a filter, you must input some text!");
                }
                double wantedFat = Double.parseDouble(clientWindow.getSearchFatField().getText());
                menuItems = deliveryService.filterLessThanDouble(menuItems, wantedFat, "FATS");
            }

            if (clientWindow.getSodiumCheckBox().isSelected()) {
                if (clientWindow.getSearchSodiumField().getText().equals("")) {
                    throw new Exception("Error! If you select a filter, you must input some text!");
                }
                double wantedSodium = Double.parseDouble(clientWindow.getSearchSodiumField().getText());
                menuItems = deliveryService.filterLessThanDouble(menuItems, wantedSodium, "SODIUM");
            }

            if (clientWindow.getPriceCheckBox().isSelected()) {
                if (clientWindow.getSearchPriceField().getText().equals("")) {
                    throw new Exception("Error! If you select a filter, you must input some text!");
                }
                double wantedPrice = Double.parseDouble(clientWindow.getSearchPriceField().getText());
                menuItems = deliveryService.filterLessThanDouble(menuItems, wantedPrice, "PRICE");
            }

            String[][] data = deliveryService.returnAsTable(menuItems);
            String[] columnHeadings = {"Title", "Rating", "Calories", "Protein", "Fats", "Sodium", "Price"};
            DefaultTableModel defaultTableModel = new DefaultTableModel(data, columnHeadings);
            JTable table = clientWindow.getProductsTable();
            table.setModel(defaultTableModel);

        } catch (Exception e) {
            ErrorPrompt errorPrompt = new ErrorPrompt(e.getMessage());
        }
    }

    public void addItemsToOrder() {
        try {
            int[] rows = clientWindow.getProductsTable().getSelectedRows();
            if (rows.length == 0) {
                throw new Exception("You must select at least an item to add to the order!");
            }

            //treat all selected rows as base products
            MenuItem[] menuItems = AdminMenuController.getBaseProductsAtSelection(clientWindow.getProductsTable(), rows);

            for(MenuItem menuItem : menuItems) {
                deliveryService.getOrderedMenuItems().add(menuItem);
            }

        } catch (Exception e) {
            ErrorPrompt errorPrompt = new ErrorPrompt(e.getMessage());
        }
    }

    public void clearAllOrderItems() {
        deliveryService.getOrderedMenuItems().clear();
    }

    public void deleteSelectedProducts() {
        try {
            int[] rows = clientWindow.getChosenProductsTable().getSelectedRows();
            if(rows.length == 0) {
                throw new Exception("You must select at least an item to remove from the order!");
            }

            MenuItem[] menuItems = AdminMenuController.getBaseProductsAtSelection(clientWindow.getChosenProductsTable(), rows);

            for(MenuItem menuItem : menuItems) {
                deliveryService.getOrderedMenuItems().remove(menuItem);
            }

        }catch (Exception e) {
            ErrorPrompt errorPrompt = new ErrorPrompt(e.getMessage());
        }
    }

    public void populateChosenProductTable() {
        JTable table = clientWindow.getChosenProductsTable();
        String[][] data = deliveryService.returnAsTable(deliveryService.getOrderedMenuItems());
        String[] columnHeadings = {"Title", "Rating", "Calories", "Protein", "Fats", "Sodium", "Price"};
        DefaultTableModel defaultTableModel = new DefaultTableModel(data, columnHeadings);
        table.setModel(defaultTableModel);
    }

    public void computePrice() {
        double currPrice = deliveryService.computeOrderPrice();
        clientWindow.getPriceLabel().setText("Total price: " + Double.toString(currPrice) + " EUR");
    }

    public void finishOrder() {
        try {
            deliveryService.createNewOrder(HomeScreenController.currentUser.getID());
            ErrorPrompt successPrompt = new ErrorPrompt("Congratulations! Your order has been performed. You will receive a bill!");
        } catch (Exception e) {
            ErrorPrompt errorPrompt = new ErrorPrompt(e.getMessage());
        }
    }
}
