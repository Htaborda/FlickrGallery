To build the app invoke gradle on the command line, or import the project and build with the Android Studio.

The app is organised as a Single Activity App, the Fragments (named "Screens" trough-out the code) implements the basic functionalities
requested.

The app is forced to work in portrait only, due to time constraints, the refresh of the app (e.g. Recreating the single activity etc etc) has not been implemented.

The Flickr API follows the following steps:

1 - The search call returns a list of objects (e.g. photos, each one with its own ID).
2 - Upon retrieving a photo, another call is necessary (thus, one call per image) to retrieve the URLs associated with the image itself (a set of different formats are available for each ID).
3 - Another call is then done in order to download the actual file (e.g. the image itself).

Due to the points above the App fixed the page size to 10 items per time, a cache of images has been implemented in the file system in order to avoid
retrieving an image many times while the user scrolls the list.

Please note that when showing the image (in the ShowPhotoScreen Fragment) the image is NOT downloaded before but only when requested,
this is due to (try to) minimise the amount of http calls made.

Possible improvements:

 - Make the cache behave as a real LRU (e.g. delete older files once the number of files in the cache gets over a certain value)
 - Clean up the cache when the app dies (e.g. once the onDestroy() is called on the only activity)
 - Make the app working in landscape.
 - Downloading the image in the closest size depending on the device resolution.
 - Change the download logic to a more agressive strategy to improve UX in slow network conditions.
 - Of course, polish the UI and add some eye-candy.

Known problems:
 - In certain network conditions, the wrong photos are displayed when the thumbnail is clicked, and the thumbnail list scrolls back to the top.



An assembled (debug version) APK is enclosed at the same level of this file.
 

