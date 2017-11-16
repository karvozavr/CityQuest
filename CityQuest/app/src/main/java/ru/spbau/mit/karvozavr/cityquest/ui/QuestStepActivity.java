package ru.spbau.mit.karvozavr.cityquest.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ru.spbau.mit.karvozavr.cityquest.R;
import ru.spbau.mit.karvozavr.cityquest.quest.AbstractQuestStep;
import ru.spbau.mit.karvozavr.cityquest.quest.QuestController;

public class QuestStepActivity extends AppCompatActivity {

    private AbstractQuestStep currentQuestStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_step);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawQuestStep();
    }

    public void drawQuestStep() {
        currentQuestStep = QuestController.getCurrentQuestStep(this);

        setTitle(currentQuestStep.title);

        TextView description = findViewById(R.id.step_desc);
        description.setText(currentQuestStep.description);

        TextView goal = findViewById(R.id.step_goal);
        goal.setText(currentQuestStep.goal);

        Button checkButton = findViewById(R.id.check_button);
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);

        checkButton.setText(getResources().getIdentifier(currentQuestStep.actionLabel, "string", getPackageName()));
        checkButton.setOnClickListener(view -> currentQuestStep.check(this));

        if (checkButton.isShown()) {
            floatingActionButton.setVisibility(View.VISIBLE);
            floatingActionButton.setOnClickListener(view -> currentQuestStep.check(this));
        }
    }
}
