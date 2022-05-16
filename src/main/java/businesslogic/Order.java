package businesslogic;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Objects;

public class Order implements Serializable {
    private int orderID;
    private int clientID;
    private String orderDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderID == order.orderID && clientID == order.clientID && orderDate.equals(order.orderDate);
    }

    /**
     * Overriden to be used in the Map structure <Order, Collection<MenuItem>>
     *
     * @return an integer hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(orderID, clientID, orderDate);
    }
}
