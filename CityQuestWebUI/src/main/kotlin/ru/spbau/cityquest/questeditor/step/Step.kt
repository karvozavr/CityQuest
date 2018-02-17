package ru.spbau.cityquest.questeditor.step

import google.maps.LatLng

abstract class Step(val stepTitle : String, val stepDesc : String)  {
    companion object {
        var nextId : Int = 0
            get() = field++
            private set
    }
    val id : Int = nextId
}

class GPSStep(title : String, desc : String, val position : LatLng) : Step(title, desc)

class QuestionStep(title : String, desc : String, val ansert : String) : Step(title, desc)

class FinalStep(title : String, desc : String) : Step(title, desc)
