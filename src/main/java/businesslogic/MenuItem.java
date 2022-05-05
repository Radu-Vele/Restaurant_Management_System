package businesslogic;

import java.io.Serializable;
import java.util.Objects;

public abstract class MenuItem implements Serializable {
    protected String title;
    protected double rating;
    protected double calories;
    protected double proteins;
    protected double fats;
    protected double sodium;
    protected double price;

    public abstract void computePrice();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return Double.compare(menuItem.rating, rating) == 0 && Double.compare(menuItem.calories, calories) == 0 && Double.compare(menuItem.proteins, proteins) == 0 && Double.compare(menuItem.fats, fats) == 0 && Double.compare(menuItem.sodium, sodium) == 0 && Double.compare(menuItem.price, price) == 0 && title.equals(menuItem.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, rating, calories, proteins, fats, sodium, price);
    }
}
