package ru.spbau.mit.karvozavr.cityquest.quest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

import ru.spbau.mit.karvozavr.cityquest.R;

/**
 * Quest step with a keyword.
 * User should enter correct keyword to proceed to another step.
 */
public class KeywordQuestStep extends AbstractQuestStep {

    @NotNull
    private final List<String> keywords;

    public KeywordQuestStep(String title, String description, String goal, String[] keywords) {
        super(title, description, goal, "keyword_quest_step_label");
        this.keywords = new ArrayList<>();

        // keywords should be compared case insensitive
        for (String word : keywords) {
            this.keywords.add(word.toLowerCase());
        }
    }

    /**
     * Check if user keyword is correct.
     */
    @Override
    public void check(Activity context) {
        AlertDialog.Builder checkDialogBuilder = new AlertDialog.Builder(context);
        View dialogView = context.getLayoutInflater().inflate(R.layout.keyword_check_dialog, null);
        checkDialogBuilder.setView(dialogView);

        EditText keywordField = dialogView.findViewById(R.id.keyword_field);
        Button button = dialogView.findViewById(R.id.check_dialog_button);

        AlertDialog checkDialog = checkDialogBuilder.create();

        button.setOnClickListener((view) -> {
            if (keywords.contains(keywordField.getText().toString().toLowerCase())) {
                Toast.makeText(context, "Yes!", Toast.LENGTH_LONG).show();
                QuestController.proceedToNextStep(context);
            } else {
                Toast.makeText(context, "Wrong this time", Toast.LENGTH_LONG).show();
            }

            checkDialog.cancel();
        });

        checkDialog.show();
    }

}
