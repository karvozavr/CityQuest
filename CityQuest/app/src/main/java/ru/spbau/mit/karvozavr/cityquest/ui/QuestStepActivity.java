package ru.spbau.mit.karvozavr.cityquest.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionMenu;

import ru.spbau.mit.karvozavr.cityquest.R;
import ru.spbau.mit.karvozavr.cityquest.quest.QuestProvider;
import ru.spbau.mit.karvozavr.cityquest.quest.QuestStep;

public class QuestStepActivity extends AppCompatActivity {

    private QuestStep currentQuestStep;
    boolean isActive = false;
    Button actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_step);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show());

        actionButton = findViewById(R.id.action_button);
        fab.setOnClickListener(this::animateFab);

        currentQuestStep = QuestProvider.getCurrentQuestStep(this);

        drawQuestStep();
    }

    private void animateFab(View view) {
        Animation rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        view.startAnimation(rotate_forward);
        actionButton.setVisibility(View.VISIBLE);
    }

    public void drawQuestStep() {
        setTitle(currentQuestStep.title);

        TextView description = findViewById(R.id.step_desc);
        description.setText(currentQuestStep.description);

        TextView goal = findViewById(R.id.step_goal);
        goal.setText(currentQuestStep.goal);
    }
}
