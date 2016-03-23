package gallery.flickr.com.flickrgallery;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import gallery.flickr.com.flickrgallery.screens.ListPhotoScreen;
import gallery.flickr.com.flickrgallery.screens.SearchScreen;
import gallery.flickr.com.flickrgallery.screens.ShowPhotoScreen;
import gallery.flickr.com.flickrgallery.utils.InputUtils;

/**
 * Main activity for the Application, as it has been designed, the app is a single activity one.
 * This activity acts also as main controller for the App, it receives various callbacks from the
 * Screens (aka Fragments).
 *
 * https://api.flickr.com/services/rest/?&method=flickr.photos.search&api_key=612cc853319c58de9c64a19aa984f957&text=cats+and+dogs&format=json
 *
 * https://api.flickr.com/services/rest/?method=flickr.photos.getSizes&api_key=d301b1a7c2e8db9e22b4bb8240fa151b&photo_id=25275539064&format=json&nojsoncallback=1
 * &auth_token=72157665944893162-59fb6357a864e426&api_sig=cd36e82d46a80360dd5a4d4dca9242e5
 * https://farm2.staticflickr.com/1475/25275539064_27bf0d644e_s.jpg
 * */
public class MainActivity extends FragmentActivity {
    private static final String TAG = "main";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_point);
        Fragment fragment = new SearchScreen();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, fragment);
        transaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_entry_point, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method invoked to switch the view to the list of results
     * */
    public void switchToList(Bundle bundle) {
        InputUtils.closeKeyboard(this);
        if (BuildConfig.DEBUG) Log.v(TAG, "Going to show the list of results");
        ListPhotoScreen listPhotoScreen = new ListPhotoScreen();
        listPhotoScreen.setArguments(bundle);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, listPhotoScreen);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void switchToDetail(Bundle bundle) {
        if (BuildConfig.DEBUG) Log.v(TAG, "Going to show the list of results");
        ShowPhotoScreen showPhotoScreen = new ShowPhotoScreen();
        showPhotoScreen.setArguments(bundle);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, showPhotoScreen);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        getFragmentManager().popBackStack();
    }

    //TODO: Clean the image cache
    //@Override
    //public void onDestroy() {}
}
