package gallery.flickr.com.flickrgallery.config;


public class AppConfig {

        //TODO: The API keys shouldn't be here, obviously
        private static final String API_KEY = "dde4cb123b9d7a9e472d42006a485cfc";
        private static final String API_SECRET = "918b990e68c35ab8";

        public static String getApiKey() {
            return API_KEY;
        }

        public static String getApiSecret() {
            return API_SECRET;
        }
}
