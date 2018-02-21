package ru.spbau.cityquest.questeditor.view

import ru.spbau.cityquest.questeditor.stepeditor.*
import ru.spbau.cityquest.questeditor.editor
import google.maps.LatLng
import kotlin.browser.document
import org.w3c.dom.*
import org.w3c.dom.events.Event

class EditorView {
    private companion object {
        class ElementNotFoundException(element : String) : Exception("FATAL: ${element} not found in the HTML DOM")

        val map = (document.getElementById("map") as HTMLDivElement?) ?: throw ElementNotFoundException("map")
        val gpsEditorWindow = (document.getElementById("gps-step-editor") as HTMLDivElement?) ?: throw ElementNotFoundException("gps-step-editor")
        val gpsStepTitleEdit = (document.getElementById("gps-step-title-edit") as HTMLInputElement?) ?: throw ElementNotFoundException("gps-step-title-edit")
        val gpsStepGoalEdit = (document.getElementById("gps-step-goal-edit") as HTMLInputElement?) ?: throw ElementNotFoundException("gps-step-goal-edit")
        val gpsStepDescEdit = (document.getElementById("gps-step-desc-edit") as HTMLTextAreaElement?) ?: throw ElementNotFoundException("gps-step-desc-edit")
        val gpsStepImageEdit = (document.getElementById("gps-step-image-edit") as HTMLInputElement?) ?: throw ElementNotFoundException("gps-step-image-edit")
        val questionEditorWindow = (document.getElementById("question-step-editor") as HTMLDivElement?) ?: throw ElementNotFoundException("question-step-editor")
        val questionStepTitleEdit = (document.getElementById("question-step-title-edit") as HTMLInputElement?) ?: throw ElementNotFoundException("question-step-title-edit")
        val questionStepGoalEdit = (document.getElementById("question-step-goal-edit") as HTMLInputElement?) ?: throw ElementNotFoundException("question-step-goal-edit")
        val questionStepAnswerEdit = (document.getElementById("question-step-answer-edit") as HTMLInputElement?) ?: throw ElementNotFoundException("question-step-answer-edit")
        val questionStepDescEdit = (document.getElementById("question-step-desc-edit") as HTMLTextAreaElement?) ?: throw ElementNotFoundException("question-step-desc-edit")
        val questionStepImageEdit = (document.getElementById("question-step-image-edit") as HTMLInputElement?) ?: throw ElementNotFoundException("question-step-image-edit")
        val finalStepEditorWindow = (document.getElementById("final-step-editor") as HTMLDivElement?) ?: throw ElementNotFoundException("final-step-editor")
        val finalStepTitleEdit = (document.getElementById("final-step-title-edit") as HTMLInputElement?) ?: throw ElementNotFoundException("final-step-title-edit")
        val finalStepGoalEdit = (document.getElementById("final-step-goal-edit") as HTMLInputElement?) ?: throw ElementNotFoundException("final-step-goal-edit")
        val finalStepDescEdit = (document.getElementById("final-step-desc-edit") as HTMLTextAreaElement?) ?: throw ElementNotFoundException("final-step-desc-edit")
        val finalStepImageEdit = (document.getElementById("final-step-image-edit") as HTMLInputElement?) ?: throw ElementNotFoundException("final-step-image-edit")
        val stepListUl = (document.getElementById("step-list-ul") as HTMLUListElement?) ?: throw ElementNotFoundException("step-list-ul")
        val questTitleEdit = (document.getElementById("quest-title-edit") as HTMLInputElement?) ?: throw ElementNotFoundException("quest-title-edit")
        val willAppearParagraph = (document.getElementById("will-appear") as HTMLParagraphElement?) ?: throw ElementNotFoundException("will-appear")
        val jsonInput = (document.getElementById("json") as HTMLInputElement?) ?: throw ElementNotFoundException("json")

        val mapOptions = google.maps.MapOptions(LatLng(59.9342802, 30.3350986), 12)
        init {
            mapOptions.clickableIcons = false
        }
        val googleMap = google.maps.Map(map, mapOptions)
    }

    fun createGPSStepEditor() : GPSStepEditor {
        return GPSStepEditor(gpsEditorWindow, gpsStepTitleEdit, gpsStepGoalEdit, gpsStepDescEdit, gpsStepImageEdit)
    }

    fun createQuestionStepEditor() : QuestionStepEditor {
        return QuestionStepEditor(questionEditorWindow, questionStepTitleEdit, gpsStepGoalEdit, questionStepAnswerEdit, questionStepDescEdit, questionStepImageEdit)
    }

    fun createFinalStepEditor() : FinalStepEditor {
        return FinalStepEditor(finalStepEditorWindow, finalStepTitleEdit, finalStepGoalEdit, finalStepDescEdit, finalStepImageEdit)
    }

    fun setResultingJson(res : String) {
        jsonInput.value = res
    }
    
    val draggableList = DraggableList(stepListUl, willAppearParagraph)
    
    fun openStepEditorWindow(stepEditor : StepEditor) {
        stepEditor.window.style.visibility = "visible"
    }
    
    fun closeStepEditorWindow(stepEditor : StepEditor) {
        stepEditor.window.style.visibility = "hidden"
    }

    fun getQuestTitle() : String {
        return questTitleEdit.value
    }
    
    fun addMarker(position : LatLng) : google.maps.Marker {
        return google.maps.Marker(position, googleMap)
    }

    fun removeMarker(marker : google.maps.Marker) {
        marker.setMap(null)
    }
    
    /* Event listener stuff */

    /**
     *  Google Maps API returns some internal value as the result of addListener call.
     *  That value should be passed to the API in case we want to detach the event.
     *  As long as even the type of the value is undocumented, we would store it as
     *  a nullable Any type value. We cannot even guarantee it is not null, so there 
     *  is some architecture realized instead of storing null for no value.
     */

    class NoListenerAttachedException(event : String) : Exception("Attempt to get listener for an unhandled event " + event)
    
    interface EventListenerStorage {
        fun getListener() : Any?
    }

    class NoListenerStored(val event : String) : EventListenerStorage {
        override fun getListener() : Any? {
            throw NoListenerAttachedException(event)
        }
    }

    class StoredListener(val listener : Any?) : EventListenerStorage {
        override fun getListener() : Any? {
            return listener
        }
    }

    private var mapOnClickListenerStorage : EventListenerStorage = NoListenerStored("click")
    
    fun attachMapClickListener(listener : (Event) -> Unit) {
        val gmListener = googleMap.addListener("click", listener)
        mapOnClickListenerStorage = StoredListener(gmListener)
    }

    fun detachMapClickListener() {
        google.maps.event.removeListener(mapOnClickListenerStorage.getListener())
        mapOnClickListenerStorage = NoListenerStored("click")
    }
}
