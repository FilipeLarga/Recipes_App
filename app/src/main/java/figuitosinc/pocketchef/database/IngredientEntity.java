package figuitosinc.pocketchef.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Ingredient")
public class IngredientEntity {

    @PrimaryKey
    @NonNull
    public String IngredientName;

    public IngredientEntity(String IngredientName) {
        this.IngredientName = IngredientName;
    }
}