package gallery.flickr.com.flickrgallery.screens;


import android.app.Fragment;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import gallery.flickr.com.flickrgallery.BuildConfig;
import gallery.flickr.com.flickrgallery.R;
import gallery.flickr.com.flickrgallery.loaders.FlickrSearchLoader;
import gallery.flickr.com.flickrgallery.loaders.ImageLoader;
import gallery.flickr.com.flickrgallery.model.FlickrSearchItem;


/**
 * The screen showing the details of an image
 * */
public class ShowPhotoScreen extends Fragment {
    private static final String TAG = "screen.show";
    public static String PHOTO_ID = "item.show";

    private String photoUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.detail_fragment, container, false);
        new ImageLoader((ImageView) v.findViewById(R.id.image_big),getActivity()).execute(this.photoUrl);
        return v;
    }


    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.photoUrl = (String) args.get(PHOTO_ID);
        if (BuildConfig.DEBUG) Log.v(TAG, "Showing the picture:" + this.photoUrl);
    }




}
