@file:JsModule("google")
@file:JsQualifier("maps")
@file:JsNonModule

package google.maps

import org.w3c.dom.Element
import org.w3c.dom.events.Event
import kotlin.js.Json

@JsName("Map")
external open class JsGoogleMap(element : Element?, options: Json?) {
    fun addListener(event : String, listener : (Event) -> Unit) : Any?
}

@JsName("Marker")
external open class JsMarker(config : Json?) {
    fun set(property : String, value : String) : Unit
    fun setMap(map : JsGoogleMap?) : Unit
}
