package businesslogic;


import dataaccess.Serializer;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import presentation.HomeScreenController;
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
    private Map<Order, Collection<MenuItem>> ordersToDeliver;

    private PropertyChangeSupport propertyChangeSupport;

    private static DeliveryService deliveryServiceInstance;


    /**
     * Singleton design pattern used
     */
    private DeliveryService() {
        orderMenuItemsMap = new LinkedHashMap<>(); //keeps the right order of the inserted order
        menuItemsCollection = new HashSet<>(); //suitable for searching, no duplicates
        chosenMenuItems = new HashSet<>();
        orderedMenuItems = new HashSet<>();
        ordersToDeliver = new HashMap<>();
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public static DeliveryService getInstance() {
        if(deliveryServiceInstance == null) {
            deliveryServiceInstance = new DeliveryService();
        }

        return deliveryServiceInstance;
    }

    /**
     * Imports products from a csv files and populates the HashSet menuItemsCollection
     * Uses stream processing
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

    public void addProduct(MenuItem menuItem) throws Exception {
        if(!menuItemsCollection.add(menuItem)) {
            throw new Exception("A product with the same title already exists. Try to edit it");
        }
    }

    @Override
    public void generateReport(int type, ArrayList<String> information) throws Exception{
        ReportGenerator reportGenerator = new ReportGenerator(type, information);
        reportGenerator.generateReport();
    }

    /**
     * Creates a new order based on the chosen products
     * The orderedMenuItems is cloned in another object that will be stored into orders
     * This way a user could perform multiple orders at once
     */
    @Override
    public void createNewOrder(int clientID) throws Exception{
        if(orderedMenuItems.isEmpty()) {
            throw new Exception("Error! You must select at least an item to create an order");
        }

        int orderID = orderMenuItemsMap.size() + 1; //order ID computation

        Order newOrder = new Order(orderID, clientID);

        Collection<MenuItem> orderedMenuItemsClone = cloneMenuItemsCollection(orderedMenuItems);

        orderMenuItemsMap.put(newOrder, orderedMenuItemsClone);
        ordersToDeliver.put(newOrder, orderedMenuItemsClone);

        generateBill(newOrder, orderedMenuItems);

        //Notify observers
        propertyChangeSupport.firePropertyChange("added order", this.ordersToDeliver, newOrder);
    }

    /**
     * Filters the collection of menu items given as parameter.
     * @param menuItems
     * @param title - valid elements' titles in the collection must contain this string
     * @return the filtered collection
     */
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

    /**
     * Filters the collection of menu items given as parameter.
     * @param menuItems
     * @param value - valid elements in the collection must be greater or equal than it
     * @return the filtered collection
     */
    public Collection<MenuItem> filterGreaterThanDouble(Collection<MenuItem> menuItems, double value, String argument) {
        Collection<MenuItem> result = null;
        switch (argument) {
            case "RATING" :
                result = menuItems.stream().
                        filter(s -> s.getRating() >= value).
                        collect(Collectors.toSet());
                break;
            case "PROTEIN" :
                result = menuItems.stream().
                        filter(s -> s.getProteins() >= value).
                        collect(Collectors.toSet());
                break;

                case "CALORIES" :
                    result = menuItems.stream().
                            filter(s -> s.getCalories() >= value).
                            collect(Collectors.toSet());
                    break;
        }

        return result;
    }

    /**
     * Filters the collection of menu items given as parameter.
     * @param menuItems
     * @param value - valid elements in the collection must be less or equal than it
     * @return the filtered collection
     */
    public Collection<MenuItem> filterLessThanDouble(Collection<MenuItem> menuItems, double value, String argument) {
        Collection<MenuItem> result = null;
        switch(argument) {
            case "PRICE" :
                result = menuItems.stream().
                        filter(s -> s.getPrice() <= value).
                        collect(Collectors.toSet());
                break;
            case "FATS" :
                result = menuItems.stream().
                        filter(s -> s.getFats() <= value).
                        collect(Collectors.toSet());
                break;
            case "SODIUM" :
                result = menuItems.stream().filter(s -> s.getSodium() <= value).collect(Collectors.toSet());

        }

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
     * Stores the serialized data from the class in a txt file using the data access package
     */
    public void saveData() throws Exception {
        Serializer<Collection<MenuItem>> serializer = new Serializer<>(this.menuItemsCollection, "menuItems.txt");
        serializer.serialize();
        Serializer<Map<Order, Collection<MenuItem>>> serializer1 = new Serializer<>(this.orderMenuItemsMap, "orders.txt");
        serializer1.serialize();
        Serializer<Map<Order, Collection<MenuItem>>> serializer2 = new Serializer<>(this.ordersToDeliver, "ordersToDeliver.txt");
        serializer2.serialize();
    }

    /**
     * Loads the information from previous instances saved in the .txt files
     */
    public void loadData() throws IOException, ClassNotFoundException {
        Serializer<Collection<MenuItem>> serializer = new Serializer<>(this.menuItemsCollection, "menuItems.txt");
        menuItemsCollection = serializer.deserialize();
        Serializer<Map<Order, Collection<MenuItem>>> serializer1 = new Serializer<>(this.orderMenuItemsMap, "orders.txt");
        orderMenuItemsMap = serializer1.deserialize();
        Serializer<Map<Order, Collection<MenuItem>>> serializer2 = new Serializer<>(this.ordersToDeliver, "ordersToDeliver.txt");
        ordersToDeliver = serializer2.deserialize();
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

    /**
     * Returns the given collection of menu items as a 2D matrix of Strings (in order to be able to display as table)
     * @param menuItems
     * @return
     */
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

    /**
     * A composite product is created and added to the menu
     * @param title
     * @throws Exception
     */
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

    /**
     * Computes the price of an order.
     * @return price value as double
     */
    public double computeOrderPrice() {
        double price = 0;
        for(MenuItem menuItem : orderedMenuItems) {
            price += menuItem.price;
        }
        return price;
    }

    /**
     * Generates PDF bill for the given order
     * @param order
     */
    public void generateBill(Order order, Collection<MenuItem> menuItems) {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            contentStream.beginText();

            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);

            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(25, 700);

            String line1 = "The details of your order:";
            contentStream.showText(line1);
            contentStream.newLine();

            String dashed_line = "------------------------------------";
            contentStream.showText(dashed_line);
            contentStream.newLine();
            contentStream.newLine();

            String line2 = "Order ID: " + Integer.toString(order.getOrderID());
            contentStream.showText(line2);
            contentStream.newLine();

            String line3 = "Customer Username: " + HomeScreenController.currentUser.getUsername();
            contentStream.showText(line3);
            contentStream.newLine();

            String line4 = "Order Price: " + computeOrderPrice();
            contentStream.showText(line4);
            contentStream.newLine();

            contentStream.newLine();
            contentStream.showText(dashed_line);
            contentStream.newLine();

            String line5 = "Ordered Menu Products:";
            contentStream.showText(line5);
            contentStream.newLine();

            for(MenuItem menuItem : menuItems) {
                contentStream.showText(menuItem.getTitle());
                contentStream.newLine();
            }

            contentStream.endText();
            contentStream.close();

            document.save("Receipt_" + order.getOrderID() + "_" + order.getOrderDate().getTime() + ".pdf");
            document.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Clones the menuItems collection
     * @param menuItems
     * @return
     * @throws CloneNotSupportedException
     */
    public Collection<MenuItem> cloneMenuItemsCollection(Collection<MenuItem> menuItems) throws CloneNotSupportedException {
        Collection<MenuItem> toReturn = new HashSet<>(menuItems.size());

        for(MenuItem menuItem : menuItems) {
            MenuItem newItem = (MenuItem) menuItem.clone();
            toReturn.add(newItem);
        }
        return toReturn;
    }

    public void addChosenItem(MenuItem menuItem) {
        this.chosenMenuItems.add(menuItem);
    }

    public Collection<MenuItem> getMenuItemsCollection() {
        return menuItemsCollection;
    }
    public Map<Order, Collection<MenuItem>> getOrdersToDeliver() {
        return ordersToDeliver;
    }
    public Collection<MenuItem> getOrderedMenuItems() {
        return orderedMenuItems;
    }

    public Map<Order, Collection<MenuItem>> getOrderMenuItemsMap() {
        return orderMenuItemsMap;
    }

    //TODO: Define Well-formed Method
}
