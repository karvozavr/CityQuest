package ru.spbau.cityquest.questeditor.step

import kotlin.js.*

class StepStorage {
    private val storage : MutableList<Step> = ArrayList<Step>()

    fun addStep(step : Step) {
        storage.add(step)
        step.activate()
        step.setIndex(storage.size - 1)
    }
    
    fun removeStep(step : Step) {
        val at = storage.indexOfFirst { it.id == step.id }
        storage.removeAt(at)
        step.deactivate()
        updateIndices()
    }
    
    fun moveStep(step : Step, toIndex : Int) {
        removeStep(step)
        storage.add(toIndex, step)
        step.activate()
        updateIndices()
    }

    fun toJsonList() : List<Json> {
        return storage.map { it.toJson() }
    }

    fun updateIndices() {
        for (i in 0 until storage.size) {
            storage[i].setIndex(i)
        }
    }
}
