package gallery.flickr.com.flickrgallery.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
/**
 * Class wrapping network-related utils
 * */
public class NetworkUtils {


    /**
     * Method used to detect whether or not the device has some sort of connectivity
     * */
    public static boolean isNetworkAvailable(Activity act) {
        ConnectivityManager cm = (ConnectivityManager)act.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
