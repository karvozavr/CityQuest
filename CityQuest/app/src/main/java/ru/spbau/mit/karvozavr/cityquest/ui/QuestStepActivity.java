package ru.spbau.mit.karvozavr.cityquest.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

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

        // Set check button listener
        Button checkButton = findViewById(R.id.check_button);
        checkButton.setText(getResources().getIdentifier(currentQuestStep.actionLabel, "string", getPackageName()));
        checkButton.setOnClickListener(view -> currentQuestStep.check(this));
    }
}
