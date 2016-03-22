package gallery.flickr.com.flickrgallery.model;


public class UrlPair {
        private String small;
        private String big;

        public UrlPair(String small, String big) {
            this.small = small;
            this.big = big;
        }

        public String getSmall() {
            return small;
        }

        public String getBig() {
            return big;
        }
}
