package gallery.flickr.com.flickrgallery.model;


public class FlickrSearchItem {
    private String id;
    private String title;
    private String url_small;
    private String url_big;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl_small() {
        return url_small;
    }

    public void setUrl_small(String url_small) {
        this.url_small = url_small;
    }

    public String getUrl_big() {
        return url_big;
    }

    public void setUrl_big(String url_big) {
        this.url_big = url_big;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
