package businesslogic;

import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReportGenerator {

    private DeliveryService deliveryService;

    private int type;
    private String startingHour;
    private String endHour;
    private int min_ordered_times;
    private double min_order_value;
    private String day;
    private ArrayList<String> information;

    public ReportGenerator(int type, ArrayList<String> information) {
        this.type = type;
        this.information = information;
        deliveryService = DeliveryService.getInstance();
    }

    public void generateReport() throws Exception{
        switch(type) {
            case 1:
                //orders performed between two hours (no day)
                generateType1();
                break;
            case 2:
                //products ordered more than a specified number of times so far
                generateType2();
                break;
            case 3:
                //clients that ordered more than a specified number of times so far + value of order > min_val
                generateType3();
                break;
            case 4:
                //the products ordered within a specific day + nr of times they've been ordered
                generateType4();
                break;
        }
    }

    public void generateType1() throws Exception{
        this.startingHour = information.get(0);
        this.endHour = information.get(1);

        String[] startingComponents = startingHour.split(":");
        String[] endingComponents = endHour.split(":");
        if(startingComponents.length != 2 || endingComponents.length != 2) {
            throw new Exception("Error: you must obey the time format required!");
        }

        final int startH = Integer.parseInt(startingComponents[0]);
        final int startMin = Integer.parseInt(startingComponents[1]);

        final int endH = Integer.parseInt(endingComponents[0]);
        final int endMin = Integer.parseInt(endingComponents[1]);

        checkCorrectHourFormat(startH, endH, startMin, endMin);

        Map<Order, Collection<MenuItem>> map = deliveryService.getOrderMenuItemsMap();
        Set<Map.Entry<Order, Collection<MenuItem>>> entries = map.entrySet();

        Set<Map.Entry<Order, Collection<MenuItem>>> filtered = entries.stream().filter(s -> {
            Order order = s.getKey();
            Date date = order.getOrderDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            if((calendar.get(Calendar.HOUR_OF_DAY) < startH)){
                return false;
            }

            if((calendar.get(Calendar.HOUR_OF_DAY) > endH)) {
                return false;
            }

            if((calendar.get(Calendar.HOUR_OF_DAY) == startH)) {
                if(calendar.get(Calendar.MINUTE) < startMin)
                    return false;
            }
            if(calendar.get(Calendar.HOUR_OF_DAY) == endH) {
                if(calendar.get(Calendar.MINUTE) > endMin)
                    return false;
            }

            return true;
        }).collect(Collectors.toSet());

        ArrayList<String> linesToWrite = new ArrayList<>();
        linesToWrite.add("TIME INTERVAL OF ORDERS REPORT\n");
        linesToWrite.add("Orders before hours " + startingHour + " and " + endHour + "\n");
        linesToWrite.add("______________________________\n");
        orderEntriesToLine(filtered, linesToWrite);

        String fileName = "TYPE_1_REPORT_" + LocalDateTime.now().getNano();

        writeToTxt(fileName, linesToWrite);
    }

    /**
     * Counts the frequency of a given product.
     * Idea: create a list of all ordered products. Process it to find the frequency of all products (Frequency Map)
     * @throws Exception
     */
    public void generateType2() throws Exception{
        min_ordered_times = Integer.parseInt(information.get(0));
        Map<MenuItem, Long> menuItemsFrequencyMap;

        Collection<MenuItem> allOrderedProducts;
        Set<Map.Entry<Order, Collection<MenuItem>>> entries = deliveryService.getOrderMenuItemsMap().entrySet();

        menuItemsFrequencyMap = entries.stream()
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream).collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        Set<Map.Entry<MenuItem, Long>> entries1 =  menuItemsFrequencyMap.entrySet().stream()
                        .filter(s -> s.getValue() >= min_ordered_times).collect(Collectors.toSet());

        //TODO: should we also count the products belonging to menus?
        ArrayList<String> linesToWrite = new ArrayList<>();
        linesToWrite.add("POPULAR PRODUCTS REPORT\n");
        linesToWrite.add("Products ordered more than: " + min_ordered_times + " times");
        linesToWrite.add("______________________________\n");
        productsEntriesToLine(entries1, linesToWrite);
        String fileName = "TYPE_2_REPORT_" + LocalDateTime.now().getNano();
        writeToTxt(fileName, linesToWrite);
    }

    /**
     * Report regarding fidel clients
     */
    public void generateType3() throws Exception {
        this.min_ordered_times = Integer.parseInt(information.get(0));
        this.min_order_value = Double.parseDouble(information.get(1));

        //filter by value
        Set<Map.Entry<Integer, Long>> validClientsEntries = deliveryService.getOrderMenuItemsMap().entrySet().stream()
                .filter(s-> {
                    double value = 0;
                    for(MenuItem menuItem : s.getValue()) {
                        value += menuItem.getPrice();
                    }
                    return value >= min_order_value;
                }).map(Map.Entry::getKey).collect(Collectors.groupingBy(
                        Order::getClientID,
                        Collectors.counting()
                )).entrySet().stream()
                .filter(s -> {
                    System.out.println(s.getKey().toString() + " " + s.getValue().toString());
                    return s.getValue() >= min_ordered_times;
                }).collect(Collectors.toSet());

        ArrayList<String> linesToWrite = new ArrayList<>();
        linesToWrite.add("FIDEL CLIENTS REPORT\n");
        linesToWrite.add("Client that ordered more than: " + min_ordered_times+ " times");
        linesToWrite.add("With value more than: " + min_order_value);
        linesToWrite.add("______________________________\n");

        clientsEntriesToLine(validClientsEntries, linesToWrite);
        String fileName = "TYPE_3_REPORT_" + LocalDateTime.now().getNano();
        writeToTxt(fileName, linesToWrite);

    }

    /**
     * Products ordered in a specific day and the number of times they've been ordered
     */
    public void generateType4() throws Exception{
        day = information.get(0);
        String[] dayComponents = day.split("/");
        if(dayComponents.length != 3)
            throw new Exception("Error! Invalid day format");
        int day_of_month = Integer.parseInt(dayComponents[0]);
        int month = Integer.parseInt(dayComponents[1]);
        int year = Integer.parseInt(dayComponents[2]);

        Map<MenuItem, Long> frequencyMap = deliveryService.getOrderMenuItemsMap().entrySet().stream()
                .filter(s -> {
                    Date date = s.getKey().getOrderDate();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);

                    return calendar.get(Calendar.DAY_OF_MONTH) == day_of_month
                            && calendar.get(Calendar.MONTH) + 1 == month
                            && calendar.get(Calendar.YEAR) == year;
                })
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(
                   Function.identity(),
                   Collectors.counting()
                ));

        ArrayList<String> linesToWrite = new ArrayList<>();
        linesToWrite.add("DAILY PRODUCTS REPORT\n");
        linesToWrite.add("Products ordered today: " + this.day);
        linesToWrite.add("______________________________\n");
        productsEntriesToLine(frequencyMap.entrySet(), linesToWrite);

        String fileName = "TYPE_4_REPORT_" + LocalDateTime.now().getNano();
        writeToTxt(fileName, linesToWrite);
    }

    public void writeToTxt(String fileName, ArrayList<String> lines) throws Exception{
        FileWriter fileWriter = new FileWriter(fileName);
        for(String line : lines) {
            fileWriter.write(line);
            fileWriter.write("\n");
        }
        fileWriter.close();
    }

    public <K, V> void orderEntriesToLine(Set<Map.Entry<K, V>> mapEntries, ArrayList<String> initialLines) {

        int i = 1;

        for(Map.Entry<K, V> entry : mapEntries) {
            String newLine = "Order " + i + ": ";
            newLine += entry.getKey().toString();
            newLine += "\n -> Menu Items Ordered: " + entry.getValue().toString() + "\n";
            initialLines.add(newLine);
            i++;
        }

    }

    public <K, V> void productsEntriesToLine(Set<Map.Entry<K, V>> mapEntries, ArrayList<String> initialLines) {

        for(Map.Entry<K, V> entry : mapEntries) {
            String newLine = "Menu Item: " + entry.getKey().toString();
            newLine += " --> number of times " + entry.getValue().toString() + "\n";
            initialLines.add(newLine);
        }

    }

    public <K, V> void clientsEntriesToLine(Set<Map.Entry<K, V>> mapEntries, ArrayList<String> initialLines) {

        //TODO: show usernames
        for(Map.Entry<K, V> entry : mapEntries) {
            String newLine = "Client ID: " + entry.getKey().toString();
            initialLines.add(newLine);
        }

    }

    public void checkCorrectHourFormat(int hour1, int hour2, int min1, int min2) throws Exception{
        if(hour1 < 0 || hour1 > 23) {
            throw new Exception("Error! Invalid time format");
        }
        if(hour2 < 0 || hour2 > 23) {
            throw new Exception("Error! Invalid time format");
        }
        if(min1 < 0 || min1 > 59) {
            throw new Exception("Error! Invalid time format");
        }
        if(min2 < 0 || min2 > 59) {
            throw new Exception("Error! Invalid time format");
        }
    }
}
