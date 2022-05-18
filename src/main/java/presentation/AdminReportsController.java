package presentation;

import businesslogic.DeliveryService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

public class AdminReportsController implements ActionListener {
    AdminWindow adminWindow;
    DeliveryService deliveryService;

    public AdminReportsController(AdminWindow adminWindow) {
        this.adminWindow = adminWindow;
        deliveryService = DeliveryService.getInstance();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(adminWindow.getGenerateType1Button())) {
            generateType1();
        }
        else if(e.getSource().equals(adminWindow.getGenerateType2Button())) {
            generateType2();
        }
        else if(e.getSource().equals(adminWindow.getGenerateType3Button())) {
            generateType3();
        }
        else if (e.getSource().equals(adminWindow.getGenerateType4Button())) {
            generateType4();
        }
    }

    public void generateType1() {
        try {
            ArrayList<String> information = new ArrayList<String>(2);

            if (adminWindow.getEndHourField().getText().equals("") ||
                    adminWindow.getStartingHourField().getText().equals("")) {
                throw new Exception("Error: you must complete all the text fields");
            }

            information.add(adminWindow.getStartingHourField().getText());
            information.add(adminWindow.getEndHourField().getText());

            deliveryService.generateReport(1, information);
        } catch (Exception e) {
            ErrorPrompt errorPrompt = new ErrorPrompt(e.getMessage());
        }
    }

    public void generateType2() {
        try {
            ArrayList<String> information = new ArrayList<>(1);

            if(adminWindow.getMinNrTimesField().getText().equals("")) {
                throw new Exception("Error: you must complete all the text fields");
            }

            information.add(adminWindow.getMinNrTimesField().getText());
            deliveryService.generateReport(2, information);
        } catch (NumberFormatException nr) {
            ErrorPrompt errorPrompt = new ErrorPrompt("Error: you must input an integer");
        } catch (Exception e) {
            ErrorPrompt errorPrompt = new ErrorPrompt(e.getMessage());
        }
    }

    public void generateType3() {
        try {
            ArrayList<String> information = new ArrayList<>(2);

            if (adminWindow.getT3MinNrOfTimesField().getText().equals("") ||
                    adminWindow.getMinPriceField().getText().equals("")) {
                throw new Exception("Error: you must complete all the text fields");
            }

            information.add(adminWindow.getT3MinNrOfTimesField().getText());
            information.add(adminWindow.getMinPriceField().getText());

            deliveryService.generateReport(3, information);

        } catch (NumberFormatException nr) {
            ErrorPrompt errorPrompt = new ErrorPrompt("Error: You must input an integer for the minimum number of times");
        } catch (Exception e) {
            ErrorPrompt errorPrompt = new ErrorPrompt(e.getMessage());
        }
    }

    public void generateType4() {
        try {
            ArrayList<String> information = new ArrayList<>(1);

            if(adminWindow.getWantedDayField().getText().equals("")) {
                throw new Exception("Error: you must complete all the text fields");
            }

            information.add(adminWindow.getWantedDayField().getText());

            deliveryService.generateReport(4, information);
        } catch (NumberFormatException nr) {
            ErrorPrompt errorPrompt = new ErrorPrompt("Error: you must input an integer");
        } catch (Exception e) {
            ErrorPrompt errorPrompt = new ErrorPrompt(e.getMessage());
        }
    }
}
