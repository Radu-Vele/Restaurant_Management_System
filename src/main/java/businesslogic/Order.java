package businesslogic;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.Objects;

public class Order implements Serializable {
    private int orderID;
    private int clientID;
    private Date orderDate;

    public Order(int orderID, int clientID) {
        this.orderID = orderID;
        this.clientID = clientID;
        this.orderDate = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderID == order.orderID && clientID == order.clientID;
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

    public int getOrderID() {
        return orderID;
    }

    public int getClientID() {
        return clientID;
    }

    @Override
    public String toString() {
        return "{" +
                "orderID=" + orderID +
                ", clientID=" + clientID +
                ", orderDate=" + orderDate +
                '}';
    }

    public Date getOrderDate() {
        return orderDate;
    }
}
