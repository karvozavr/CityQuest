package ru.spbau.cityquest.webui.questeditor

import org.w3c.dom.Node
import org.w3c.dom.events.Event

class DraggableList {
    private var dragSource : Node? = null

    fun handleDragStart(e: Event, elem: Node) {
        dragSource = elem
    }

    fun handleDragOver(e: Event, elem: Node) {
        e.preventDefault()
    }

    fun handleDragLeave(e: Event, elem: Node) { }

    fun handleDrop(e: Event, elem: Node) {
        e.stopPropagation()

        if (dragSource != elem && dragSource != null) {
            elem.parentNode?.removeChild(dragSource!!)
            elem.parentNode?.insertBefore(dragSource!!, elem)
        }
    }
}