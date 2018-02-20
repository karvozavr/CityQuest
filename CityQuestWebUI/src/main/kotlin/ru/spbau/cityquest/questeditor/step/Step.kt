package ru.spbau.cityquest.questeditor.step

import ru.spbau.cityquest.questeditor.editor
import google.maps.LatLng

import kotlin.js.Json

external fun pointToJson(title : String, desc : String, type : String, goal : String, keyword : String, lat : Double?, lng : Double?) : Json

abstract class Step(var stepTitle : String, var stepGoal : String, var stepDesc : String)  {
    companion object {
        var nextId : Int = 0
            get() = field++
            private set
    }
    val id : Int = nextId

    open fun activate() {}
    open fun setIndex(index : Int) {}
    open fun deactivate() {}

    abstract fun getPictureClass() : String
    abstract fun toJson() : Json
}

class GPSStep(title : String, goal : String, desc : String, var position : LatLng) : Step(title, goal, desc) {
    class NoMarkerStoredException : Exception("Attempt to get removed/not created marker")

    interface MarkerStorage {
        fun getMarker() : google.maps.Marker
    }

    class NoMarkerStored : MarkerStorage {
        override fun getMarker() : google.maps.Marker {
            throw NoMarkerStoredException()
        }
    }

    class StoredMarker(val marker : google.maps.Marker) : MarkerStorage {
        override fun getMarker() : google.maps.Marker {
            return marker
        }
    }

    private var markerStorage : MarkerStorage = NoMarkerStored()
    
    override fun activate() {
        markerStorage = StoredMarker(editor.view.addMarker(position))
        super.activate()
    }

    override fun setIndex(index : Int) {
        markerStorage.getMarker().set("label", "${index + 1}")
        super.setIndex(index)
    }

    override fun deactivate() {
        editor.view.removeMarker(markerStorage.getMarker())
        markerStorage = NoMarkerStored()
        super.deactivate()
    }

    override fun getPictureClass() : String {
        return "step-list-gps-pic"
    }

    override fun toJson() : Json {
        return pointToJson(stepTitle, stepDesc, "geo", stepGoal, "", position.lat, position.lng)
    }
}

class QuestionStep(title : String, goal : String, desc : String, var answer : String) : Step(title, goal, desc) {
    override fun getPictureClass() : String {
        return "step-list-question-pic"
    }

    override fun toJson() : Json {
        return pointToJson(stepTitle, stepDesc, "key", stepGoal, answer, null, null)
    }
}

class FinalStepPictureClassException : Exception("Attempt to get picture class from final step")

class FinalStep(title : String, goal : String, desc : String) : Step(title, goal, desc) {
    override fun getPictureClass() : String {
        throw FinalStepPictureClassException()
    }

    override fun toJson() : Json {
        return pointToJson(stepTitle, stepDesc, "final", stepGoal, "", null, null)
    }
}
