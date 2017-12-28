package ru.spbau.cityquest.webui.questeditor

import google.maps.KtGoogleMap
import google.maps.KtMarker
import google.maps.MapOptions
import org.w3c.dom.Attr
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLTextAreaElement
import org.w3c.dom.events.Event
import kotlin.browser.document
import kotlin.js.json

external fun alert(message: String)

const val defaultTitle : String = "Lorem ipsum"
const val defaultDesc : String = "All embrace me\n" +
        "It's my time to rule at last\n" +
        "Fifteen years have I been waiting\n" +
        "To sit upon my throne\n" +
        "No allegiance\n" +
        "I will swear no oath\n" +
        "Crowned by god not by the church\n" +
        "As my power is divine\n" +
        "They thought I was too young to rule the land\n" +
        "Just as they failed to understand\n" +
        "Born to rule\n" +
        "My time has come\n" +
        "I was chosen by heaven\n" +
        "Say my name when you pray\n" +
        "To the skies\n" +
        "See Carolus rise\n" +
        "With the lord my protector\n" +
        "Make them bow to my will\n" +
        "To the skies\n" +
        "See Carolus rise\n"
const val defaultGoal : String = "Poltava"
const val defaultAns : String = "42"


class QuestEditor(mapOptions: MapOptions) {
    class DocumentNodes {
        val map = document.getElementById("map") as HTMLElement?
        val editGPSPointTitleEditInput = document.getElementById("edit-gps-point-title-edit-input") as HTMLInputElement?
        val editGPSPointGoalEditInput = document.getElementById("edit-gps-point-goal-edit-input") as HTMLInputElement?
        val editGPSPointDescEditInput = document.getElementById("edit-gps-point-desc-edit-input")  as HTMLTextAreaElement?
        val editTextPointTitleEditInput = document.getElementById("edit-text-point-title-edit-input") as HTMLInputElement?
        val editTextPointGoalEditInput = document.getElementById("edit-text-point-goal-edit-input") as HTMLInputElement?
        val editTextPointDescEditInput = document.getElementById("edit-text-point-desc-edit-input")  as HTMLTextAreaElement?
        val editTextPointAnsEditInput = document.getElementById("edit-text-point-ans-edit-input")  as HTMLInputElement?
        val editFinalPointTitleEditInput = document.getElementById("edit-final-point-title-edit-input") as HTMLInputElement?
        val editFinalPointDescEditInput = document.getElementById("edit-final-point-desc-edit-input")  as HTMLTextAreaElement?
        val saveChanges = document.getElementById("save-changes") as HTMLElement?
        val willAppear = document.getElementById("will-appear") as HTMLElement?
        val stateViewer = document.getElementById("state") as HTMLElement?
        val editGPSPoint = document.getElementById("edit-gps-point") as HTMLElement?
        val editTextPoint = document.getElementById("edit-text-point") as HTMLElement?
        val editFinalPoint = document.getElementById("edit-final-point") as HTMLElement?
        val questPointsList = document.getElementById("quest-points-list") as HTMLElement?
        val jsonInput = document.getElementById("json") as HTMLInputElement?
    }

    val documentNodes : DocumentNodes = DocumentNodes()

    data class CurrentEdit(val editIndex: Int) {
        fun isNothing() : Boolean = editIndex == -2
        fun isNew() : Boolean = editIndex == -1
    }

    val editNothing : CurrentEdit = CurrentEdit(-2)
    val editNew : CurrentEdit = CurrentEdit(-1)

    val map : KtGoogleMap = KtGoogleMap(documentNodes.map, mapOptions)

    fun onClickListener(event : Event) {
        val questPoint = GPSQuestPoint(questPoints.getNextId(),
                getCurrentEditTitle(),
                getCurrentEditGoal(),
                getCurrentEditDesc(),
                js("event.latLng"))
        questPoint.marker = KtMarker(questPoint.latLng, map)
        questPoint.marker?.set("label", "${questPoints.size + 1}")
        questPoints.addPoint(questPoint)
        editorState.switchState(QuestEditorStateManager.QuestEditorState.VIEW)
    }

    var mapOnClickListener : Any? = null

    val questPoints = QuestPointStorage(this)

    var editorState : QuestEditorStateManager = QuestEditorStateManager(this)

    init {
        editorState.switchState(QuestEditorStateManager.QuestEditorState.VIEW)
    }

    private fun createValElement(value: String) : Attr {
        val svalue = document.createAttribute("value")
        svalue.value = value
        return svalue
    }

