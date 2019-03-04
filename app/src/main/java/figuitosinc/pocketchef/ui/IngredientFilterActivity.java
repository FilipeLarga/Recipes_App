package figuitosinc.pocketchef.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;

import figuitosinc.pocketchef.R;
import figuitosinc.pocketchef.database.IngredientEntity;
import figuitosinc.pocketchef.database.IngredientFilterViewModel;
import figuitosinc.pocketchef.database.RecipeIngredientDatabase;
import figuitosinc.pocketchef.ui.IngredientRecyclerView.IngredientRecyclerViewAdapter;
import figuitosinc.pocketchef.ui.IngredientRecyclerView.IngredientRecyclerViewOnClickListener;

public class IngredientFilterActivity extends AppCompatActivity {

    //    private ChipGroup chipGroup;
    private IngredientRecyclerViewAdapter unselectedIngredientRecyclerViewAdapter;
    private IngredientRecyclerViewAdapter selectedIngredientRecyclerViewAdapter;
    private IngredientFilterViewModel ingredientFilterViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportPostponeEnterTransition();
        setContentView(R.layout.activity_ingredient_filter);
        loadTextView();
        Glide.with(getApplicationContext()).load(R.drawable.pale_ingredients).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                supportStartPostponedEnterTransition();
                return false;
            }
        }).into((ImageView) findViewById(R.id.ingredient_filter_imageview));

        loadBottomSheet();


        RecyclerView unselectedRecyclerView = findViewById(R.id.ingredient_filter_bottom_sheet_unselected_recyclerview);
        StaggeredGridLayoutManager unselectedStaggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL);
        unselectedRecyclerView.setLayoutManager(unselectedStaggeredGridLayoutManager);
        unselectedIngredientRecyclerViewAdapter = new IngredientRecyclerViewAdapter(this, new IngredientRecyclerViewOnClickListener() {
            @Override
            public void onItemClick(String name, int position) {
                Toast.makeText(IngredientFilterActivity.this, "works " + position + name, Toast.LENGTH_SHORT).show();
                IngredientEntity selectedIngredient = unselectedIngredientRecyclerViewAdapter.getIngredient(position);
                ingredientFilterViewModel.addSelectedIngredient(selectedIngredient);
            }
        });
        unselectedRecyclerView.setAdapter(unselectedIngredientRecyclerViewAdapter);

        RecyclerView selectedRecyclerView = findViewById(R.id.ingredient_filter_bottom_sheet_selected_recyclerview);
        StaggeredGridLayoutManager selectedStaggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        selectedRecyclerView.setLayoutManager(selectedStaggeredGridLayoutManager);
        selectedIngredientRecyclerViewAdapter = new IngredientRecyclerViewAdapter(this, new IngredientRecyclerViewOnClickListener() {
            @Override
            public void onItemClick(String name, int position) {
                IngredientEntity selectedIngredient = selectedIngredientRecyclerViewAdapter.getIngredient(position);
                ingredientFilterViewModel.removeSelectedIngredient(selectedIngredient);
            }
        });

        selectedRecyclerView.setAdapter(selectedIngredientRecyclerViewAdapter);

        setupViewModel();


    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void loadTextView() {
        String ingredientFilterText = "Select ingredients to start searching";
        TextView ingredientFilterTV = findViewById(R.id.ingredient_filter_textview);

//        Spannable spannable = new SpannableString(ingredientFilterText);
//        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.colorSecondary)), 28, 39, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//        spannable.setSpan(new RelativeSizeSpan(1.2f), 28, 39, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//        ingredientFilterTV.setText(spannable);
        ingredientFilterTV.setText(ingredientFilterText);
    }

    private void loadBottomSheet() {

        LinearLayout bottomSheet = findViewById(R.id.ingredient_filter_bottom_sheet_linearlayout);
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        final ImageView arrowRotate = findViewById(R.id.ingredient_filter_bottom_sheet_rotate);


        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
                arrowRotate.setRotation(v * 180);
            }
        });

        ConstraintLayout constraintLayout = findViewById(R.id.ingredient_filter_bottom_sheet_peek_constraintlayout);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED)
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                else
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }

    private void setupViewModel() {
        ingredientFilterViewModel = ViewModelProviders.of(this).get(IngredientFilterViewModel.class);
        ingredientFilterViewModel.getUnselectedIngredients().observe(this, new Observer<List<IngredientEntity>>() {
            @Override
            public void onChanged(@Nullable List<IngredientEntity> ingredientEntities) {
                if (ingredientEntities == null) {
//                    Log.d("viewmodel", "entrei no null");
                } else {
//                    Log.d("viewmodel", "entrei viewmodel " + ingredientEntities.size());
                    unselectedIngredientRecyclerViewAdapter.setData(ingredientEntities);
                }
            }
        });

        ingredientFilterViewModel.getSelectedIngredients().observe(this, new Observer<List<IngredientEntity>>() {

            private TextView textView = findViewById(R.id.ingredient_filter_bottom_sheet_peek_textview);

            @Override
            public void onChanged(@Nullable List<IngredientEntity> ingredientEntities) {
                if (ingredientEntities == null) {
//                    Log.d("viewmodel", "entrei no null");
                } else {
//                    Log.d("viewmodel", "entrei viewmodel " + ingredientEntities.size());
                    selectedIngredientRecyclerViewAdapter.setData(ingredientEntities);
                    int size = ingredientEntities.size();
                    if (size == 1) {
                        textView.setText(" " + ingredientEntities.size() + " Ingredient Selected");
                    } else {
                        textView.setText(ingredientEntities.size() + " Ingredients Selected");
                    }
                }
            }
        });
    }

    public void delete(View view) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                RecipeIngredientDatabase.getInstance(getApplicationContext()).ingredienteDao().deleteAll();
            }
        });
    }

    public void testIngredients(View v) {
//        Log.d("viewmodel", "entrei test Ingredients");
        for (int i = 0; i < 3; i++)
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    RecipeIngredientDatabase.getInstance(getApplicationContext()).ingredienteDao().insertIngredient(new IngredientEntity("Eggs " + new Random().nextInt(9999)));
                }
            });

//        List<IngredientEntity> ingredientEntities = new ArrayList<>();
//        ingredientEntities.add(new IngredientEntity("Onions"));
//        ingredientEntities.add(new IngredientEntity("Salmon"));
//        ingredientEntities.add(new IngredientEntity("Potatoes"));
//        ingredientEntities.add(new IngredientEntity("Mushrooms"));
//        ingredientEntities.add(new IngredientEntity("Carrots"));
//        ingredientEntities.add(new IngredientEntity("Eggs"));
//
//
//        for (int i = 0; i < 40; i++) {
//            IngredientEntity ingredientEntity = new IngredientEntity("Bacon " + i);
//            ingredientEntities.add(ingredientEntity);
//        }
//        unselectedIngredientRecyclerViewAdapter.setData(ingredientEntities);
    }

}
