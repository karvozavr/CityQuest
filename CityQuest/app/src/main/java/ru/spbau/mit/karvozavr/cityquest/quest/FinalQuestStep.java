package ru.spbau.mit.karvozavr.cityquest.quest;

import android.app.Activity;
import android.app.AlertDialog;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.View;
import android.widget.Button;

import ru.spbau.mit.karvozavr.cityquest.R;

public class FinalQuestStep extends AbstractQuestStep {

    public FinalQuestStep(String title,
                          String description,
                          String image) {
        super(title, description, null,  "final_quest_step_label", image);
    }

    @Override
    public void check(Activity context) {
        final AlertDialog.Builder checkDialogBuilder = new AlertDialog.Builder(context);
        final View dialogView = context.getLayoutInflater().inflate(R.layout.rate_quest_dialog, null);
        checkDialogBuilder.setView(dialogView);

        final Button button = dialogView.findViewById(R.id.rate_quest_dialog_button);
        final AppCompatRatingBar ratingBar = dialogView.findViewById(R.id.quest_rating_bar);
        final AlertDialog checkDialog = checkDialogBuilder.create();

        button.setEnabled(false);
        ratingBar.setOnRatingBarChangeListener((bar, rating, fromUser) -> {
            button.setEnabled(true);
        });

        button.setOnClickListener((view) -> {
            QuestController.publishRating(ratingBar.getRating());
            checkDialog.cancel();
        });

        checkDialog.show();
    }
}
