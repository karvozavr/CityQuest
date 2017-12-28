package ru.spbau.cityquest.webui.questeditor

import google.maps.LatLng
import google.maps.MapOptions
import kotlin.js.Json

var editor : QuestEditor? = null

@JsName("getEditor")
fun getEditor() : QuestEditor {
    return editor!!
}

external fun descOnChange(value: String, elem: Any)
external fun ptToJSON(title: String, desc: String, type: String, goal: String, keyword: String, lat: Double?, lng: Double?) : Json

fun main(args: Array<String>) {
    println("CityQuest Web UI in Kotlin is at your service!")

    val initPoint = LatLng(59.9342802, 30.3350986)
    val initZoom : Byte = 12
    val mapOptions = MapOptions(initPoint, initZoom)
    mapOptions.clickableIcons = false

    editor = QuestEditor(mapOptions)
}