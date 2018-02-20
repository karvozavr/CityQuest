package ru.spbau.cityquest.webui.questeditor

import google.maps.KtMarker
import google.maps.LatLng
import kotlin.js.Json
import kotlin.js.json


abstract class QuestPoint(val id: Int, var title: String, val goal: String, var desc: String) {
    open fun getLatLng() : LatLng? = null

    open fun getKeywords() : String = ""

    abstract fun getType() : String

    fun toJson(idx: Int) : Json {
        return ptToJSON(title, desc, getType(), goal, getKeywords(), getLatLng()?.lat, getLatLng()?.lng)
    }
}

class GPSQuestPoint(id: Int, title: String, goal: String, desc: String, val latLng: LatLng) : QuestPoint(id, title, goal, desc) {
    override fun getType(): String {
        return "geo"
    }

    override fun getLatLng(): LatLng? = latLng

    var marker : KtMarker? = null
        set(value) {
            field?.setMap(null)
            field = value
        }
}

class TextQuestPoint(id: Int, title: String, goal: String, desc: String, val keywords: String) : QuestPoint(id, title, goal, desc) {
    override fun getType(): String {
        return "key"
    }

    override fun getKeywords(): String = keywords
}

class FinalQuestPoint(id: Int, title: String, desc: String) : QuestPoint(id, title, "", desc) {
    override fun getType(): String {
        return "final"
    }
}