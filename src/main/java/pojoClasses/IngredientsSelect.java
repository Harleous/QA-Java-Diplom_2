package pojoClasses;

public class IngredientsSelect {
    public IngredientsSelect(Object[] ingredients) {
        this.ingredients = ingredients;
    }

    public Object[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(Object[] ingredients) {
        this.ingredients = ingredients;
    }

    Object[] ingredients;
}
