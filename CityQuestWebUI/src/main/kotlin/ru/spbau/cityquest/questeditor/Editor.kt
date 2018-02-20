package ru.spbau.cityquest.questeditor

import ru.spbau.cityquest.questeditor.view.EditorView
import ru.spbau.cityquest.questeditor.step.StepStorage
import ru.spbau.cityquest.questeditor.stepeditor.*

@JsName("editor")
object editor {
    val view : EditorView = EditorView()

    val storage : StepStorage = StepStorage()

    @JsName("gpsEditor")
    val gpsEditor : GPSStepEditor = view.createGPSStepEditor()
    val questionEditor : QuestionStepEditor = view.createQuestionStepEditor()
    /*
    val finalStepEditor : FinalStepEditor = TODO("Implement the Editor")
    */

    fun saveQuest() : Nothing = TODO("Implement the Editor")
}
