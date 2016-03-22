package gallery.flickr.com.flickrgallery.loaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

import gallery.flickr.com.flickrgallery.BuildConfig;
import gallery.flickr.com.flickrgallery.utils.ImageManager;

/**
 * Async task used to load an image and
 * change on the fly the image inside the imageview
 * */
public class ImageLoader extends AsyncTask<String, Void, Bitmap> {

    private static final String TAG = "loader.image";

    ImageView imageView;
    private Context ctx;

    public ImageLoader(ImageView imageView, Context ctx) {
        this.imageView = imageView;
        this.ctx = ctx;
    }

    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap bitmap = null;
        if (BuildConfig.DEBUG) Log.v(TAG, "Downloading image from " + url);
        try {
            InputStream in = new java.net.URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(in);
            ImageManager.getInstance(this.ctx).saveImage(String.valueOf(url.hashCode()),bitmap);
        } catch (Exception e) {
            Log.v(TAG,"Ex",e);
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }
}
