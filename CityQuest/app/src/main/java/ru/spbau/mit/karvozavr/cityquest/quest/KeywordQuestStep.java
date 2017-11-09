package ru.spbau.mit.karvozavr.cityquest.quest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import ru.spbau.mit.karvozavr.cityquest.R;

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
     * @param context activity context
     * @return if user keyword is correct
     */
    @Override
    public boolean check(Context context) {
        AlertDialog.Builder checkDialogBuilder = new AlertDialog.Builder(context);
        View dialogView = ((Activity) context).getLayoutInflater().inflate(R.layout.keyword_check_dialog, null);
        EditText keywordField = ((Activity) context).findViewById(R.id.keyword_field);
        Button button = ((Activity) context).findViewById(R.id.check_dialog_button);

        checkDialogBuilder.setView(dialogView);
        AlertDialog checkDialog = checkDialogBuilder.create();
        checkDialog.show();

        return false;
    }

}
