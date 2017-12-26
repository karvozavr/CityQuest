package ru.spbau.cityquest.webui.questeditor

import google.maps.KtMarker
import google.maps.LatLng

open class QuestPoint(val id: Int, val title: String, val desc: String)

class GPSQuestPoint(id: Int, title: String, desc: String, val latLng: LatLng) : QuestPoint(id, title, desc) {
    var marker : KtMarker? = null
        set(value) {
            field?.setMap(null)
            field = value
        }
}


