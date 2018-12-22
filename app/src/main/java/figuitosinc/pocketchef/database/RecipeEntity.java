package figuitosinc.pocketchef.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.List;

@Entity(tableName = "Recipe")
public class RecipeEntity {

    @PrimaryKey
    @NonNull
    public String RecipeName;
    public String categoryOne;
    public String categoryTwo;
    public String categoryThree;
    public boolean favorite;
    public int duration;
    public List<String> directions;
    public int difficulty;
    public int servings;

    public RecipeEntity(String RecipeName, String categoryOne, String categoryTwo, String categoryThree, boolean favorite, int duration, List<String> directions, int difficulty, int servings) {
        this.RecipeName = RecipeName;
        this.categoryOne = categoryOne;
        this.categoryTwo = categoryTwo;
        this.categoryThree = categoryThree;
        this.duration = duration;
        this.directions = directions;
        this.difficulty = difficulty;
        this.servings = servings;
        this.favorite = favorite;
    }
}