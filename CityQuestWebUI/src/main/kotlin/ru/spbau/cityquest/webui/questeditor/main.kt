package ru.spbau.cityquest.webui.questeditor

import google.maps.LatLng
import google.maps.MapOptions

fun main(args: Array<String>) {
    println("CityQuest Web UI in Kotlin is at your service!")

    val initPoint = LatLng(59.9342802, 30.3350986)
    val initZoom : Byte = 12
    val mapOptions = MapOptions(initPoint, initZoom)
    mapOptions.clickableIcons = false

    val editor = QuestEditor(mapOptions)
}