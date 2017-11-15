package ru.spbau.mit.karvozavr.cityquest.quest;

import android.app.Activity;

public class FinalQuestStep extends AbstractQuestStep {

    public FinalQuestStep(String title, String description) {
        super(title, description, null, "quest_step_action_label_final");
    }

    @Override
    public void check(Activity context) {

    }
}
