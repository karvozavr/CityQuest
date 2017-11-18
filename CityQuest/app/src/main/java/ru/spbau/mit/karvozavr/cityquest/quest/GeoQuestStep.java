package ru.spbau.mit.karvozavr.cityquest.quest;

import android.app.Activity;
import android.location.Location;

public class GeoQuestStep extends AbstractQuestStep {

    private final Location targetLocation;

    public GeoQuestStep(String title, String description, String goal, String actionLabel, Location targetLocation) {
        super(title, description, goal, actionLabel);
        this.targetLocation = targetLocation;
    }

    @Override
    public void check(Activity context) {

    }
}
