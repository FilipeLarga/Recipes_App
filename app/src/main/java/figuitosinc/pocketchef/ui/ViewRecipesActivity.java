package figuitosinc.pocketchef.ui;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import figuitosinc.pocketchef.R;

public class ViewRecipesActivity extends AppCompatActivity {

    private CategoryListFragment categoryListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipes);

        Toolbar toolbar = findViewById(R.id.view_recipes_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorSecondary, null), PorterDuff.Mode.SRC_ATOP);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.view_recipes_collapsingtoolbar);
        collapsingToolbarLayout.setCollapsedTitleTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.abel_regular));
        collapsingToolbarLayout.setExpandedTitleTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.abel_regular));

        if (savedInstanceState == null) {
            categoryListFragment = new CategoryListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.view_recipes_fragment_container, categoryListFragment).commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_recipes_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setIconifiedByDefault(true);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println(item.getItemId());
        System.out.println(R.id.menu_settings + " settings");
        System.out.println(R.id.menu_search + " search");
        switch (item.getItemId()) {
            case android.R.id.home:
                System.out.println("Entrei Home");
                finish();
                return true;
            case R.id.menu_settings:
                System.out.println("Entrei Settings");
                return true;
            case R.id.menu_search:
                System.out.println("Entrei");
                final NestedScrollView nestedScrollView = findViewById(R.id.view_recipes_scrollview);
                nestedScrollView.post(new Runnable() {
                    public void run() {
                        nestedScrollView.fullScroll(View.FOCUS_UP);
                    }
                });
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
