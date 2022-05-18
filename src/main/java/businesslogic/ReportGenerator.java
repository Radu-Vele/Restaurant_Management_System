package businesslogic;

import java.io.FileWriter;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ReportGenerator {

    private DeliveryService deliveryService;

    private int type;
    private String startingHour;
    private String endHour;
    private int min_ordered_times;
    private int min_order_value;
    private String day;
    private ArrayList<String> information;

    public ReportGenerator(int type, ArrayList<String> information) {
        this.type = type;
        this.information = information;
        deliveryService = DeliveryService.getInstance();
    }

    //TODO: generate
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
        linesToWrite.add("\n______________________________\n");
        entriesToLine(filtered, linesToWrite);

        String fileName = "TYPE_1_REPORT_" + LocalDateTime.now().getNano();

        writeToTxt(fileName, linesToWrite);
    }

    public void generateType2() {

    }

    public void generateType3() {

    }

    public void generateType4() {

    }

    public void writeToTxt(String fileName, ArrayList<String> lines) throws Exception{
        FileWriter fileWriter = new FileWriter(fileName);
        for(String line : lines) {
            fileWriter.write(line);
            fileWriter.write("\n");
        }
        fileWriter.close();
    }

    public <K, V> ArrayList<String> entriesToLine(Set<Map.Entry<K, V>> mapEntries, ArrayList<String> initialLines) {

        int i = 1;

        for(Map.Entry<K, V> entry : mapEntries) {
            String newLine = "Order " + i + ": ";
            newLine += entry.getKey().toString();
            newLine += "\n -> Menu Items Ordered: " + entry.getValue().toString() + "\n";
            initialLines.add(newLine);
            i++;
        }

        return initialLines;
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
        if(min2 < 0 || min2 < 59) {
            throw new Exception("Error! Invalid time format");
        }
    }
}
