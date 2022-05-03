package businesslogic;


import java.beans.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

/**
 * Contains the logic for dealing with admin and client operations
 * - Plays the role of an observer using the Java PropertyChangeListener implementation
 */
public class DeliveryService implements IDeliveryServiceProcessing{

    private Map<Order, Collection<MenuItem>> orderMenuItemsMap; //TODO: Choose the appropriate type of map
    private Collection<MenuItem> menuItemsCollection; //TODO: Choose the appropriate type of collection for searching based on elements (Hash Table?)

    private PropertyChangeSupport propertyChangeSupport;

    //TODO: Add from csv using lambda expressions and stream processing
    @Override
    public void importProducts() {

    }

    @Override
    public void manageProduct() {

    }

    @Override
    public void generateReport() {
        //TODO: use lambda expressions and stream processing to generate the 4 types of reports
    }

    @Override
    public void createNewOrder() {
        //TODO: generate bill
    }

    /**
     * Search method for a menu item based on generic criterion
     * TODO: Uses lambda functions and stream processing
     * @param criterion
     * @param <T>
     * @return
     */
    public <T> MenuItem searchBy(T criterion) {
        MenuItem toReturn = null;
        return toReturn;
    }

    /**
     * Add a new listener to the observable object
     * @param propertyChangeListener Observer object to be added (Employee controller)
     */
    public void addListener(PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
    }

    /**
     * Removes the listener specified as parameter from the observable object
     * @param propertyChangeListener Observer object to be removed (Employee controller)
     */
    public void removeListener(PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.removePropertyChangeListener(propertyChangeListener);
    }

    //TODO: Define Well-formed Method

    /**
     * Stores the serialized data from the clas in a txt file using the data access package
     */
    public void saveData() {

    }

    /**
     * Loads the information from previous orders saved in the .txt files
     */
    public void loadData() {

    }
}
