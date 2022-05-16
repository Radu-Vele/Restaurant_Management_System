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
    }

    public void populateSearchTable() {
        //go through all filters and apply them
        try {
            Collection<MenuItem> menuItems = deliveryService.getMenuItemsCollection();
            if (clientWindow.getTitleCheckBox().isSelected()) {
                if (clientWindow.getSearchTitleField().getText().equals("")) {
                    throw new Exception("Error! If you select a filter, you must input some text!");
                }
                menuItems = deliveryService.filterByTitle(menuItems, clientWindow.getSearchTitleField().getText());
            }
            if (clientWindow.getRatingCheckBox().isSelected()) {
                if (clientWindow.getRatingCheckBox().getText().equals("")) {
                    throw new Exception("Error! If you select a filter, you must input some text!");
                }
                double wantedRating = Double.parseDouble(clientWindow.getSearchRatingField().getText());
                menuItems = deliveryService.filterByRating(menuItems, wantedRating);
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
}
