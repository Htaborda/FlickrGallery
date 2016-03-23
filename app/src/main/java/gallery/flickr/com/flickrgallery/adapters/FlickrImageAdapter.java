package gallery.flickr.com.flickrgallery.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import gallery.flickr.com.flickrgallery.BuildConfig;
import gallery.flickr.com.flickrgallery.R;
import gallery.flickr.com.flickrgallery.loaders.ImageLoader;
import gallery.flickr.com.flickrgallery.model.FlickrSearchItem;
import gallery.flickr.com.flickrgallery.utils.ImageManager;


public class FlickrImageAdapter extends ArrayAdapter<FlickrSearchItem> {

    private static final String TAG = "adapter.image";

    public FlickrImageAdapter(Context context, FlickrSearchItem[] objects) {
        super(context, R.layout.search_item, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            FlickrSearchItem item = getItem(position);
            ViewHolder vh = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_item, null);
                ImageView image = (ImageView) convertView.findViewById(R.id.image);
                TextView tw = (TextView)convertView.findViewById(R.id.image_title);
                vh = new ViewHolder(image,tw,item.getUrl_big());
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            vh.textView.setText(item.getTitle());
            Bitmap cached = ImageManager.getInstance(getContext()).getBitmap(String.valueOf(item.getUrl_small().hashCode()));
            if (cached!=null) {
                vh.imageView.setImageBitmap(cached);
                if (BuildConfig.DEBUG) Log.v(TAG, "Returning the view for item n."+position+", id:"+item.getId()+" from cache");
            } else {
                new ImageLoader(vh.imageView,getContext()).execute(item.getUrl_small());
                if (BuildConfig.DEBUG) Log.v(TAG, "Returning the view for item n."+position+", id:"+item.getId()+" deferred");
            }
            return convertView;
    }


    public class ViewHolder {

        ViewHolder(ImageView imageView, TextView textView,String url) {
            this.imageView = imageView;
            this.textView = textView;
            this.urlBig = url;
        }

        ImageView imageView;
        TextView textView;
        // service information
        String urlBig;
        public String getUrlBig() {
            return urlBig;
        }
    }
}