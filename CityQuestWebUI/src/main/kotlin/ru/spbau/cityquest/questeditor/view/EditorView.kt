package ru.spbau.cityquest.questeditor.view

import ru.spbau.cityquest.questeditor.stepeditor.StepEditor
import google.maps.LatLng
import kotlin.browser.document
import org.w3c.dom.*

class EditorView {
    private companion object {
        class ElementNotFoundException(element : String) : Exception("FATAL: ${element} not found in the HTML DOM")

        val map = (document.getElementById("map") as HTMLDivElement?) ?: throw ElementNotFoundException("map")

        val mapOptions = google.maps.MapOptions(LatLng(59.9342802, 30.3350986), 12)
        init {
            mapOptions.clickableIcons = false
        }
        val googleMap = google.maps.Map(map, mapOptions)
    }

    /*
    fun getStepList() : DraggableList = TODO("Implement the EditorView DraggableList functions")
    fun openStepEditorWindow(editor : StepEditor) : Nothing = TODO("Implement the EditorView StepEditor functions")
    fun closeStepEditorWindow() : Nothing = TODO("Implement the EditorView StepEditor functions")
    fun attachMapClickListener(listener : (LatLng) -> Unit) : Nothing = TODO("Implement the EditorView map listener functions")
    */
}
