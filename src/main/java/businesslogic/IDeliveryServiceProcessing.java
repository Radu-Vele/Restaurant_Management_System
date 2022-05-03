package businesslogic;

/**
 * Delivery service interface
 * - Defines the main methods for administrator and client operations
 */
public interface IDeliveryServiceProcessing {
    /**
     * Admin operation:
     * - importing products from .csv file
     */
    void importProducts();

    /**
     * Admin operation:
     * - add / edit / delete products from menu
     */
    void manageProduct();

    /**
     * Admin operation:
     * - generate 4 types of reports
     */
    void generateReport();

    /**
     * Client operation
     * - searching for products
     * - uses an algorithm for computing the price
     * - generates a bill in a .txt file
     */
    void createNewOrder();

    //TODO: design by contract - pre, post conditions => assert for implementing DeliveryService
    //TODO: custom javaDoc tags
}
