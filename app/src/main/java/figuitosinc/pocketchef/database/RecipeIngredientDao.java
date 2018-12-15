package figuitosinc.pocketchef.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface RecipeIngredientDao {

    @Query("SELECT Ingredient.* FROM Ingredient INNER JOIN recipe_ingredient_join ON " +
            "Ingredient.IngredientName = recipe_ingredient_join.IngredientId WHERE " +
            "recipe_ingredient_join.RecipeId = :recipeName")
    List<IngredientEntity> getIngredientsForRecipe(String recipeName);

    @Query("SELECT Recipe.* FROM Recipe INNER JOIN recipe_ingredient_join ON " +
            "Recipe.RecipeName = recipe_ingredient_join.RecipeId WHERE " +
            "recipe_ingredient_join.IngredientId = :ingredientName")
    List<RecipeEntity> getRecipesForIngredient(String ingredientName);

    @Insert
    void insert(RecipeIngredientJoin recipeIngredientJoin);

}