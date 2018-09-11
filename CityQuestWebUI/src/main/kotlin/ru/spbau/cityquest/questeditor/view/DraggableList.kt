package ru.spbau.cityquest.questeditor.view

import ru.spbau.cityquest.questeditor.step.Step
import ru.spbau.cityquest.questeditor.editor
import kotlin.browser.document
import org.w3c.dom.*
import org.w3c.dom.events.Event

import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.*

class DraggableList(val ulEl : HTMLUListElement, val emptyListDecoration : HTMLParagraphElement) {
    class UnexpectedDragEventException : Exception("Couldn't handle drag event without drag source")

    interface DragSourceHolder {
        fun getDragSource() : HTMLLIElement
        fun getStepSource() : Step
    }

    class EmptyDragSourceHolder : DragSourceHolder {
        override fun getDragSource() : HTMLLIElement {
            throw UnexpectedDragEventException()
        }
        override fun getStepSource() : Step {
            throw UnexpectedDragEventException()
        }
    }

    class DragSource(val dragSource : HTMLLIElement, val sourceStep : Step) : DragSourceHolder {
        override fun getDragSource() : HTMLLIElement {
            return dragSource
        }
        override fun getStepSource() : Step {
            return sourceStep
        }
    }

    private var dragSource : DragSourceHolder = EmptyDragSourceHolder()

    private fun shrinkedText(text : String, length: Int) : String {
        if (text.length > length) {
            return text.substring(0, length - 3) + "..."
        } else {
            return text
        }
    }
    
    private fun createListItem(step : Step) : HTMLLIElement {
        val linesDiv = document.create.div("step-list-lines")
        val commonDiv = document.create.div("step-list-common") {
            div("step-list-pic " + step.getPictureClass())
            div("step-list-title") {
                +shrinkedText(step.stepTitle, 25)
            }
            div("step-list-desc") {
                +shrinkedText(step.stepDesc, 35)
            }
        }
        commonDiv.appendChild(linesDiv)
        var ret = document.create.li {}
        ret = document.create.li {
            onDragStartFunction = { _ ->
                dragSource = DragSource(ret, step)
            }
            onDragOverFunction = { e ->
                e.preventDefault()
            }
            onDragLeaveFunction = { }
            onDropFunction = { e ->
                e.stopPropagation()
                val whereDroppedIndex = ulEl.children.asList().indexOfFirst { listNode -> ret.isSameNode(listNode) }
                val dragSourceIndex = ulEl.children.asList().indexOfFirst { listNode -> dragSource.getDragSource().isSameNode(listNode) }
                ulEl.removeChild(dragSource.getDragSource())
                if (dragSourceIndex > whereDroppedIndex) {
                    ulEl.insertBefore(dragSource.getDragSource(), ret)
                } else {
                    ulEl.insertBefore(dragSource.getDragSource(), ulEl.children[whereDroppedIndex])
                }
                editor.storage.moveStep(dragSource.getStepSource(), whereDroppedIndex)
                dragSource = EmptyDragSourceHolder()
            }
        }
        ret.setAttribute("draggable", "true")
        ret.appendChild(commonDiv)
        return ret
    }
    
    fun addStep(step : Step) {
        emptyListDecoration.style.visibility = "hidden"
        ulEl.appendChild(createListItem(step))
    }
}
