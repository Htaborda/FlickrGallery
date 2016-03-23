package gallery.flickr.com.flickrgallery.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;


import java.util.List;

import gallery.flickr.com.flickrgallery.model.FlickrSearchItem;
import gallery.flickr.com.flickrgallery.model.UrlPair;
import gallery.flickr.com.flickrgallery.services.FlickrProxy;

/**
 * Loader responsible for searching images based on text
 * */
public class FlickrSearchLoader extends AsyncTaskLoader<List<FlickrSearchItem>> {
    private static final String TAG = "loader.search";
    private String searchTerms;
    private int page;

    public FlickrSearchLoader(Context context,String searchString, int page) {
        super(context);
        Log.v(TAG, "Loader created with search terms:" + searchString+" and page:"+page);
        this.searchTerms = searchString;
        this.page = page;
    }

    @Override
    public List<FlickrSearchItem> loadInBackground() {
        Log.v(TAG, "loading images");
        List<FlickrSearchItem> items = FlickrProxy.getInstance().search(this.searchTerms,this.page,FlickrProxy.PER_PAGE_DEFAULT);
        for (FlickrSearchItem item:items) {
            UrlPair pair = FlickrProxy.getInstance().getUrls(item.getId());
            item.setUrl_big(pair.getBig());
            item.setUrl_small(pair.getSmall());
        }
        return items;
    }
}

