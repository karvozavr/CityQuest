package ru.spbau.mit.karvozavr.cityquest.quest;

import java.io.Serializable;

/**
 * Represents a single quest step
 */
public abstract class QuestStep implements Serializable{
    public final String title;
    public final String description;
    public final String goal;

    /**
     * This is text that would be shown on action button in QuestActivity
     */
    public final String actionLabel;

    public QuestStep(String title, String description, String goal, String actionLabel) {
        this.title = title;
        this.description = description;
        this.goal = goal;
        this.actionLabel = actionLabel;
    }

    public abstract boolean check(Object input);
}
