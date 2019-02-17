package figuitosinc.pocketchef.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import figuitosinc.pocketchef.R;

public class IngredientFilterActivity extends AppCompatActivity {

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
}
