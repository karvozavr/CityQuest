package ru.spbau.cityquest.webui.questeditor

import org.w3c.dom.*
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

    private fun nodeIndexInList(node : Node) : Int {
        return (node.parentElement!! as HTMLUListElement).children.asList()
                .indexOfFirst { listNode -> node.isSameNode(listNode) }
    }

    fun handleDrop(e: Event, elem: Node) {
        e.stopPropagation()

        if (dragSource != elem) {
            val i : Int = nodeIndexInList(elem)
            val j : Int = nodeIndexInList(dragSource!!)
            println("i=$i,j=$j")
            elem.parentNode?.removeChild(dragSource!!)
            if (j > i) {
                elem.parentNode?.insertBefore(dragSource!!, elem)
            } else {
                elem.parentNode?.insertBefore(dragSource!!,
                        (elem.parentElement!! as HTMLUListElement).children[i + 1])
            }
        }
    }
}