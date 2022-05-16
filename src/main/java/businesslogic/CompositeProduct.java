package businesslogic;

import java.util.ArrayList;

public class CompositeProduct extends MenuItem{

    ArrayList<MenuItem> components;

    public CompositeProduct(String title) {
        this.title = title;
        components = new ArrayList<>();
    }

    @Override
    public void computeComponents() {
        for(MenuItem component : components) {
            this.rating += component.rating;
            this.calories += component.calories;
            this.proteins += component.proteins;
            this.fats += component.fats;
            this.sodium += component.sodium;
            this.price += component.price;
        }

        this.rating /= components.size();
    }

    public ArrayList<MenuItem> getComponents() {
        return components;
    }
}
