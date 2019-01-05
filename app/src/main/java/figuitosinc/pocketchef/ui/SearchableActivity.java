package figuitosinc.pocketchef.ui;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import figuitosinc.pocketchef.CategoriesEnum;
import figuitosinc.pocketchef.R;

public class SearchableActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            System.out.println("Recebi: " + query);
            searchCategory(query);
        }
    }

    private List<String> searchCategory(String query) {
        List<String> results = new ArrayList<>();

        for (String category : CategoriesEnum.categories) {
            if (query.equalsIgnoreCase(category)) {
                results.add(category);
                System.out.println(category);
            }
        }

        return results;
    }

}
