package figuitosinc.pocketchef.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class HomeActivityViewModel extends AndroidViewModel {

    private LiveData<List<RecipeEntity>> recipes;
    private RecipeIngredientDatabase db = RecipeIngredientDatabase.getInstance(this.getApplication());

    public HomeActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<RecipeEntity>> getRecipes() {
        return db.recipeDao().loadAllRecipes();
    }

    public LiveData<List<CategoryPOJO>> getRecipeCategory() {
        return db.recipeDao().getRecipeCategory();
    }
}
