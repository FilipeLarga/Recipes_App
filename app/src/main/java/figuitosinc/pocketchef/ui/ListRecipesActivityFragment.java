package figuitosinc.pocketchef.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import figuitosinc.pocketchef.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListRecipesActivityFragment extends Fragment {

    public ListRecipesActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_recipes, container, false);
    }
}
