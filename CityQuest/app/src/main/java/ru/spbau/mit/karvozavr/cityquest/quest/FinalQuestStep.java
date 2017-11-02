package ru.spbau.mit.karvozavr.cityquest.quest;

public class FinalQuestStep extends QuestStep {

    public FinalQuestStep(String title, String description) {
        super(title, description, null, "quest_step_action_label_final");
    }

    @Override
    public boolean check(Object input) {
        return true;
    }
}
