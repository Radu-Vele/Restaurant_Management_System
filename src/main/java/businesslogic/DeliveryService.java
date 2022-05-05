package businesslogic;


import dataaccess.Serializer;

import java.beans.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Contains the logic for dealing with admin and client operations
 * - Plays the role of an observer using the Java PropertyChangeListener implementation
 */
public class DeliveryService implements IDeliveryServiceProcessing{

    private Map<Order, Collection<MenuItem>> orderMenuItemsMap; //TODO: Choose the appropriate type of map
    private Collection<MenuItem> menuItemsCollection; //TODO: Choose the appropriate type of collection for searching based on elements (Hash Table?)

    private PropertyChangeSupport propertyChangeSupport;

    public DeliveryService() {
        orderMenuItemsMap = new LinkedHashMap<>(); //keeps the right order of the inserted order
        menuItemsCollection = new HashSet<>(); //suitable for searching, no duplicates
    }

    /**
     * Imports products from a csv files and populates the HashSet menuItemsCollection
     * @throws FileNotFoundException
     */
    @Override
    public void importProducts() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/products.csv")));
        menuItemsCollection = reader.lines()
                .skip(1)
                .map(s -> {
                    String title = "";
                    double rating = 0;
                    double calories = 0;
                    double proteins = 0;
                    double fats = 0;
                    double sodium = 0;
                    double price = 0;
                    String[] wordsArray = s.split(",");
                    int i = 0;
                    for (String word : wordsArray) {
                        switch (i) {
                            case 0:
                                title = word;
                                break;
                            case 1:
                                rating = Double.parseDouble(word);
                                break;
                            case 2:
                                calories= Double.parseDouble(word);
                                break;
                            case 3:
                                proteins = Double.parseDouble(word);
                                break;
                            case 4:
                                fats = Double.parseDouble(word);
                                break;
                            case 5:
                                sodium = Double.parseDouble(word);
                                break;
                            case 6:
                                price = Double.parseDouble(word);
                                break;
                        }
                        i++;
                    }
                    return new BaseProduct(title, rating, calories, proteins, fats, sodium, price);
                })
                .collect(Collectors.toSet());
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
     * Stores the serialized data from the class in a txt file using the data access package
     */
    public void saveData() throws Exception {
        //save products set
        Serializer<Collection<MenuItem>> serializer = new Serializer<>(this.menuItemsCollection, "menuItems.txt");
        serializer.serialize();
    }

    /**
     * Loads the information from previous or
     * reders saved in the .txt files
     */
    public Collection<MenuItem> loadData() throws IOException, ClassNotFoundException {
        Collection<MenuItem> menuItems;
        Serializer<Collection<MenuItem>> serializer = new Serializer<>(this.menuItemsCollection, "menuItems.txt");
        menuItems = serializer.deserialize();
        return menuItems;
    }
}
