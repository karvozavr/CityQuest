package ru.spbau.cityquest.webui.questeditor

class QuestPointStorage {
    var nextId : Int = 0
        get() = field++
        private set

    private val pointsList : MutableList<QuestPoint> = ArrayList<QuestPoint>()

    fun addPoint(point: QuestPoint) {
        pointsList.add(point)
    }

    val size : Int
        get() = pointsList.size
}