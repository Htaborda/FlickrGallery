package gallery.flickr.com.flickrgallery.screens;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import gallery.flickr.com.flickrgallery.BuildConfig;
import gallery.flickr.com.flickrgallery.MainActivity;
import gallery.flickr.com.flickrgallery.R;
import gallery.flickr.com.flickrgallery.utils.NetworkUtils;


/**
 * The screen fragment showing the search
 */

public class SearchScreen extends Fragment implements View.OnClickListener {
    private static final String TAG = "screen.search";

    public SearchScreen() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.entry_fragment, container, false);
        Button searchButton = (Button) fragmentView.findViewById(R.id.search_trigger);
        searchButton .setOnClickListener(this);
        return fragmentView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.search_trigger) {
            EditText searchEdit = (EditText)getView().findViewById(R.id.search_tags);
            String searchTerms = searchEdit.getText().toString();
            if (searchTerms.length()==0) {
                Toast.makeText(getActivity(), getResources().getString(R.string.error_message_empty_search), Toast.LENGTH_LONG).show();
            } else if (NetworkUtils.isNetworkAvailable(getActivity())){
                if (BuildConfig.DEBUG) Log.v(TAG, "Click on the search button:"+searchTerms+"*");
                Bundle bundle = new Bundle();
                bundle.putString(ListPhotoScreen.SEARCH_TERMS,searchTerms);
                ((MainActivity)this.getActivity()).switchToList(bundle);
            } else {
                String message = String.format(getResources().getString(R.string.error_message_no_network), searchTerms);
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
}