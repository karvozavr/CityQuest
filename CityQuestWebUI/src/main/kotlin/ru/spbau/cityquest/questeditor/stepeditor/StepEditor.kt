package ru.spbau.cityquest.questeditor.stepeditor

import ru.spbau.cityquest.questeditor.step.*
import ru.spbau.cityquest.questeditor.editor
import google.maps.LatLng
import org.w3c.dom.*

abstract class StepEditor(ofWindow : HTMLDivElement) {
    val window : HTMLDivElement = ofWindow

    @JsName("open")
    abstract fun open()
    abstract fun save()
    @JsName("close")
    abstract fun close()
    @JsName("cancel")
    abstract fun cancel()
}

class NoneStepEdited : Exception("Attempt to get an edit from closed editor.") 

interface CurrentStepStorage<out T : Step> {
    fun getCurrentStep() : T
    fun saveCurrentStep()
}

class EmptyStepStorage<out T : Step> : CurrentStepStorage<T> {
    override fun getCurrentStep() : T {
        throw NoneStepEdited()
    }
    override fun saveCurrentStep() {
        throw NoneStepEdited()
    }
}

class NewStepStorage<out T : Step>(val currentStep : T) : CurrentStepStorage<T> {
    override fun getCurrentStep() : T {
        return currentStep
    }
    override fun saveCurrentStep() {
        editor.storage.addStep(currentStep)
        editor.view.draggableList.addStep(currentStep)
    }
}

class GPSStepEditor(ofWindow : HTMLDivElement,
                    private val titleEdit : HTMLInputElement,
                    private val descEdit : HTMLTextAreaElement) : StepEditor(ofWindow) {
    private var currentStep : CurrentStepStorage<GPSStep> = EmptyStepStorage<GPSStep>()

    override fun open() {
        titleEdit.value = ""
        descEdit.value = ""
        currentStep = NewStepStorage<GPSStep>(GPSStep("", "", LatLng(0.0, 0.0)))
        editor.view.openStepEditorWindow(this)
    }
    
    override fun save() {
        currentStep.getCurrentStep().stepTitle = titleEdit.value
        currentStep.getCurrentStep().stepDesc = descEdit.value
        currentStep.saveCurrentStep()
        currentStep = EmptyStepStorage<GPSStep>()
    }

    fun setMarker(where : LatLng) {
        currentStep.getCurrentStep().position = where
    }

    @JsName("startMarkerPlacement")
    fun startMarkerPlacement() {
        editor.view.attachMapClickListener { event ->
            setMarker(js("event.latLng"))
            save()
            editor.view.detachMapClickListener()
        }
    }

    override fun close() {
        editor.view.closeStepEditorWindow(this)
    }

    override fun cancel() {
        currentStep = EmptyStepStorage<GPSStep>()
    }
}

class QuestionStepEditor(ofWindow : HTMLDivElement,
                         val titleEdit : HTMLInputElement,
                         val answerEdit : HTMLInputElement,
                         val descEdit : HTMLTextAreaElement) : StepEditor(ofWindow) {
    private var currentStep : CurrentStepStorage<QuestionStep> = EmptyStepStorage<QuestionStep>()

    override fun open() {
        titleEdit.value = ""
        descEdit.value = ""
        currentStep = NewStepStorage<QuestionStep>(QuestionStep("", "", ""))
        editor.view.openStepEditorWindow(this)
    }
    
    override fun save() {
        currentStep.getCurrentStep().stepTitle = titleEdit.value
        currentStep.getCurrentStep().stepDesc = descEdit.value
        currentStep.getCurrentStep().answer = answerEdit.value
        currentStep.saveCurrentStep()
        currentStep = EmptyStepStorage<QuestionStep>()
    }
    
    override fun close() {
        editor.view.closeStepEditorWindow(this)
    }
    
    override fun cancel() {
        currentStep = EmptyStepStorage<QuestionStep>()
    }
}

class FinalStepEditor(ofWindow : HTMLDivElement,
                      val titleEdit : HTMLInputElement,
                      val descEdit : HTMLTextAreaElement) : StepEditor(ofWindow) {
    private var currentStep : CurrentStepStorage<FinalStep> = EmptyStepStorage<FinalStep>()
                          
    override fun open() {
        currentStep = NewStepStorage<FinalStep>(FinalStep("", ""))
        editor.view.openStepEditorWindow(this)
    }
    
    override fun save() {
        currentStep.getCurrentStep().stepTitle = titleEdit.value
        currentStep.getCurrentStep().stepDesc = descEdit.value
        editor.storage.addStep(currentStep.getCurrentStep())
        currentStep = EmptyStepStorage<FinalStep>()
    }
    
    override fun close() {
        editor.view.closeStepEditorWindow(this)
    }
    
    override fun cancel() {
        currentStep = EmptyStepStorage<FinalStep>()
    }
}
