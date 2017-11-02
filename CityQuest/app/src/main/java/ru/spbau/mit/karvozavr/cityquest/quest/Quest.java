package ru.spbau.mit.karvozavr.cityquest.quest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.datatype.Duration;

/**
 * Represents quest entity
 */
public class Quest implements Serializable {
    public final QuestInfo info;
    private final ArrayList<QuestStep> steps;

    /**
     * Create Quest object from QuestInfo ans quest steps
     * @param info quest info
     * @param steps quest steps
     */
    public Quest(QuestInfo info, QuestStep[] steps) {
        this.info = info;
        this.steps = new ArrayList<>();
        this.steps.addAll(Arrays.asList(steps));
    }

    public QuestStep getStep(int index) {
        return steps.get(index);
    }
}
