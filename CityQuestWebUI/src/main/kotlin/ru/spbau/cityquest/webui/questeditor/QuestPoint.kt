package ru.spbau.cityquest.webui.questeditor

import google.maps.LatLng

open class QuestPoint(val id: Int, val title: String, val desc: String)

class GPSQuestPoint(id: Int, title: String, desc: String, val latLng: LatLng) : QuestPoint(id, title, desc) {
    fun addMarker() : Unit = TODO("Implement the QuestEditor interface")
    fun removeMarker() : Unit = TODO("Implement the QuestEditor interface")
}


