package google.maps

import org.w3c.dom.Element
import kotlin.js.json

class LatLng(val lat: Double, val lng: Double) {
    fun toJson() = json("lat" to lat, "lng" to lng)
}

class MapOptions(val center: LatLng, val zoom: Byte) {
    fun toJson() = json("center" to center.toJson(), "zoom" to zoom)
}

class KtGoogleMap(element: Element?, options: MapOptions) : JsGoogleMap(element, options.toJson())