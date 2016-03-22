package gallery.flickr.com.flickrgallery.config;


public class AppConfig {
        private static final String API_KEY = "612cc853319c58de9c64a19aa984f957";
        private static final String API_SECRET = "bcbe828872ebb4cc";

        public static String getApiKey() {
            return API_KEY;
        }

        public static String getApiSecret() {
            return API_SECRET;
        }
}
