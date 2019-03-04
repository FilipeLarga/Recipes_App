package figuitosinc.pocketchef.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface IngredientDao {

    @Query("SELECT * FROM Ingredient")
    LiveData<List<IngredientEntity>> findAllIngredients();

    @Query("SELECT EXISTS(SELECT * FROM Ingredient WHERE IngredientName = :name )")
    int checkIfIngredientExists(String name);

    @Insert
    void insertIngredient(IngredientEntity ingredientEntity);

    @Delete
    void deleteRecipe(IngredientEntity ingredientEntity);

    @Query("DELETE FROM Ingredient")
    void deleteAll();

}