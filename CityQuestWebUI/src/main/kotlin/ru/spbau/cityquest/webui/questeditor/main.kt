package ru.spbau.cityquest.webui.questeditor

import google.maps.KtGoogleMap
import google.maps.LatLng
import google.maps.MapOptions
import kotlin.browser.document

fun performChecks() {
    if (document.head == null)
        println("Warning: document.head is null, the QuestEditor may not work properly.")
}

@JsName("initMapCallback")
fun initMapCallback() {
    val node = document.getElementById("map")
    val options = MapOptions(LatLng(59.9342802, 30.3350986), 12)
    val map = KtGoogleMap(node, options)
}

fun main(args: Array<String>) {
    println("CityQuest Web UI in Kotlin is at your service!")

    performChecks()

    createGoogleMapsLoader()
    println("Google Maps loader has been added to the HTML DOM.")
}