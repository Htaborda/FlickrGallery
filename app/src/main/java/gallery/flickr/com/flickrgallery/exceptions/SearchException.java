package gallery.flickr.com.flickrgallery.exceptions;

/**
 * Exception bound to the search functionality
 * */
public class SearchException extends Exception{

    public SearchException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}