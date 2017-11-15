package ru.spbau.mit.karvozavr.cityquest.quest;

import android.app.Activity;

public class GeoQuestStep extends AbstractQuestStep {

    public GeoQuestStep(String title, String description, String goal, String actionLabel) {
        super(title, description, goal, actionLabel);
    }

    @Override
    public void check(Activity context) {

    }
}
