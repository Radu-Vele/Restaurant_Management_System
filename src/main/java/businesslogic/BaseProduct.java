package businesslogic;

public class BaseProduct extends MenuItem{

    public BaseProduct(String title, double rating, double calories, double proteins, double fats, double sodium, double price) {
        this.title = title;
        this.rating = rating;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.sodium = sodium;
        this.price = price;
    }

    @Override
    public String toString() {
        return "{" +
                "title='" + title + '\'' +
                ", rating=" + rating +
                ", calories=" + calories +
                ", proteins=" + proteins +
                ", fats=" + fats +
                ", sodium=" + sodium +
                ", price=" + price +
                '}';
    }

    @Override
    public void computeComponents() {

    }
}
