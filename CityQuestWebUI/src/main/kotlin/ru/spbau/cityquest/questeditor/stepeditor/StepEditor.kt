package ru.spbau.cityquest.questeditor.stepeditor

import org.w3c.dom.HTMLDivElement

abstract class StepEditor(ofWindow : HTMLDivElement) {
    val window : HTMLDivElement = ofWindow

    abstract fun open()
    abstract fun save()
    abstract fun close()
}

class GPSStepEditor(ofWindow : HTMLDivElement) : StepEditor(ofWindow) {
    override fun open() = TODO("Implement the GPSStepEditor")
    override fun save() = TODO("Implement the GPSStepEditor")
    override fun close() = TODO("Implement the GPSStepEditor")
}

class QuestionStepEditor(ofWindow : HTMLDivElement) : StepEditor(ofWindow) {
    override fun open() = TODO("Implement the QuestionStepEditor")
    override fun save() = TODO("Implement the QuestionStepEditor")
    override fun close() = TODO("Implement the QuestionStepEditor")
}

class FinalStepEditor(ofWindow : HTMLDivElement) : StepEditor(ofWindow) {
    override fun open() = TODO("Implement the FinalStepEditor")
    override fun save() = TODO("Implement the FinalStepEditor")
    override fun close() = TODO("Implement the FinalStepEditor")
}
