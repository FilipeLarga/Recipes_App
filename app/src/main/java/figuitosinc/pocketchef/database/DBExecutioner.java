package figuitosinc.pocketchef.database;

import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DBExecutioner {

    private static volatile DBExecutioner instance = new DBExecutioner();
    private Executor executor = Executors.newSingleThreadExecutor();


    private DBExecutioner() {
    }

    public static synchronized DBExecutioner getInstance() {
        return instance;
    }

    public Executor getExecutor() {
        return executor;
    }

    public void deleteRecipe(final RecipeEntity recipe, final Context context) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                RecipeIngredientDatabase.getInstance(context).recipeDao().deleteRecipe(recipe);
            }
        });
    }


    public void addRecipe(final RecipeEntity recipe, final Context context) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                RecipeIngredientDatabase.getInstance(context).recipeDao().insertRecipe(recipe);
            }
        });
    }

}
