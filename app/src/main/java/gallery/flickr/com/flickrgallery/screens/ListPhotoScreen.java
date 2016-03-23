package gallery.flickr.com.flickrgallery.screens;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import gallery.flickr.com.flickrgallery.BuildConfig;
import gallery.flickr.com.flickrgallery.MainActivity;
import gallery.flickr.com.flickrgallery.R;
import gallery.flickr.com.flickrgallery.adapters.FlickrImageAdapter;
import gallery.flickr.com.flickrgallery.loaders.FlickrSearchLoader;
import gallery.flickr.com.flickrgallery.model.FlickrSearchItem;
import gallery.flickr.com.flickrgallery.services.FlickrProxy;


/**
 * Screen responsible for showing the list of photos, it also retrieves the next batch of photos when the user gets close to the
 * last one.
 * */

public class ListPhotoScreen extends ListFragment implements LoaderManager.LoaderCallbacks<List<FlickrSearchItem>>, AbsListView.OnScrollListener {

    public static String SEARCH_TERMS = "search.terms";

    private List<FlickrSearchItem> dataShown = null;
    private static final String TAG = "screen.list";
    private String searchTerms;
    private int currentPage = 0;
    private int loadingPage = 1;

    public ListPhotoScreen() {
        if (BuildConfig.DEBUG) Log.v(TAG, "List Photos screen created");
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.searchTerms = (String) args.get(SEARCH_TERMS);
        if (BuildConfig.DEBUG) Log.v(TAG, "Setting the search terms:"+this.searchTerms);
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (BuildConfig.DEBUG) Log.v(TAG,"click on item");
        Bundle bundle = new Bundle();
        FlickrImageAdapter.ViewHolder vh = (FlickrImageAdapter.ViewHolder) v.getTag();
        bundle.putString(ShowPhotoScreen.PHOTO_ID,vh.getUrlBig());
        ((MainActivity)this.getActivity()).switchToDetail(bundle);
    }

    @Override
    public Loader<List<FlickrSearchItem>> onCreateLoader(int id, Bundle args) {
        if (BuildConfig.DEBUG) Log.v(TAG,"Loader fired");
        return new FlickrSearchLoader(getActivity(),this.searchTerms,loadingPage);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (BuildConfig.DEBUG) Log.v(TAG,"Activity created");
        getLoaderManager().initLoader(0, savedInstanceState, this).forceLoad();
    }

    @Override
    public void onLoadFinished(Loader<List<FlickrSearchItem>> loader, List<FlickrSearchItem> data) {
        currentPage++;
        if (this.dataShown==null) {
            getListView().setOnScrollListener(this);
            this.dataShown = data;
        } else {
            this.dataShown.addAll(data);
            String message = String.format(getResources().getString(R.string.loading_page_received), Integer.toString(loadingPage));
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
        setListAdapter(new FlickrImageAdapter(this.getActivity(), this.dataShown.toArray(new FlickrSearchItem[FlickrProxy.PER_PAGE_DEFAULT])));
        ((ArrayAdapter)getListAdapter()).notifyDataSetChanged();
        if (BuildConfig.DEBUG) Log.v(TAG, "List Photos, finish loading, data:"+data.size()+", currentPage:"+currentPage+", loadingPage:"+loadingPage);
    }

    @Override
    public void onLoaderReset(Loader<List<FlickrSearchItem>> loader) {
        if (BuildConfig.DEBUG) Log.v(TAG, "List Photos, reset the loader");
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (BuildConfig.DEBUG) Log.v(TAG, "Scroll changed");
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (getListAdapter()!=null && (getListView().getLastVisiblePosition() ==  getListAdapter().getCount() -1)) {
            if (BuildConfig.DEBUG) Log.v(TAG, "Scrolling to the bottom");
            if (currentPage==loadingPage) {
                loadingPage++;
                if (BuildConfig.DEBUG) Log.v(TAG, "Load next batch");
                getLoaderManager().restartLoader(0, null, this).forceLoad();
                String message = String.format(getResources().getString(R.string.loading_page_fired), Integer.toString(loadingPage));
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}