package pojoClasses;

import java.util.ArrayList;

public class IngredientsSelect {
    public IngredientsSelect(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<String> getIngredientsSelect() {
        return ingredients;
    }

    public void setIngredientsSelect(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    ArrayList<String> ingredients;

}
