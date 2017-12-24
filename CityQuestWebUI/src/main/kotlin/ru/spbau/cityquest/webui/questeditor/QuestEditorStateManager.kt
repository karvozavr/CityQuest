package ru.spbau.cityquest.webui.questeditor

class QuestEditorStateManager(editor: QuestEditor) {
    enum class QuestEditorState {
        UNSET {
            override fun stateOn() = TODO("Implement state on/off functions")
            override fun stateOff() = TODO("Implement state on/off functions")
        },
        VIEW {
            override fun stateOn() = TODO("Implement state on/off functions")
            override fun stateOff() = TODO("Implement state on/off functions")
        },
        EDIT_QUEST_POINT {
            override fun stateOn() = TODO("Implement state on/off functions")
            override fun stateOff() = TODO("Implement state on/off functions")
        },
        PLACE_MARKER {
            override fun stateOn() = TODO("Implement state on/off functions")
            override fun stateOff() = TODO("Implement state on/off functions")
        };

        abstract fun stateOn() : Unit
        abstract fun stateOff() : Unit
    }

    var currentState : QuestEditorState = QuestEditorState.UNSET
        private set

    val questEditor : QuestEditor = editor

    private fun setDbgEditorState(stateName : String) : Unit = TODO("Implement debug QuestEditor functions")

    init {
        setDbgEditorState(currentState.toString())
    }

    fun switchState(newState : QuestEditorState) {
        currentState.stateOff()
        currentState = newState
        currentState.stateOn()
    }
}