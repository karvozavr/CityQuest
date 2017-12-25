package ru.spbau.cityquest.webui.questeditor

import google.maps.KtGoogleMap
import google.maps.LatLng
import google.maps.MapOptions
import kotlin.browser.document

//val optionsG = MapOptions(LatLng(59.9342802, 30.3350986), 12)

fun createMap() {
    val node = document.getElementById("map")
    val options = MapOptions(LatLng(59.9342802, 30.3350986), 12)
    println(JSON.stringify(options.toJson()))
    val map = KtGoogleMap(node, options)
}

fun main(args: Array<String>) {
    println("CityQuest Web UI in Kotlin is at your service!")
    createMap()
}