package figuitosinc.pocketchef;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class TransitionTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_test);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.transition_imageview2).setVisibility(View.VISIBLE);
            }
        }, 1000);

    }

}
