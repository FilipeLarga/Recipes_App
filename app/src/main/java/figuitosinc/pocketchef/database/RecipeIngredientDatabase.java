package figuitosinc.pocketchef.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {RecipeEntity.class, IngredientEntity.class, RecipeIngredientJoin.class}, version = 1, exportSchema = false)
@TypeConverters(DataConverter.class)
public abstract class RecipeIngredientDatabase extends RoomDatabase {
    private static final String DB_NAME = "recipesdb.db";
    private static volatile RecipeIngredientDatabase instance;


    public static synchronized RecipeIngredientDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static RecipeIngredientDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                RecipeIngredientDatabase.class,
                DB_NAME).build();
    }

    public abstract RecipeDao recipeDao();

    public abstract IngredientDao ingredienteDao();

    public abstract RecipeIngredientDao recipeIngredientDao();

}