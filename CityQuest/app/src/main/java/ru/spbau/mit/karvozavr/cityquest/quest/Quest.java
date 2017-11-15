package ru.spbau.mit.karvozavr.cityquest.quest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents quest entity
 */
public class Quest implements Serializable {
    public final QuestInfo info;
    private final ArrayList<AbstractQuestStep> steps;

    /**
     * Create Quest object from QuestInfo ans quest steps
     * @param info quest info
     * @param steps quest steps
     */
    public Quest(QuestInfo info, AbstractQuestStep[] steps) {
        this.info = info;
        this.steps = new ArrayList<>();
        this.steps.addAll(Arrays.asList(steps));
    }

    public AbstractQuestStep getStep(int index) {
        return steps.get(index);
    }
}
