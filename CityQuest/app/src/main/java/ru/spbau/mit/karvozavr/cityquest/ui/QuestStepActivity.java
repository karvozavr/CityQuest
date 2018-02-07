package ru.spbau.mit.karvozavr.cityquest.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import ru.spbau.mit.karvozavr.api.CityQuestServerAPI;
import ru.spbau.mit.karvozavr.api.LoadingErrorException;
import ru.spbau.mit.karvozavr.cityquest.R;
import ru.spbau.mit.karvozavr.cityquest.quest.AbstractQuestStep;
import ru.spbau.mit.karvozavr.cityquest.quest.Quest;
import ru.spbau.mit.karvozavr.cityquest.quest.QuestController;
import ru.spbau.mit.karvozavr.cityquest.quest.QuestInfo;
import ru.spbau.mit.karvozavr.cityquest.quest.ServiceProvider;
import ru.spbau.mit.karvozavr.cityquest.quest.UserProgress;
import ru.spbau.mit.karvozavr.cityquest.ui.util.GoogleServicesActivity;

public class QuestStepActivity extends GoogleServicesActivity {

    private ProgressDialog loadingProgressDialog;
    public final AsyncTask<QuestInfo, Void, Void> loadTask = new AsyncLoadQuest();
    View questStepView;
    boolean isFinishingNow = false;

    private static final int REQUEST_CODE_USER_DATA_RECEIVED = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_step);
        QuestController.invokeQuestController(this);

        loadQuest();
    }

    private void loadQuest() {
        loadingProgressDialog = ProgressDialog.show(this, "Loading", "Please, wait.");
        questStepView = findViewById(R.id.quest_step_layout);
        questStepView.setVisibility(View.INVISIBLE);
        QuestController.loadCurrentQuest(this);
    }

    public void onQuestLoaded(Quest quest) {
        if (quest == null) {
            Toast.makeText(this, R.string.failed_load, Toast.LENGTH_LONG).show();
            loadingProgressDialog.dismiss();
            AlertDialog.Builder loadFailedDialogBuilder = new AlertDialog.Builder(this);
            loadFailedDialogBuilder.setNeutralButton("OK", (dialogInterface, i) -> {
                Intent intent = new Intent(QuestStepActivity.this, QuestGalleryActivity.class);
                startActivity(intent);
            });
            loadFailedDialogBuilder.setCancelable(false);
            loadFailedDialogBuilder.setMessage(R.string.failed_load);
            loadFailedDialogBuilder.create().show();
        } else {
            QuestController.onQuestLoaded(quest);
            signIn();
        }
    }

    private void drawQuestStep(@NonNull AbstractQuestStep currentQuestStep) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(currentQuestStep.title);

        ImageView toolbarImage = findViewById(R.id.toolbar_image);
        Picasso.with(this)
            .load(QuestController.currentQuest.info.image)
            .into(toolbarImage);

        TextView description = findViewById(R.id.step_desc);
        description.setText(currentQuestStep.description);

        TextView goal = findViewById(R.id.step_goal);
        goal.setText(currentQuestStep.goal);

        Button checkButton = findViewById(R.id.check_button);
        checkButton.setText(getResources().getIdentifier(currentQuestStep.actionLabel, "string", getPackageName()));
        checkButton.setOnClickListener(view -> currentQuestStep.check(this));

        /* FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        if (description.getText().length() + goal.getText().length() > 512) {
            floatingActionButton.setVisibility(View.VISIBLE);
            floatingActionButton.setOnClickListener(view -> currentQuestStep.check(this));
        }*/
    }

    @Override
    protected void onUserProgressReceived() {
        super.onUserProgressReceived();

        loadingProgressDialog.dismiss();

        UserProgress userProgress = QuestController.getUserProgress();
        if (userProgress.finished && userProgress.progress == -1) {

            // If user already passed this quest, suggest him on more time.
            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(QuestStepActivity.this);

            dialogBuilder.setMessage(R.string.repeat_this_quest);

            dialogBuilder.setPositiveButton(R.string.positive_answer,
                (dialog, which) -> QuestController.proceedToNextStep(QuestStepActivity.this));

            dialogBuilder.setNegativeButton(R.string.negative_answer,
                (dialog, which) -> {
                    Intent intent = new Intent(QuestStepActivity.this, QuestGalleryActivity.class);
                    QuestStepActivity.this.startActivity(intent);
                });

            dialogBuilder.setCancelable(false);
            dialogBuilder.show();
        }

        drawQuestStep(QuestController.getCurrentQuestStep());

        // Now activity is visible for user
        questStepView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoginSucceed() {
        super.onLoginSucceed();

        // Obtain user progress;
        getUserProgress(QuestController.currentQuest);
    }

    private class AsyncLoadQuest extends AsyncTask<QuestInfo, Void, Void> {

        private Quest quest;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ServiceProvider.getInternetAccess(QuestStepActivity.this);
        }

        @Override
        protected Void doInBackground(QuestInfo... args) {
            try {
                quest = CityQuestServerAPI.getQuestByQuestID(args[0].id);
            } catch (LoadingErrorException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            onQuestLoaded(quest);
        }
    }
}
