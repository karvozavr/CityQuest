package ru.spbau.cityquest.webui.questeditor


import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.*
import org.w3c.dom.HTMLLIElement
import org.w3c.dom.Node
import kotlin.browser.document

class QuestPointStorage {
    var nextId : Int = 0
        get() = field++
        private set

    private val pointsList : MutableList<QuestPoint> = ArrayList<QuestPoint>()
    private val draggableList : DraggableList = DraggableList()
    private var currentDragPoint : Node? = null

    fun createListItem(point: QuestPoint, editor: QuestEditor) : HTMLLIElement {
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
        val elem = document.create.div("quests-point-item") {
            div("qpi-pic")
            div("qpi-title") {
                +point.title
            }
            div("qpi-desc") {
                +point.desc
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
            draggable = Draggable.htmlTrue

            onDragStartFunction = { e ->
                draggableList.handleDragStart(e, ret!!)
                currentDragPoint = ret
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
        ret.appendChild(elem)
        return ret
    }

    fun removeQuestPoint(point: QuestPoint) : Unit = TODO("Implement the quest point storage")

    fun handleDrop(point: QuestPoint) : Unit = TODO("Implement the quest point storage")

    fun addPoint(point: QuestPoint) {
        pointsList.add(point)
    }

    val size : Int
        get() = pointsList.size
}