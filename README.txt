To build the app invoke gradle on the command line, or build with the Android Studio.

The app is organised as a Single Activity App, the Fragments (named "Screens" trough-out the code) implements the basic functionalities
requested.

The app is forced to work in portrait only, due to time constraints, the refresh of the app (e.g. Recreating the single activity etc etc) has not been implemented.

The Flickr API turned out to work as follows:

1 - The search call returns a list of objects (e.g. photos, each one with its own ID).
2 - Upon retrieving a photo, another call is necessary (thus, one call per image) to retrieve the URLs associated with the image itself.
3 - Another call is then done in order to download the actual file (e.g. the image itself).

Due to the points above the App fixed the page size to 10 items per time, a cache of images has been implemented in order to avoid 
retrieving an image many times while the user scrolls the list.

Possible improvements:

 - Make the cache a real LRU (e.g. delete older files once the number of files in the cache gets over a certain value)
 - Clean up the cache when the app dies (e.g. once the onDestroy() is called on the only activity)
 - Of course, make the app working in landscape.
 - Downloading the image in the "correct" size depending on the device resolution.

Notice that when showing the image (in the ShowPhotoScreen Fragment) the image is NOT downloaded before but only when requested, 
this is due to (try to) minimise the amount of http calls made.

An assembled (debug version) APK is enclosed at the same level of this file.
 

