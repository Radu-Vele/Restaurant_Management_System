package businesslogic;


import dataaccess.Serializer;
import utils.AlreadyImportedInitialProducts;

import java.beans.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Contains the logic for dealing with admin and client operations
 * - Plays the role of an observer using the Java PropertyChangeListener implementation
 * - Singleton
 */
public class DeliveryService implements IDeliveryServiceProcessing{

    private Map<Order, Collection<MenuItem>> orderMenuItemsMap;
    private Collection<MenuItem> menuItemsCollection;
    private Collection<MenuItem> chosenMenuItems;
    private Collection<MenuItem> orderedMenuItems;

    private PropertyChangeSupport propertyChangeSupport;

    private static DeliveryService deliveryServiceInstance;


    private DeliveryService() {
        orderMenuItemsMap = new LinkedHashMap<>(); //keeps the right order of the inserted order
        menuItemsCollection = new HashSet<>(); //suitable for searching, no duplicates
        chosenMenuItems = new HashSet<>();
        orderedMenuItems = new HashSet<>();
    }

    public static DeliveryService getInstance() {
        if(deliveryServiceInstance == null) {
            deliveryServiceInstance = new DeliveryService();
        }

        return deliveryServiceInstance;
    }

    /**
     * Imports products from a csv files and populates the HashSet menuItemsCollection
     * @throws FileNotFoundException
     */
    @Override
    public void importProducts() throws FileNotFoundException, AlreadyImportedInitialProducts {
        if(!menuItemsCollection.isEmpty()) {
            throw new AlreadyImportedInitialProducts();
        }
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
    public void manageProduct(String operation, MenuItem menuItem) throws Exception{
        switch (operation) {
            case "ADD":
                addProduct(menuItem);
                break;
            case "MODIFY":
                break;
            case "DELETE":
                //TODO: arrange
                break;
        }
    }

    @Override
    public void generateReport() {
        //TODO: use lambda expressions and stream processing to generate the 4 types of reports

    }

    /**
     *
     */
    @Override
    public void createNewOrder() {
        //TODO: generate bill
    }

    public Collection<MenuItem> filterByTitle(Collection<MenuItem> menuItems, String title) {
        Collection<MenuItem> result;
        if(title.equals("")) {
            result = menuItems;
        }
        else {
            result = menuItems.stream().
                    filter(s -> s.getTitle().toLowerCase(Locale.ROOT).contains(title.toLowerCase(Locale.ROOT))).
                    collect(Collectors.toSet());
        }
        return result;
    }

    public Collection<MenuItem> filterByRating(Collection<MenuItem> menuItems, double rating) {
        Collection<MenuItem> result = menuItems.stream().
                filter(s -> s.getRating() >= rating).
                collect(Collectors.toSet());
        return result;
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
     * Loads the information from previous instances saved in the .txt files
     */
    public void loadData() throws IOException, ClassNotFoundException {
        Serializer<Collection<MenuItem>> serializer = new Serializer<>(this.menuItemsCollection, "menuItems.txt");
        menuItemsCollection = serializer.deserialize();
    }

    public void addProduct(MenuItem menuItem) throws Exception {
        if(!menuItemsCollection.add(menuItem)) {
            throw new Exception("A product with the same title already exists. Try to edit it");
        }
    }

    public Collection<MenuItem> getMenuItemsCollection() {
        return menuItemsCollection;
    }

    /**
     * Transforms the set of menu items filtered w.r.t. the keyword into a 2D string array for populating JTable
     * @return
     */
    public String[][] setToTable(String filteringName) {
        Collection<MenuItem> filteredMenuItemHashSet =  filterByTitle(menuItemsCollection, filteringName);
        String[][] toReturn = returnAsTable(filteredMenuItemHashSet);
        return toReturn;
    }

    public void addChosenItem(MenuItem menuItem) {
        this.chosenMenuItems.add(menuItem);
    }

    public String[][] returnAsTable(Collection<MenuItem> menuItems) {
        String[][] toReturn = new String[menuItems.size()][7];

        int i = 0;
        for(MenuItem menuItem : menuItems) {
            toReturn[i][0] = menuItem.title;
            toReturn[i][1] = Double.toString(menuItem.rating);
            toReturn[i][2] = Double.toString(menuItem.calories);
            toReturn[i][3] = Double.toString(menuItem.proteins);
            toReturn[i][4] = Double.toString(menuItem.fats);
            toReturn[i][5] = Double.toString(menuItem.sodium);
            toReturn[i][6] = Double.toString(menuItem.price);
            i++;
        }

        return toReturn;
    }

    public Collection<MenuItem> getChosenMenuItems() {
        return chosenMenuItems;
    }

    public void addCompositeProduct(String title) throws Exception{
        CompositeProduct compositeProduct = new CompositeProduct(title);

        if(chosenMenuItems.isEmpty() || chosenMenuItems.size() == 1) {
            throw new Exception("The list of chosen items is empty! Please select two or more items to add to the menu");
        }

        for(MenuItem menuItem : chosenMenuItems) {
            compositeProduct.getComponents().add(menuItem);
        }

        compositeProduct.computeComponents();
        menuItemsCollection.add(compositeProduct);
    }
}
