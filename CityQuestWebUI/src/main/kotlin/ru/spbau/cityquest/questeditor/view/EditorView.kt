package ru.spbau.cityquest.questeditor.view

import ru.spbau.cityquest.questeditor.stepeditor.StepEditor
import google.maps.LatLng

class EditorView {
    fun getStepList() : DraggableList = TODO("Implement the EditorView DraggableList functions")
    fun openStepEditorWindow(editor : StepEditor) : Nothing = TODO("Implement the EditorView StepEditor functions")
    fun closeStepEditorWindow() : Nothing = TODO("Implement the EditorView StepEditor functions")
    fun attachMapClickListener(listener : (LatLng) -> Unit) : Nothing = TODO("Implement the EditorView map listener functions")
}
