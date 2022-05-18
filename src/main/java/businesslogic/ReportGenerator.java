package businesslogic;

import java.util.Collection;

public class ReportGenerator {
    private int type;
    private String startingHour;
    private String endHour;
    private int min_ordered_times;
    private int min_order_value;
    private String day;

    public ReportGenerator(int type, Collection<String> information) {
        this.type = type;
    }

    //TODO: generate
    public void generateReport() {
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

    public void generateType1() {

    }

    public void generateType2() {

    }

    public void generateType3() {

    }

    public void generateType4() {

    }
}
