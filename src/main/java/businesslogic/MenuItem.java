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

    /**
     * Override equals such that we won't have products with the same name
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return title.equals(menuItem.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, rating, calories, proteins, fats, sodium, price);
    }

    public String getTitle() {
        return title;
    }

    public double getRating() {
        return rating;
    }

    public double getCalories() {
        return calories;
    }

    public double getProteins() {
        return proteins;
    }

    public double getFats() {
        return fats;
    }

    public double getSodium() {
        return sodium;
    }

    public double getPrice() {
        return price;
    }
}
