package ru.spbau.cityquest.questeditor.step

import ru.spbau.cityquest.questeditor.editor
import google.maps.LatLng

abstract class Step(var stepTitle : String, var stepDesc : String)  {
    companion object {
        var nextId : Int = 0
            get() = field++
            private set
    }
    val id : Int = nextId

    open fun activate() {}
    open fun setIndex(index : Int) {}
    open fun deactivate() {}
}

class GPSStep(title : String, desc : String, var position : LatLng) : Step(title, desc) {
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
}

class QuestionStep(title : String, desc : String, var answer : String) : Step(title, desc)

class FinalStep(title : String, desc : String) : Step(title, desc)
