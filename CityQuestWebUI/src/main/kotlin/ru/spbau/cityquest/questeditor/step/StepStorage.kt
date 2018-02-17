package ru.spbau.cityquest.questeditor.step

import kotlin.js.*

class StepStorage {
    private val storage : MutableList<Step> = ArrayList<Step>()

    fun addStep(step : Step) = storage.add(step)
    
    fun removeStep(step : Step) {
        val at = storage.indexOfFirst { it.id == step.id }
        storage.removeAt(at)
    }
    
    fun moveStep(step : Step, after : Step) {
        removeStep(step)
        val toIndex = storage.indexOfFirst { it.id == after.id }
        storage.add(toIndex, step)
    }

    fun toJson() : Json = TODO("Implement the step storage")
}
