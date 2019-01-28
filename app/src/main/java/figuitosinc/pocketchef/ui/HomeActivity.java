package figuitosinc.pocketchef.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import figuitosinc.pocketchef.CategoriesEnum;
import figuitosinc.pocketchef.R;
import figuitosinc.pocketchef.database.HomeActivityViewModel;
import figuitosinc.pocketchef.database.RecipeCategoryPOJO;


public class HomeActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    private Map<String, Integer> categoriesMap = new HashMap<>();
    private int lastCategory = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            Thread.sleep(500);  // Sleeps 1 second for splash screen... bad practice but whatever
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_new);

        loadSearchButton();
        loadIngredientFilterButton();


//        String searchText = " color=" + ContextCompat.getColor(getApplicationContext(), R.color.colorSecondary) + ">Search</font> recipes";
//        TextView searchTV = findViewById(R.id.home_search_cardview_description_textview);
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//            searchTV.setText(Html.fromHtml(searchText, Html.FROM_HTML_MODE_LEGACY));
//        } else {
//            searchTV.setText(Html.fromHtml(searchText));
//        }

//        Glide.with(getApplicationContext()).load(R.drawable.logo_chef_small).into((ImageView) findViewById(R.id.home_logo_ImageView));
//        loadRandomQuote((TextView) findViewById(R.id.home_quote_TextView));
        initializeMap();
        setupViewModel();
    }

    @Override
    protected void onResume() {
        super.onResume();

        final ImageView categoryImage = findViewById(R.id.home_icon_ImageView);
        final TextView tv = findViewById(R.id.home_category_TextView);
        final TextView tvCount = findViewById(R.id.home_count_TextView);

        final Runnable loadRunnable = new Runnable() {
            @Override
            public void run() {
                loadRandomImage(categoryImage, tv, tvCount);
            }
        };

        final Runnable hideRunnable = new Runnable() {
            @Override
            public void run() {
                hideImage(categoryImage, tv, tvCount);
            }
        };

        handler.post(new Runnable() {
            @Override
            public void run() {
                handler.post(loadRunnable);
                handler.postDelayed(hideRunnable, 4000);
                handler.postDelayed(this, 5000);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacksAndMessages(null);
    }

    private void initializeMap() {
        for (String category : CategoriesEnum.categories) {
            categoriesMap.put(category, 0);
        }
    }

//    private void loadRandomQuote(TextView view) {
//        String[] quotes = getResources().getStringArray(R.array.quotes);
//        int x = new Random().nextInt(quotes.length);
//
//        view.setText(quotes[x]);
//    }

    @SuppressWarnings("deprecation")
    private void loadRandomImage(ImageView view, TextView tv, TextView tvCount) {
        view.setVisibility(View.VISIBLE);
        tv.setVisibility(View.VISIBLE);
        tvCount.setVisibility(View.VISIBLE);

        if (lastCategory == -1) {
            lastCategory = new Random().nextInt(CategoriesEnum.count);
        } else {
            lastCategory = lastCategory + 1 + new Random().nextInt(3);
        }

        if (lastCategory >= CategoriesEnum.count) {
            lastCategory = lastCategory - CategoriesEnum.count;
        }
        String category = CategoriesEnum.categories[lastCategory];
        Glide.with(getApplicationContext()).load(getResources().getIdentifier("category_icon_" + category.toLowerCase(), "drawable", getPackageName())).into(view);
        view.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_center_animation));
//        String count = "<font color='" + ContextCompat.getColor(getApplicationContext(), R.color.colorSecondary) + "'>" + categoriesMap.get(category) + "</font>";

        int ra = new Random().nextInt(67);
        tvCount.setText(String.valueOf(ra));

        if (categoriesMap.get(category) == 1)
            tv.setText(" " + category + " Recipe!");
        else
            tv.setText(" " + category + " Recipes!");
        tv.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_animation));
        tvCount.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_animation));

//        tv.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_center_offset_animation));
//        tvCount.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_center_offset_animation));


    }

    private void hideImage(ImageView view, TextView tv, TextView tvCount) {
        view.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.center_left_animation));
        view.setVisibility(View.INVISIBLE);
        tv.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out_animation));
//        tv.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.center_left_offset_animation));

        tv.setVisibility(View.INVISIBLE);
        tvCount.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out_animation));
//        tvCount.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.center_left_offset_animation));
        tvCount.setVisibility(View.INVISIBLE);
    }

    private void setupViewModel() {
        HomeActivityViewModel homeActivityViewModel = ViewModelProviders.of(this).get(HomeActivityViewModel.class);
        homeActivityViewModel.getRecipeCategory().observe(this, new Observer<List<RecipeCategoryPOJO>>() {
            @Override
            public void onChanged(@Nullable List<RecipeCategoryPOJO> list) {

                //Reset the map
                categoriesMap.clear();
                initializeMap();

                if (list != null) {
                    for (RecipeCategoryPOJO category : list) {
                        int previousValue = categoriesMap.get(category.categoryOne);
                        categoriesMap.put(category.categoryOne, previousValue + 1);
                        previousValue = categoriesMap.get(category.categoryTwo);
                        categoriesMap.put(category.categoryTwo, previousValue + 1);
                        previousValue = categoriesMap.get(category.categoryThree);
                        categoriesMap.put(category.categoryThree, previousValue + 1);
                    }
                }
            }
        });
    }

    public void startAddRecipeActivity(View view) {
//        Intent intent = new Intent(this, NewRecipeActivity.class);
//        handler.removeCallbacksAndMessages(null);
//        startActivity(intent);
    }

    public void startListRecipesActivity(View view) {
        Intent intent = new Intent(this, ViewRecipesActivity.class);
        handler.removeCallbacksAndMessages(null);
        startActivity(intent);
    }

    private void loadSearchButton() {
        String searchText = "Search for recipes\nand categories";
        TextView searchTV = findViewById(R.id.home_search_cardview_description_textview);

        Spannable spannable = new SpannableString(searchText);
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.colorSecondary)), 0, 6, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannable.setSpan(new RelativeSizeSpan(1.2f), 0, 6, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        searchTV.setText(spannable);

//        ImageView searchImage = findViewById(R.id.home_search_cardview_icon_imageview);
//        Glide.with(getApplicationContext()).load(R.drawable.mirage_searching).into(searchImage);
    }

    private void loadIngredientFilterButton() {
        String ingredientFilterText = "Find recipes with the right Ingredients";
        TextView ingredientFilterTV = findViewById(R.id.home_ingredient_filter_cardview_description_textview);

        Spannable spannable = new SpannableString(ingredientFilterText);
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.colorSecondary)), 28, 39, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannable.setSpan(new RelativeSizeSpan(1.2f), 28, 39, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ingredientFilterTV.setText(spannable);
    }
}