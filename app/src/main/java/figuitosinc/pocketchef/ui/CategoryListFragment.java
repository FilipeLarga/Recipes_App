package figuitosinc.pocketchef.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import figuitosinc.pocketchef.category_recyclerview.Category;
import figuitosinc.pocketchef.category_recyclerview.CategoryRecyclerViewAdapter;
import figuitosinc.pocketchef.database.CategoryPOJO;
import figuitosinc.pocketchef.database.RecipeViewModel;

public class CategoryListFragment extends Fragment {

    private List<Category> categories = new ArrayList<>();
    private Map<String, Integer> categoriesCountMap = new HashMap<>();
    private Map<String, Integer> categoriesFavoriteMap = new HashMap<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);
        setupViewModel();
        RecyclerView recyclerView = view.findViewById(R.id.categories_fragment_recyclerView);
        CategoryRecyclerViewAdapter adapter = new CategoryRecyclerViewAdapter(getContext(), categories);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        return view;
    }

    private void setupViewModel() {
        RecipeViewModel recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        recipeViewModel.getRecipeCategory().observe(this, new Observer<List<CategoryPOJO>>() {
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
