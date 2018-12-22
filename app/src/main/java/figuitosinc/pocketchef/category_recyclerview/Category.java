package figuitosinc.pocketchef.category_recyclerview;

public class Category {

    private String name;
    private int recipeCount;
    private int favoriteCount;

    public Category(String name, int recipeCount, int favoriteCount) {
        this.name = name;
        this.recipeCount = recipeCount;
        this.favoriteCount = favoriteCount;
    }

    public String getName() {
        return name;
    }

    public int getRecipeCount() {
        return recipeCount;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }
}
