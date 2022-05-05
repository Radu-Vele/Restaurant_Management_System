package businesslogic;

import java.io.Serializable;

public abstract class MenuItem implements Serializable {
    protected String title;
    protected double rating;
    protected double calories;
    protected double proteins;
    protected double fats;
    protected double sodium;
    protected double price;

    public abstract void computePrice();
}
