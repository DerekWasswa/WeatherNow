Weather Now
==========

A simple app which displays the weather for the users' location.



## Getting Started and Installation

1. Clone this repository to your machine.

2. In Android Studio, under the file menu select open, then select an open and navigate to the project you just unzipped.

3. The build environment builds a secret api_key for the weather service. Go to OpenWeather(https://openweathermap.org/api) and generate the api_key to use.

4. Open gradle.properties file and add a key-value pair for the api key with the key of OPEN_WEATHER_APIKEY

4. Build the project using `./gradlew build`.

5. Run the application on a connected device or emulator.

### Prerequisites

1. [Set up Android Studio (developed with 4.0)](https://developer.android.com/studio/install)

2. [Enable Kotlin in Android Studio](https://medium.com/@elye.project/setup-kotlin-for-android-studio-1bffdf1362e8)

3. [Run application on emulator](https://developer.android.com/studio/run/emulator)

4. [Run application on android device](https://developer.android.com/studio/run/device)


## Demo/UIs
![Thursday Weather](https://i.postimg.cc/rp3w2Jhz/Screenshot-20220331-015016-Weather-Now.jpg "Mannheim Thursday")
![Friday Weather](https://i.postimg.cc/sxRfv2Cr/Screenshot-20220331-015026-Weather-Now.jpg "Mannheim Friday")


## Running the tests (Unit tests)

1. Run tests using `./gradlew test` or `./gradlew testDebugUnitTest`.

Unit tests can be run using one of the following:
~~~~
./gradlew test
~~~~

~~~~
./gradlew testDebugUnitTest
~~~~


## Architecture
* Model View ViewModel (MVVM)


## Consumed API Endpoints

```
    api.openweathermap.org/data/2.5/forecast?lat={}&lon={}&appid={}
```


## Dependencies

* [Android](https://www.android.com/) - Operating System.
* [Retrofit](https://square.github.io/retrofit/) - HTTP Requests.
* [Kotlin](https://kotlinlang.org/) - Programing language.
* [Koin](https://insert-koin.io/) - Dependency Injection.
* [Google Play Services Location Library](https://developers.google.com/android/guides/setup) - Location library.
* [Facebook Shimmer](https://github.com/facebook/shimmer-android) - Android Loading Indicator with Shimmer effect.
* [Desugar](https://mvnrepository.com/artifact/com.android.tools/desugar_jdk_libs/1.1.5) - Allow lower API levels to work with new Java libraries.
* [Mockito](https://javadoc.io/doc/org.mockito/mockito-core/2.23.0/org/mockito/Mockito.html) - Library that allows for mock creation, verification and stubbing while writing tests.


## Credits
* [Demystifying the need for desugaring](https://blog.mindorks.com/desugaring-in-android)
* [Awesome Weather Icons](https://erikflowers.github.io/weather-icons/)
* [Unit testing](https://medium.com/swlh/unit-testing-with-kotlin-coroutines-the-android-way-19289838d257)
* [Unit testing](https://medium.com/swlh/unit-testing-with-kotlin-coroutines-the-android-way-19289838d257)
* [Unit testing](https://medium.com/@iamanbansal/testing-viewmodel-livedata-4a62f34e7c26)
* [Unit testing](https://www.geeksforgeeks.org/unit-testing-of-viewmodel-with-kotlin-coroutines-and-livedata-in-android/)