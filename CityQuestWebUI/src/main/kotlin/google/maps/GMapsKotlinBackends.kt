@file:JsModule("google")
@file:JsQualifier("maps")
@file:JsNonModule

package google.maps

import org.w3c.dom.Element
import kotlin.js.Json

@JsName("Map")
external open class JsGoogleMap(element: Element?, options: Json?)
