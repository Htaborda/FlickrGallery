package gallery.flickr.com.flickrgallery.services;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import gallery.flickr.com.flickrgallery.BuildConfig;
import gallery.flickr.com.flickrgallery.config.AppConfig;
import gallery.flickr.com.flickrgallery.model.FlickrSearchItem;
import gallery.flickr.com.flickrgallery.model.UrlPair;

/**
 * Proxy class for the Flickr API, implemented via the Singleton pattern.
 * This choice has been made aiming to have a testable class
 * */
public class FlickrProxy {
    /** The unique instance*/
    private static FlickrProxy instance = new FlickrProxy();
    /** The API key (to be loaded via  {@link gallery.flickr.com.flickrgallery.config.AppConfig} */
    private String apiKey = AppConfig.getApiKey();
    /** The API secret (to be loaded via {@link gallery.flickr.com.flickrgallery.config.AppConfig} */
    private String apiSecret = AppConfig.getApiSecret();
    /** The tag used to log the search operation*/
    private static final String TAG_SEARCH = "service.search";
    /** The base url of the Flickr API for search*/
    private static final String SEARCH_BASE_URL = "https://api.flickr.com/services/rest/?&method=flickr.photos.search";
    /** The default number of images to be loaded per batch*/
    public static final int PER_PAGE_DEFAULT = 10;
    /** The base url of the Flickr API for take the urls of an image*/
    private static final String GET_IMAGE_SRC_BASE_URL = " https://api.flickr.com/services/rest/?method=flickr.photos.getSizes";

    private FlickrProxy() {

    }

    /**
     * Getter for the (unique) instance
     * */
    public static FlickrProxy getInstance() {
        return instance;
    }

    public List<FlickrSearchItem> search(String searchTerms,int page,int perPage) {
        if (BuildConfig.DEBUG) Log.v(TAG_SEARCH,"Search for "+searchTerms);
        List<FlickrSearchItem> ret = new ArrayList<FlickrSearchItem>(PER_PAGE_DEFAULT);
        StringBuilder urlBuilder = new StringBuilder(SEARCH_BASE_URL);
        urlBuilder.append("&api_key="+this.apiKey).append("&text="+cleanSearchTerm(searchTerms)+"&format=json")
        .append("&per_page=").append(Integer.toString(perPage)).append("&page=").append(Integer.toString(page))
        .append("&nojsoncallback=1");
        if(BuildConfig.DEBUG) Log.v(TAG_SEARCH," URL:"+urlBuilder.toString());
        try {
            String json = getJSON(urlBuilder.toString());
            if(BuildConfig.DEBUG) Log.v(TAG_SEARCH," Found:"+json);
            JSONObject jsonObject = new JSONObject(json);
            int numPhotos = jsonObject.getJSONObject("photos").getJSONArray("photo").length();
            for (int i=0;i<numPhotos;i++) {
                JSONObject jsonItem = jsonObject.getJSONObject("photos").getJSONArray("photo").getJSONObject(i);
                FlickrSearchItem flickrSearchItem = new FlickrSearchItem();
                flickrSearchItem.setId(jsonItem.getString("id"));
                flickrSearchItem.setTitle(jsonItem.getString("title"));
                ret.add(flickrSearchItem);
            }
            if(BuildConfig.DEBUG) Log.v(TAG_SEARCH," Found number:"+jsonObject.getJSONObject("photos").getJSONArray("photo").length());
        } catch (JSONException e) {
            if(BuildConfig.DEBUG) Log.v(TAG_SEARCH," Ex",e);
        }
        return ret;
    }

    public UrlPair getUrls(String imageId) {
        if (BuildConfig.DEBUG) Log.v(TAG_SEARCH,"Finding the url for image id:"+imageId);
        String small = null;
        String big = null;
        StringBuilder urlBuilder = new StringBuilder(GET_IMAGE_SRC_BASE_URL);
        urlBuilder.append("&api_key=").append(apiKey).append("&photo_id=")
                  .append(imageId).append("&format=json&nojsoncallback=1");
        try {
            JSONObject jsonObject = new JSONObject(getJSON(urlBuilder.toString())).getJSONObject("sizes");
            JSONArray jsonArray = jsonObject.getJSONArray("size");
            int numSizes = jsonArray.length();
            for (int i =0;i<numSizes;i++) {
                JSONObject jObject = jsonArray.getJSONObject(i);
                if ("Large Square".equals(jObject.getString("label"))) {
                    small = jObject.getString("source");
                }
                if ("Large".equals(jObject.getString("label"))) {
                    big = jObject.getString("source");
                }
            }
        } catch (JSONException e) {
            if(BuildConfig.DEBUG) Log.v(TAG_SEARCH," Ex",e);
        }
        return new UrlPair(small,big);
    }


    private static String getJSON(String urlPath) {
        if(BuildConfig.DEBUG) Log.v(TAG_SEARCH," URL:"+urlPath);
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urlPath);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return readStream(in);
        } catch(MalformedURLException muE) {
            muE.printStackTrace();
        } catch (IOException e) {
            if(BuildConfig.DEBUG) Log.v(TAG_SEARCH," Ex",e);
        }  finally {
            if (urlConnection!=null)urlConnection.disconnect();
        }
        return null;
    }

    private static String readStream(InputStream in) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String nextLine = "";
            while ((nextLine = reader.readLine()) != null) {
                sb.append(nextLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private String cleanSearchTerm(String searchTerms) {
        return searchTerms.replace(' ','+').trim();

    }
}
