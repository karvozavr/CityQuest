package ru.spbau.cityquest.webui.questeditor

import google.maps.KtMarker
import google.maps.LatLng
import kotlin.js.Json
import kotlin.js.json

abstract class QuestPoint(val id: Int, var title: String, var desc: String) {
    open fun getLatLng() : LatLng? = null

    open fun getKeywords() : String = ""

    open fun getGoal() : String = "???"

    abstract fun getType() : String

    fun toJson(idx: Int) : Json {
        return json(
                "title" to title,
                "description" to desc,
                "goal" to getGoal(),
                "step_type" to getType(),
                "latitude" to getLatLng()?.lat,
                "longitude" to getLatLng()?.lng,
                "keywords" to getKeywords(),
                "step_number" to idx + 1
        )
    }
}

class GPSQuestPoint(id: Int, title: String, desc: String, val latLng: LatLng) : QuestPoint(id, title, desc) {
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

class TextQuestPoint(id: Int, title: String, desc: String, val keywords: String) : QuestPoint(id, title, desc) {
    override fun getType(): String {
        return "key"
    }

    override fun getKeywords(): String = keywords
}