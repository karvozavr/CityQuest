Note that Google Maps API Key for CityQuestWebUI is not present in the repository.
To get the things working you must add file `earlyloader/src/main/kotlin/ru/spbau/cityquest/webui/earlyloader/GoogleMapsAPIKey.kt`
with the following content:

```kotlin
package ru.spbau.cityquest.webui.questeditor

const val GOOGLE_MAPS_API_KEY : String = "YOUR_API_KEY"
```