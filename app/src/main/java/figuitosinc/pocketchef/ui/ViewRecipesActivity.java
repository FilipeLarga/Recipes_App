package figuitosinc.pocketchef.ui;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import figuitosinc.pocketchef.R;

public class ViewRecipesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipes);

        Toolbar toolbar = findViewById(R.id.view_recipes_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorSecondary, null), PorterDuff.Mode.SRC_ATOP);

        CategoryListFragment categoryListFragment = new CategoryListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.view_recipes_nestedscrollview, categoryListFragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_recipes_menu, menu);
        return true;
    }


}
