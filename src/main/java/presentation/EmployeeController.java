package presentation;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EmployeeController implements PropertyChangeListener {

    private EmployeeWindow employeeWindow;

    public EmployeeController(EmployeeWindow employeeWindow) {
        this.employeeWindow = employeeWindow;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
