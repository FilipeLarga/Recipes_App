package figuitosinc.pocketchef.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM Recipe")
    LiveData<List<RecipeEntity>> loadAllRecipes();

    @Insert
    void insertRecipe(RecipeEntity recipeEntity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRecipe(RecipeEntity recipeEntity);

    @Delete
    void deleteRecipe(RecipeEntity recipeEntity);

    @Query("DELETE FROM Recipe")
    void deleteAll();

    @Query("SELECT categoryOne, categoryTwo, categoryThree FROM Recipe")
    LiveData<List<CategoryPOJO>> getRecipeCategory();
}