package ru.spbau.mit.karvozavr.api.utils;

import ru.spbau.mit.karvozavr.cityquest.quest.AbstractQuestStep;
import ru.spbau.mit.karvozavr.cityquest.quest.FinalQuestStep;
import ru.spbau.mit.karvozavr.cityquest.quest.GeoQuestStep;
import ru.spbau.mit.karvozavr.cityquest.quest.KeywordQuestStep;


public class QuestStepParsed {
    private int stepNum;
    private String title;
    private String description;
    private String stepType;
    private String goal;
    private String keywords;
    private double latitude;
    private double longitude;

    public QuestStepParsed(
            int stepNum,
            String title,
            String description,
            String goal,
            String stepType,
            String keywords,
            double latitude,
            double longitude) {
        this.stepNum = stepNum;
        this.title = title;
        this.description = description;
        this.goal = goal;
        this.stepType = stepType;
        this.keywords = keywords;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getStepNum() {
        return stepNum;
    }

    public AbstractQuestStep getQuestStep() {
        switch (stepType) {
            case "key":
                return new KeywordQuestStep(title, description, goal, keywords.split("\n"));
            case "geo":
                return new GeoQuestStep(title, description, goal, latitude, longitude);
            case "final":
                return new FinalQuestStep(title, description);
        }

        return null;
    }
}
