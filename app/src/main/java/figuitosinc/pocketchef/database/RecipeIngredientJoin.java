package figuitosinc.pocketchef.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

@Entity(tableName = "recipe_ingredient_join",
        primaryKeys = {"RecipeId", "IngredientId"},
        foreignKeys = {
                @ForeignKey(entity = RecipeEntity.class,
                        parentColumns = "RecipeName",
                        childColumns = "RecipeId"),
                @ForeignKey(entity = IngredientEntity.class,
                        parentColumns = "IngredientName",
                        childColumns = "IngredientId")
        },
        indices = {
                @Index(value = "RecipeId"),
                @Index(value = "IngredientId")
        }
)

public class RecipeIngredientJoin {

    @NonNull
    private String RecipeId;
    @NonNull
    private String IngredientId;

    public RecipeIngredientJoin(String RecipeId, String IngredientId) {
        this.RecipeId = RecipeId;
        this.IngredientId = IngredientId;
    }

    public String getIngredientId() {
        return IngredientId;
    }

    public String getRecipeId() {
        return RecipeId;
    }
}