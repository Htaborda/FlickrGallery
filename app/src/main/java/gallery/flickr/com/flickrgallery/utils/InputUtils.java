package gallery.flickr.com.flickrgallery.utils;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


/**
 * Class wrapping utils to deal with input related tasks
 * */
public class InputUtils {


    /**
     * Method closing the soft keyboard
     * */
    public static void closeKeyboard(Activity act) {
        View view = act.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)act.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }
}
