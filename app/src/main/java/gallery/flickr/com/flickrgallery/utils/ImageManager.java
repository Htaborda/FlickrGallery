package gallery.flickr.com.flickrgallery.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

/**
 * Class responsible for caching the images
 * */
public class ImageManager  {
            private static final String TAG = "utils.image";
            private static ImageManager instance = null;
            private File cacheDir;
            private boolean initialised;

            private ImageManager(Context ctx) {
                String sdState = android.os.Environment.getExternalStorageState();
                if (sdState.equals(android.os.Environment.MEDIA_MOUNTED)) {
                    File sdDir = android.os.Environment.getExternalStorageDirectory();
                    cacheDir = new File(sdDir,"data/flickrGallery");
                } else {
                    cacheDir = ctx.getCacheDir();
                }
                if(!cacheDir.exists())
                    cacheDir.mkdirs();
            }

            public static ImageManager getInstance(Context ctx) {
                    if (instance==null) {
                        instance = new ImageManager(ctx);
                    }
                    return instance;
            }

            public void saveImage(String imageName,Bitmap bm) {
                Log.v(TAG,"Saving image as "+imageName);
                FileOutputStream out = null;
                try {
                    out = new FileOutputStream(new File(cacheDir, imageName));
                    bm.compress(Bitmap.CompressFormat.PNG, 90, out);
                } catch (Exception e) {
                    Log.v(TAG,"Ex",e);
                }
                finally {
                    try { if (out != null ) out.close(); }
                    catch(Exception ex) {
                        Log.v(TAG,"Ex",ex);
                    }
                }
            }

            public Bitmap getBimap(String imageName) {
                try {
                    File f = new File(cacheDir, imageName);
                    Bitmap bitmap = BitmapFactory.decodeFile(f.getPath());
                    Log.v(TAG,"Retrieving image as "+imageName);
                    if(bitmap != null)
                        return bitmap;
                } catch (Exception ex) {
                    Log.v(TAG,"Ex",ex);
                }
                return null;
            }

}