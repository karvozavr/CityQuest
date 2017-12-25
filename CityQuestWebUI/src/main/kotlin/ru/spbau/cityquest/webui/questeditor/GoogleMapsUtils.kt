package ru.spbau.cityquest.webui.questeditor

import kotlin.browser.document

//data class LatLng(val lat: Double, val lng: Double)

fun createGoogleMapsLoader() {
    val loaderScript = document.createElement("script")
    loaderScript.setAttribute("async", "")
    loaderScript.setAttribute("defer", "")
    loaderScript.setAttribute("src",
            "https://maps.googleapis.com/maps/api/js?key=$GOOGLE_MAPS_API_KEY&callback=initMap")
    document.head?.appendChild(loaderScript)
}