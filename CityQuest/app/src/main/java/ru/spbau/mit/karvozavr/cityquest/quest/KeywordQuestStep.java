package ru.spbau.mit.karvozavr.cityquest.quest;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Quest step with a keyword.
 * User should enter correct keyword to proceed to another step.
 */
public class KeywordQuestStep extends QuestStep {

    @NotNull
    private final List<String> keywords;

    public KeywordQuestStep(String title, String description, String goal, String[] keywords) {
        super(title, description, goal, "quest_step_action_label_keyword");
        this.keywords = new ArrayList<>();

        // keywords should be compared case insensitive
        for (String word : keywords) {
            this.keywords.add(word.toLowerCase());
        }
    }

    /**
     * Check if user keyword is correct.
     *
     * @param input user keyword
     * @return if user keyword is correct
     */
    @Override
    public boolean check(Object input) {
        String userKeyword = ((String) input).toLowerCase();
        return keywords.contains(userKeyword);
    }
}
