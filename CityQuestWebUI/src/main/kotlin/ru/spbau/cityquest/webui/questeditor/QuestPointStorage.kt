package ru.spbau.cityquest.webui.questeditor


import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.*
import org.w3c.dom.HTMLLIElement
import kotlin.browser.document


class QuestPointStorage(questEditor: QuestEditor) {
    var nextId : Int = 0
        private set

    fun getNextId() = nextId++

    private val pointsList : MutableList<QuestPoint> = ArrayList()
    private val draggableList : DraggableList = DraggableList()
    private var currentDragPoint : QuestPoint? = null
    private val editor = questEditor

    fun createListItem(point: QuestPoint) : HTMLLIElement {
        var ret : HTMLLIElement? = null
        val linesDiv = document.create.div("qpi-lines")
        val editButton = document.create.button(classes = "qpi-edit-button") {
            onClickFunction = {
                editor.currentEdit = QuestEditor.CurrentEdit(point.id)
                editor.editorState.switchState(QuestEditorStateManager.QuestEditorState.EDIT_QUEST_POINT)
            }
        }
        val removeButton = document.create.button(classes = "qpi-remove-button") {
            onClickFunction = {
                removeQuestPoint(point)
                editor.documentNodes.questPointsList?.removeChild(ret!!)
            }
        }
        val elem = document.create.div("quest-points-item") {
            div("qpi-pic")
            div("qpi-title") {
                +point.title.substring(0, 30)
            }
            div("qpi-desc") {
                +point.desc.substring(0, 50)
            }
            onClickFunction = {
                if (linesDiv.style.visibility == "hidden") {
                    linesDiv.style.visibility = "visible"
                    removeButton.style.visibility = "hidden"
                    editButton.style.visibility = "hidden"
                } else {
                    linesDiv.style.visibility = "hidden"
                    removeButton.style.visibility = "visible"
                    editButton.style.visibility = "visible"
                }
            }
        }
        elem.appendChild(linesDiv)
        elem.appendChild(editButton)
        elem.appendChild(removeButton)
        ret = document.create.li {
            onDragStartFunction = { e ->
                draggableList.handleDragStart(e, ret!!)
                currentDragPoint = point
            }

            onDragOverFunction = { e ->
                draggableList.handleDragOver(e, ret!!)
            }

            onDragLeaveFunction = { e ->
                draggableList.handleDragLeave(e, ret!!)
            }

            onDropFunction = { e ->
                draggableList.handleDrop(e, ret!!)
                handleDrop(point)
            }
        }
        ret.setAttribute("draggable", "true")
        ret.appendChild(elem)
        return ret
    }

    private fun resetLabels() {
        pointsList.forEachIndexed { i, p ->
            if (p is GPSQuestPoint) {
                p.marker!!.set("label", "${i + 1}")
            }
        }
    }

    private fun printList() {
        pointsList.forEach { p ->
            print("${p.id} ")
        }
        println()
    }

    fun handleDrop(point: QuestPoint) {
        if (currentDragPoint!!.id != point.id) {
            val posFrom = pointsList.indexOfFirst { it.id == currentDragPoint!!.id }
            pointsList.removeAt(posFrom)
            val posTo = pointsList.indexOfFirst { it.id == point.id }
            pointsList.add(posTo, currentDragPoint!!)
            resetLabels()
        }
    }

    fun getQuestPointById(id: Int) = pointsList.first { it.id == id }

    fun addPoint(point: QuestPoint) {
        pointsList.add(point)
        editor.documentNodes.willAppear?.style?.visibility = "hidden"
        editor.documentNodes.questPointsList?.appendChild(createListItem(point))
    }

    fun removeQuestPoint(point: QuestPoint) {
        val i = pointsList.indexOfFirst { it.id == point.id }
        if (pointsList[i] is GPSQuestPoint) {
            (pointsList[i] as GPSQuestPoint).marker = null
        }
        pointsList.removeAt(i)
        resetLabels()
    }

    val size : Int
        get() = pointsList.size
}
