package figuitosinc.pocketchef.category_recyclerview;

public class Category {

    private String name;
    private int recipeCount;

    public Category(String name, int recipeCount) {
        this.name = name;
        this.recipeCount = recipeCount;
    }

    public String getName() {
        return name;
    }

    public int getRecipeCount() {
        return recipeCount;
    }
}
