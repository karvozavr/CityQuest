package ru.spbau.cityquest.webui.questeditor

class QuestEditorStateManager(editor: QuestEditor) {
    enum class QuestEditorState {
        UNSET {
            override fun stateOn(stateManager: QuestEditorStateManager) { }
            override fun stateOff(stateManager: QuestEditorStateManager) { }
        },
        VIEW {
            override fun stateOn(stateManager: QuestEditorStateManager) {
                stateManager.questEditor.currentEdit = stateManager.questEditor.editNothing
            }
            override fun stateOff(stateManager: QuestEditorStateManager) { }
        },
        EDIT_QUEST_POINT {
            override fun stateOn(stateManager: QuestEditorStateManager) {
                stateManager.questEditor.documentNodes.editGPSPoint?.style?.visibility = "visible"
            }
            override fun stateOff(stateManager: QuestEditorStateManager) {
                stateManager.questEditor.documentNodes.editGPSPoint?.style?.visibility = "hidden"
                stateManager.questEditor.documentNodes.saveChanges?.style?.visibility = "hidden"
            }
        },
        PLACE_MARKER {
            override fun stateOn(stateManager: QuestEditorStateManager) {
                val editor = stateManager.questEditor
                editor.mapOnClickListener = editor.map.addListener("click", editor::onClickListener)
            }

            override fun stateOff(stateManager: QuestEditorStateManager) {
                val editor = stateManager.questEditor
                google.maps.event.removeListener(editor.mapOnClickListener)
                editor.mapOnClickListener = null
            }
        };

        abstract fun stateOn(stateManager: QuestEditorStateManager): Unit
        abstract fun stateOff(stateManager: QuestEditorStateManager): Unit
    }

    var currentState : QuestEditorState = QuestEditorState.UNSET
        private set

    val questEditor : QuestEditor = editor

    private fun setDbgEditorState(stateName : String) : Unit {
        questEditor.documentNodes.stateViewer?.innerHTML = "Mode: $stateName"
    }

    init {
        setDbgEditorState(currentState.toString())
    }

    fun switchState(newState : QuestEditorState) {
        currentState.stateOff(this)
        currentState = newState
        currentState.stateOn(this)
        setDbgEditorState(currentState.toString())
    }
}