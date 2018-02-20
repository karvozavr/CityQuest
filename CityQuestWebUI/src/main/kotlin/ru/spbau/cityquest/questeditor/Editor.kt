package ru.spbau.cityquest.questeditor

import ru.spbau.cityquest.questeditor.view.EditorView
import ru.spbau.cityquest.questeditor.step.StepStorage
import ru.spbau.cityquest.questeditor.stepeditor.*

import kotlin.js.json

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
        val questJSON = JSON.stringify(json(
                "name" to "Untitiled :(",
                "description" to "Undescribed :(",
                "avg_distance" to 1.0,
                "author" to "CityQuest Community",
                "steps" to storage.toJsonList()
        ))
        view.setResultingJson(questJSON)
    }
}
