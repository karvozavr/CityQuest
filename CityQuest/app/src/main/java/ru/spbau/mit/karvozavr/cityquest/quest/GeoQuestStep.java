package ru.spbau.mit.karvozavr.cityquest.quest;

import android.content.Context;

public class GeoQuestStep extends QuestStep {

    public GeoQuestStep(String title, String description, String goal, String actionLabel) {
        super(title, description, goal, actionLabel);
    }

    @Override
    public boolean check(Context context) {
        return false;
    }
}
