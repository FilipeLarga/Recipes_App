package figuitosinc.pocketchef.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import figuitosinc.pocketchef.CategoriesEnum;
import figuitosinc.pocketchef.R;
import figuitosinc.pocketchef.category_recyclerview.Category;
import figuitosinc.pocketchef.category_recyclerview.CategoryRecyclerViewAdapter;
import figuitosinc.pocketchef.database.CategoryPOJO;
import figuitosinc.pocketchef.database.ViewRecipeActivityViewModel;

public class CategoryListFragment extends Fragment {

    private List<Category> categories = new ArrayList<>();
    private Map<String, Integer> categoriesCountMap = new HashMap<>();
    private Map<String, Integer> categoriesFavoriteMap = new HashMap<>();
    private CategoryRecyclerViewAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_category_list, container, false);
        setupViewModel();
        RecyclerView recyclerView = view.findViewById(R.id.categories_fragment_recyclerView);
        adapter = new CategoryRecyclerViewAdapter(getContext(), categories);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        FloatingActionButton fab = view.findViewById(R.id.view_recipes_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                View inflatedView = getLayoutInflater().inflate(R.layout.cardview_category_big, null);
//                ConstraintLayout constraintLayoutBig = inflatedView.findViewById(R.id.cardview_big_constraintLayout);
//
//                ConstraintLayout constraintLayoutSmall = view.findViewById(R.id.cardview_small_constraintLayout);

                View inflatedView = getLayoutInflater().inflate(R.layout.cardview_simple_big, null);
                ConstraintLayout constraintLayoutBig = inflatedView.findViewById(R.id.categories_fragment_simple_cardview_constraintLayout);

                ConstraintLayout constraintLayoutSmall = view.findViewById(R.id.categories_fragment_favorites_cardview_constraintLayout);

                ConstraintSet constraintSetSmall = new ConstraintSet();
                constraintSetSmall.clone(constraintLayoutSmall);

                ConstraintSet constraintSetBig = new ConstraintSet();
                constraintSetBig.clone(constraintLayoutBig);

                TransitionManager.beginDelayedTransition(constraintLayoutSmall);
                constraintSetBig.applyTo(constraintLayoutSmall);

                System.out.println("Acabou");

            }
        });


        return view;
    }

    private void setupViewModel() {
        ViewRecipeActivityViewModel viewRecipeActivityViewModel = ViewModelProviders.of(this).get(ViewRecipeActivityViewModel.class);
        viewRecipeActivityViewModel.getRecipeCategory().observe(this, new Observer<List<CategoryPOJO>>() {
            @Override
            public void onChanged(@Nullable List<CategoryPOJO> list) {

                //Reset the map
                categoriesCountMap.clear();
                categoriesFavoriteMap.clear();
                initializeMaps();
                if (list != null) {
                    for (CategoryPOJO category : list) {
                        int previousValue = categoriesCountMap.get(category.categoryOne);
                        categoriesCountMap.put(category.categoryOne, previousValue + 1);
                        if (category.favorite) {
                            previousValue = categoriesFavoriteMap.get(category.categoryOne);
                            categoriesFavoriteMap.put(category.categoryOne, previousValue + 1);
                        }
                        previousValue = categoriesCountMap.get(category.categoryTwo);
                        categoriesCountMap.put(category.categoryTwo, previousValue + 1);
                        if (category.favorite) {
                            previousValue = categoriesFavoriteMap.get(category.categoryTwo);
                            categoriesFavoriteMap.put(category.categoryTwo, previousValue + 1);
                        }
                        previousValue = categoriesCountMap.get(category.categoryThree);
                        categoriesCountMap.put(category.categoryThree, previousValue + 1);
                        if (category.favorite) {
                            previousValue = categoriesFavoriteMap.get(category.categoryThree);
                            categoriesFavoriteMap.put(category.categoryThree, previousValue + 1);
                        }
                    }
                }
                initializeCategoryList();
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initializeCategoryList() {
        String[] aux = CategoriesEnum.categories;
        for (int i = 0; i < CategoriesEnum.count; i++) {
            categories.add(new Category(aux[i], categoriesCountMap.get(aux[i]), categoriesFavoriteMap.get(aux[i])));
        }
    }

    private void initializeMaps() {
        for (String category : CategoriesEnum.categories) {
            categoriesCountMap.put(category, 0);
            categoriesFavoriteMap.put(category, 0);
        }
    }

}
