package figuitosinc.pocketchef.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.animation.Animation;
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
    private Intent intent;
    private boolean animating;

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
        loadBrowseButton();

        NavigationView navigationView = findViewById(R.id.home_drawer_navigationview);
        View header = navigationView.getHeaderView(0);
        ImageView headerImageView = header.findViewById(R.id.navigation_drawer_header_imagivew);
        Glide.with(this).load(R.drawable.navigation_drawer_header).into(headerImageView);


//        Glide.with(getApplicationContext()).load(R.drawable.logo_chef_small).into((ImageView) findViewById(R.id.home_logo_ImageView));
//        loadRandomQuote((TextView) findViewById(R.id.home_quote_TextView));

        initializeMap();
        setupViewModel();
    }

    @Override
    protected void onResume() {
        super.onResume();

        intent = null;
        animating = true;

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
        Animation right_center_animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_center_animation);
        right_center_animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                animating = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (intent != null) {
                    handler.removeCallbacksAndMessages(null);
                    if (intent.getComponent().getClass().getSimpleName().equals(IngredientFilterActivity.class.getSimpleName())) {
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(HomeActivity.this, findViewById(R.id.home_ingredient_filter_cardview_icon_imageview), "ingredientFilterTransition");
                        startActivity(intent, options.toBundle());
                    } else {
                        startActivity(intent);
                    }
                } else {
                    animating = false;
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.setAnimation(right_center_animation);
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

        Animation center_left_animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.center_left_animation);
        center_left_animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                animating = true;
                if (intent != null) {
                    handler.removeCallbacksAndMessages(null);
                    if (intent.getComponent().getClass().getSimpleName().equals(IngredientFilterActivity.class.getSimpleName())) {
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(HomeActivity.this, findViewById(R.id.home_ingredient_filter_cardview_icon_imageview), "ingredientFilterTransition");
                        startActivity(intent, options.toBundle());
                    } else {
                        startActivity(intent);
                    }
                }
                animation.cancel();
//                Toast.makeText(HomeActivity.this, "started", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (intent != null) {
                    handler.removeCallbacksAndMessages(null);
                    startActivity(intent);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        view.setAnimation(center_left_animation);
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

    public void startIngredientFilterActivity(View view) {
//        Intent intent = new Intent(this, NewRecipeActivity.class);
//        handler.removeCallbacksAndMessages(null);
//        startActivity(intent);
        intent = new Intent(this, IngredientFilterActivity.class);
        if (!animating) {
            handler.removeCallbacksAndMessages(null);

//            Fade fade = new Fade();
////            fade.excludeTarget(R.id.appBar, true);
//            fade.excludeTarget(android.R.id.statusBarBackground, true);
//            fade.excludeTarget(android.R.id.navigationBarBackground, true);
//
//            getWindow().setEnterTransition(fade);
//            getWindow().setExitTransition(fade);
            ImageView imageView = findViewById(R.id.home_ingredient_filter_cardview_icon_imageview);
//            TextView textView = findViewById(R.id.home_ingredient_filter_cardview_description_textview);
            Pair<View, String> p1 = Pair.create((View) imageView, ViewCompat.getTransitionName(imageView));
//            Pair<View, String> p2 = Pair.create((View) textView, ViewCompat.getTransitionName(textView));

            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(HomeActivity.this, p1);
            startActivity(intent, options.toBundle());
        }
    }

    public void startListRecipesActivity(View view) {
        intent = new Intent(this, ViewRecipesActivity.class);
        if (!animating) {
            handler.removeCallbacksAndMessages(null);
            startActivity(intent);
        }
    }

    private void loadBrowseButton() {
        String ingredientFilterText = "Browse Recipes";
        TextView ingredientFilterTV = findViewById(R.id.home_browse_cardview_description_textview);

        Spannable spannable = new SpannableString(ingredientFilterText);
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.colorSecondary)), 10, 13, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannable.setSpan(new RelativeSizeSpan(1.2f), 10, 13, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ingredientFilterTV.setText(spannable);

        ImageView imageView = findViewById(R.id.home_browse_cardview_icon_imageview);
        Glide.with(getApplicationContext()).load(R.drawable.rush).into(imageView);
    }

    private void loadSearchButton() {
        String searchText = "Search for recipes\nand categories";
        TextView searchTV = findViewById(R.id.home_search_cardview_description_textview);

        Spannable spannable = new SpannableString(searchText);
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.colorSecondary)), 0, 6, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannable.setSpan(new RelativeSizeSpan(1.2f), 0, 6, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        searchTV.setText(spannable);

//        CardView cardView = findViewById(R.id.home_search_cardview);
//        cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startListRecipesActivity();
//            }
//        });
        ImageView imageView = findViewById(R.id.home_search_cardview_icon_imageview);
        Glide.with(getApplicationContext()).load(R.drawable.mirage_searching).into(imageView);
    }

    private void loadIngredientFilterButton() {
        String ingredientFilterText = "Find recipes with the right Ingredients";
        TextView ingredientFilterTV = findViewById(R.id.home_ingredient_filter_cardview_description_textview);

        Spannable spannable = new SpannableString(ingredientFilterText);
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.colorSecondary)), 28, 39, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannable.setSpan(new RelativeSizeSpan(1.2f), 28, 39, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ingredientFilterTV.setText(spannable);

        ImageView imageView = findViewById(R.id.home_ingredient_filter_cardview_icon_imageview);
        Glide.with(getApplicationContext()).load(R.drawable.pale_ingredients).into(imageView);

    }
}