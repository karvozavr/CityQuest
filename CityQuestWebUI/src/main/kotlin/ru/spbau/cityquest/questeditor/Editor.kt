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
    @JsName("questionEditor")
    val questionEditor : QuestionStepEditor = view.createQuestionStepEditor()
    @JsName("finalStepEditor")
    val finalStepEditor : FinalStepEditor = view.createFinalStepEditor()

    @JsName("submitQuest")
    fun submitQuest() {
        
    }
}