    var currentEdit : CurrentEdit = editNothing
        set(value) {
            field = value
            if (value.isNothing()) {
                documentNodes.editGPSPointTitleEditInput?.innerHTML = ""
                documentNodes.editGPSPointDescEditInput?.innerHTML = ""
            } else if (value.isNew()) {
                val titleVal = defaultTitle + " ${questPoints.nextId + 1}"
                documentNodes.editGPSPointTitleEditInput!!.attributes.setNamedItem(createValElement(titleVal))
                documentNodes.editTextPointTitleEditInput!!.attributes.setNamedItem(createValElement(titleVal))
                println(titleVal)
                documentNodes.editFinalPointTitleEditInput!!.attributes.setNamedItem(createValElement(titleVal))
                documentNodes.editGPSPointGoalEditInput!!.attributes.setNamedItem(createValElement(defaultGoal))
                documentNodes.editTextPointAnsEditInput!!.attributes.setNamedItem(createValElement(defaultAns))
                documentNodes.editTextPointGoalEditInput!!.attributes.setNamedItem(createValElement(defaultGoal))
                descOnChange(defaultDesc, documentNodes.editGPSPointDescEditInput!!)
                descOnChange(defaultDesc, documentNodes.editTextPointDescEditInput!!)
                descOnChange(defaultDesc, documentNodes.editFinalPointDescEditInput!!)
                documentNodes.saveChanges?.style?.visibility = "hidden"
            } else {
                documentNodes.saveChanges?.style?.visibility = "visible"
                val point = questPoints.getQuestPointById(value.editIndex)
                val titleVal = document.createAttribute("value")
                titleVal.value = point.title
                documentNodes.editGPSPointTitleEditInput?.attributes?.setNamedItem(titleVal)
                documentNodes.editGPSPointDescEditInput?.innerHTML = point.desc
            }
        }

    fun getCurrentEditTitle() : String {
        return if (editorState.currentState == QuestEditorStateManager.QuestEditorState.EDIT_FINAL_QUEST_POINT)
            documentNodes.editFinalPointTitleEditInput!!.value
        else if (editorState.currentState == QuestEditorStateManager.QuestEditorState.EDIT_GPS_QUEST_POINT
                       || editorState.currentState == QuestEditorStateManager.QuestEditorState.PLACE_MARKER)
            documentNodes.editGPSPointTitleEditInput!!.value
        else
            documentNodes.editTextPointTitleEditInput!!.value
    }

    fun getCurrentEditGoal() : String {
        return if (editorState.currentState == QuestEditorStateManager.QuestEditorState.EDIT_GPS_QUEST_POINT
                || editorState.currentState == QuestEditorStateManager.QuestEditorState.PLACE_MARKER)
            documentNodes.editGPSPointGoalEditInput!!.value
        else
            documentNodes.editTextPointGoalEditInput!!.value
    }

    fun getCurrentEditDesc() : String {
        return if (editorState.currentState == QuestEditorStateManager.QuestEditorState.EDIT_FINAL_QUEST_POINT)
            documentNodes.editFinalPointDescEditInput!!.innerHTML
        else if (editorState.currentState == QuestEditorStateManager.QuestEditorState.EDIT_GPS_QUEST_POINT
                || editorState.currentState == QuestEditorStateManager.QuestEditorState.PLACE_MARKER) {
            documentNodes.editGPSPointDescEditInput!!.innerHTML
        } else
            documentNodes.editTextPointDescEditInput!!.innerHTML
    }

    fun getCurrentEditAns() : String = documentNodes.editTextPointAnsEditInput!!.value

    @JsName("newQuestPoint")
    fun newQuestPoint() {
        currentEdit = editNew
        editorState.switchState(QuestEditorStateManager.QuestEditorState.EDIT_GPS_QUEST_POINT)
    }

    @JsName("newTextPoint")
    fun newTextPoint() {
        currentEdit = editNew
        editorState.switchState(QuestEditorStateManager.QuestEditorState.EDIT_TEXT_QUEST_POINT)
    }

    @JsName("cancelNewPoint")
    fun cancelNewPoint() {
        editorState.switchState(QuestEditorStateManager.QuestEditorState.VIEW)
    }

    @JsName("placeMarker")
    fun placeMarker() {
        editorState.switchState(QuestEditorStateManager.QuestEditorState.PLACE_MARKER)
    }

    @JsName("saveChanges")
    fun saveChanges() {
        editorState.switchState(QuestEditorStateManager.QuestEditorState.VIEW)
    }

    @JsName("submitChanges")
    fun submitChanges() {
        questPoints.addPoint(FinalQuestPoint(
                questPoints.getNextId(),
                getCurrentEditTitle(),
                getCurrentEditDesc()
        ))
        documentNodes.jsonInput!!.value = getJsonDesc()
    }

    @JsName("saveTextChanges")
    fun saveTextChanges() {
        questPoints.addPoint(TextQuestPoint(
                questPoints.getNextId(),
                getCurrentEditTitle(),
                getCurrentEditGoal(),
                getCurrentEditDesc(),
                getCurrentEditAns()
        ))
        editorState.switchState(QuestEditorStateManager.QuestEditorState.VIEW)
    }

    @JsName("finalPoint")
    fun finalPoint() {
        currentEdit = editNew
        editorState.switchState(QuestEditorStateManager.QuestEditorState.EDIT_FINAL_QUEST_POINT)
    }

    private fun getJsonDesc() : String {
        val questPointsJSONS = questPoints.getPointsJSONs()
        return JSON.stringify(json(
                "name" to "Untitled :(",
                "description" to "Undescribed :(",
                "avg_distance" to 1.0,
                "author" to "CityQuest Community",
                "steps" to questPointsJSONS
        ))
    }
}