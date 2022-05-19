package businesslogic;

import utils.AlreadyImportedInitialProducts;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Delivery service interface
 * - Defines the main methods for administrator and client operations
 * @invariant wellFormed()
 */
public interface IDeliveryServiceProcessing {
    /**
     * Admin operation:
     * @pre true
     * @post !menuItemsCollection.isEmpty()
     * - importing products from .csv file
     */
    void importProducts()  throws FileNotFoundException, AlreadyImportedInitialProducts;

    /**
     * Admin operation:
     * - add / edit / delete products from menu
     * @pre true
     * @post true
     */
    void manageProduct(String operation, MenuItem menuItem) throws Exception;

    /**
     * Admin operation:
     * - generate 4 types of reports
     * @pre true
     * @post true
     */
    void generateReport(int type, ArrayList<String> information) throws Exception;

    /**
     * Client operation
     * - searching for products
     * - uses an algorithm for computing the price
     * - generates a bill
     * @pre true
     * @post true
     */
    void createNewOrder(int clientID) throws Exception;
}
