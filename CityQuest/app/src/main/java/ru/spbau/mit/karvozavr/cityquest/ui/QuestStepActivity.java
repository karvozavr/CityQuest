package ru.spbau.mit.karvozavr.cityquest.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ru.spbau.mit.karvozavr.api.CityQuestServerAPI;
import ru.spbau.mit.karvozavr.api.LoadingErrorException;
import ru.spbau.mit.karvozavr.cityquest.R;
import ru.spbau.mit.karvozavr.cityquest.quest.AbstractQuestStep;
import ru.spbau.mit.karvozavr.cityquest.quest.Quest;
import ru.spbau.mit.karvozavr.cityquest.quest.QuestController;
import ru.spbau.mit.karvozavr.cityquest.quest.QuestInfo;
import ru.spbau.mit.karvozavr.cityquest.quest.ServiceProvider;
import ru.spbau.mit.karvozavr.cityquest.ui.util.GoogleSignInActivity;

public class QuestStepActivity extends GoogleSignInActivity {

  private ProgressDialog dialog;
  public final AsyncTask<QuestInfo, Void, Void> loadTask = new AsyncLoadQuest();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quest_step);
    QuestController.invokeQuestController(this);

    loadQuest();
  }

  private void loadQuest() {
    dialog = ProgressDialog.show(this, "Loading", "Please, wait.");
    QuestController.loadCurrentQuest(this);
  }

  public void onQuestLoaded(Quest quest) {
    dialog.dismiss();
    if (quest == null) {
      Toast.makeText(this, "Fail", Toast.LENGTH_LONG).show();
    } else {
      QuestController.onQuestLoaded(quest);
      drawQuestStep(QuestController.getCurrentQuestStep());
    }
  }

  private void drawQuestStep(@NonNull AbstractQuestStep currentQuestStep) {
    setTitle(currentQuestStep.title);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    TextView description = findViewById(R.id.step_desc);
    description.setText(currentQuestStep.description);

    TextView goal = findViewById(R.id.step_goal);
    goal.setText(currentQuestStep.goal);

    Button checkButton = findViewById(R.id.check_button);
    FloatingActionButton floatingActionButton = findViewById(R.id.fab);

    checkButton.setText(getResources().getIdentifier(currentQuestStep.actionLabel, "string", getPackageName()));
    checkButton.setOnClickListener(view -> currentQuestStep.check(this));

    if (description.getText().length() + goal.getText().length() > 512) {
      floatingActionButton.setVisibility(View.VISIBLE);
      floatingActionButton.setOnClickListener(view -> currentQuestStep.check(this));
    }
  }

  private class AsyncLoadQuest extends AsyncTask<QuestInfo, Void, Void> {

    Quest quest;

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      ServiceProvider.getInternetAccess(QuestStepActivity.this);
    }

    @Override
    protected Void doInBackground(QuestInfo... args) {
      try {
        quest = CityQuestServerAPI.getQuestByQuestInfo(args[0]);
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

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    switch (requestCode) {
      case REQUEST_CODE_SIGN_IN:
        loadFromDisk();
    }
  }

  private void loadFromDisk() {

  }
}
