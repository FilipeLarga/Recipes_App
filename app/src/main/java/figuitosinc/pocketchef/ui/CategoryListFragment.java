package figuitosinc.pocketchef.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import figuitosinc.pocketchef.CategoriesEnum;
import figuitosinc.pocketchef.R;
import figuitosinc.pocketchef.TransitionTest;
import figuitosinc.pocketchef.category_recyclerview.Category;
import figuitosinc.pocketchef.category_recyclerview.CategoryRecyclerViewAdapter;
import figuitosinc.pocketchef.database.RecipeCategoryPOJO;
import figuitosinc.pocketchef.database.ViewRecipeActivityViewModel;

public class CategoryListFragment extends Fragment {

    private List<Category> categories = new ArrayList<>();
    private Map<String, Integer> categoriesCountMap = new HashMap<>();
    LayoutInflater inflater;
    View view;
    //    private Map<String, Integer> categoriesFavoriteMap = new HashMap<>();
    private CategoryRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category_list_big, container, false);
        setupViewModel();
        RecyclerView recyclerView = view.findViewById(R.id.categories_fragment_recyclerView);
        adapter = new CategoryRecyclerViewAdapter(getContext(), categories);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        startRandom((CardView) view.findViewById(R.id.categories_fragment_random_cardview));

        return view;
    }

    private void setupViewModel() {
        ViewRecipeActivityViewModel viewRecipeActivityViewModel = ViewModelProviders.of(this).get(ViewRecipeActivityViewModel.class);
        viewRecipeActivityViewModel.getRecipeCategory().observe(this, new Observer<List<RecipeCategoryPOJO>>() {
            @Override
            public void onChanged(@Nullable List<RecipeCategoryPOJO> list) {

                //Reset the map
                categoriesCountMap.clear();
                int favorites = 0;
//                categoriesFavoriteMap.clear();
                initializeMaps();
                if (list != null) {
                    for (RecipeCategoryPOJO category : list) {

                        if (category.favorite)
                            favorites++;

                        int previousValue = categoriesCountMap.get(category.categoryOne);
                        categoriesCountMap.put(category.categoryOne, previousValue + 1);
//                        if (category.favorite) {
//                            previousValue = categoriesFavoriteMap.get(category.categoryOne);
//                            categoriesFavoriteMap.put(category.categoryOne, previousValue + 1);
//                        }
                        previousValue = categoriesCountMap.get(category.categoryTwo);
                        categoriesCountMap.put(category.categoryTwo, previousValue + 1);
//                        if (category.favorite) {
//                            previousValue = categoriesFavoriteMap.get(category.categoryTwo);
//                            categoriesFavoriteMap.put(category.categoryTwo, previousValue + 1);
//                        }
                        previousValue = categoriesCountMap.get(category.categoryThree);
                        categoriesCountMap.put(category.categoryThree, previousValue + 1);
//                        if (category.favorite) {
//                            previousValue = categoriesFavoriteMap.get(category.categoryThree);
//                            categoriesFavoriteMap.put(category.categoryThree, previousValue + 1);
//                        }
                    }
                }

//                TextView textView = view.findViewById(R.id.categories_fragment_favorites_cardview_recipe_count_textview);
//                textView.setText(favorites + "");
////                textView = view.findViewById(R.id.categories_fragment_all_cardview_recipe_count_textview);
//                textView.setText(list.size() + "");

                initializeCategoryList();
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initializeCategoryList() {
        String[] aux = CategoriesEnum.categories;
        for (int i = 0; i < CategoriesEnum.count; i++) {
            categories.add(new Category(aux[i], categoriesCountMap.get(aux[i])/*, categoriesFavoriteMap.get(aux[i])*/));
        }
    }

    private void initializeMaps() {
        for (String category : CategoriesEnum.categories) {
            categoriesCountMap.put(category, 0);
//            categoriesFavoriteMap.put(category, 0);
        }
    }

    public void startRandom(CardView cv) {
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Click");
                Intent intent = new Intent(getContext(), TransitionTest.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(getActivity(), view.findViewById(R.id.categories_fragment_random_cardview_icon_imageview), "sharedtransition");
                startActivity(intent, options.toBundle());

            }
        });
    }

}
