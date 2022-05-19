package presentation;

import businesslogic.DeliveryService;
import businesslogic.MenuItem;
import businesslogic.Order;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.Map;

public class EmployeeController implements PropertyChangeListener, ActionListener {

    private EmployeeWindow employeeWindow;
    private DeliveryService deliveryService;

    public EmployeeController(EmployeeWindow employeeWindow) {
        this.employeeWindow = employeeWindow;
        this.deliveryService = DeliveryService.getInstance();
        deliveryService.addListener(this);
        updateNrOfOrders();
        updateList();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        updateList();
        updateNrOfOrders();
    }

    public void updateNrOfOrders() {
        String text = "Number of orders to deliver: ";
        employeeWindow.getNumberOfOrdersLabel().setText(text + deliveryService.getOrdersToDeliver().size());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(employeeWindow.getMarkAsDoneButton())) {
            markSelectedAsDone();
            updateList();
            updateNrOfOrders();
        }
    }

    public void updateList() {
        JList list = employeeWindow.getOrdersList();
        DefaultListModel listModel = new DefaultListModel();

        int i = 0;
        for(Map.Entry<Order, Collection<MenuItem>> entry : deliveryService.getOrdersToDeliver().entrySet()) {
            Order order = entry.getKey();
            String toAdd = "";
            toAdd += order.getOrderID() + " ";
            toAdd += order.getClientID() + " -> ";
            for(MenuItem menuItem : entry.getValue()) {
                toAdd += menuItem.getTitle() + " ";
            }

            listModel.add(i, toAdd);
        }

        list.setModel(listModel);
    }

    public void markSelectedAsDone() {
        String orderToDelete = (String) employeeWindow.getOrdersList().getSelectedValue();
        boolean found = false;
        for(Map.Entry<Order, Collection<MenuItem>> entry : deliveryService.getOrdersToDeliver().entrySet()) {
            if(entry.getKey().getOrderID() == Integer.parseInt(orderToDelete.substring(0, 1))) {
                deliveryService.getOrdersToDeliver().remove(entry.getKey());
                found = true;
            }
        }
        if(!found) {
            ErrorPrompt errorPrompt = new ErrorPrompt("You must select an order to mark as done.");
        }
    }
}