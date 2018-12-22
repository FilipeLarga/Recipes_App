package figuitosinc.pocketchef.category_recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import figuitosinc.pocketchef.R;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.CategoryViewHolder> {

    private Context context;
    private List<Category> categories;

    public CategoryRecyclerViewAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cardview_category, viewGroup, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i) {
        if (categories.get(i).getRecipeCount() != 1)
            categoryViewHolder.recipeCountTextView.setText(categories.get(i).getRecipeCount() + " Recipes");
        else
            categoryViewHolder.recipeCountTextView.setText(categories.get(i).getRecipeCount() + " Recipe");

        if (categories.get(i).getFavoriteCount() != 1)
            categoryViewHolder.favoriteCountTextView.setText(categories.get(i).getFavoriteCount() + " Favorites");
        else
            categoryViewHolder.favoriteCountTextView.setText(categories.get(i).getFavoriteCount() + " Favorite");

        categoryViewHolder.titleTextView.setText(categories.get(i).getName());

        Glide.with(context).load(context.getResources().getIdentifier("category_icon_" + categories.get(i).getName().toLowerCase() + "_small", "drawable", context.getPackageName())).into(categoryViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView recipeCountTextView;
        TextView favoriteCountTextView;
        ImageView imageView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.cardview_title_textview);
            recipeCountTextView = itemView.findViewById(R.id.cardview_recipe_count_textview);
            favoriteCountTextView = itemView.findViewById(R.id.cardview_favorite_count_textview);
            imageView = itemView.findViewById(R.id.cardview_icon_imageview);
        }
    }

}
