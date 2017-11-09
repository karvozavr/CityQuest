package ru.spbau.mit.karvozavr.cityquest.quest;

import android.content.Context;

public class FinalQuestStep extends QuestStep {

    public FinalQuestStep(String title, String description) {
        super(title, description, null, "quest_step_action_label_final");
    }

    @Override
    public boolean check(Context context) {
        return true;
    }
}
