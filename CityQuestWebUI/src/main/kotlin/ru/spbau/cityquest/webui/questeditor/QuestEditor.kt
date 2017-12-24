package ru.spbau.cityquest.webui.questeditor

const val defaultTitle : String = "Lorem ipsum dolor sit amet"
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


class QuestEditor {
    data class CurrentEdit(val editIndex: Int) {
        fun isNothing() : Boolean = editIndex == -2
        fun isNew() : Boolean = editIndex == -1
    }

    val questPointsList : ArrayList<QuestPoint> = ArrayList()

    val editNothing : CurrentEdit = CurrentEdit(-2)
    val editNew : CurrentEdit = CurrentEdit(-1)

    var editorState : QuestEditorStateManager = QuestEditorStateManager(this)

    var currentEdit : CurrentEdit = editNothing
        set(value) = TODO("Implement the currentEdit setter")

    fun getCurrentEditTitle() : String = TODO("Implement the editor interface")
    fun getCurrentEditDesc() : String = TODO("Implement the editor interface")
}