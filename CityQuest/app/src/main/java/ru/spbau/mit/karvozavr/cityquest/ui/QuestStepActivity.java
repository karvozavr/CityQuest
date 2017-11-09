package ru.spbau.mit.karvozavr.cityquest.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ru.spbau.mit.karvozavr.cityquest.R;
import ru.spbau.mit.karvozavr.cityquest.quest.QuestController;
import ru.spbau.mit.karvozavr.cityquest.quest.QuestStep;

public class QuestStepActivity extends AppCompatActivity {

    private QuestStep currentQuestStep;
    boolean isActive = false;
    Button actionButton;

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
        checkButton.setOnClickListener(view -> {
            if (currentQuestStep.check(this)) {
                Toast.makeText(this, "Yes!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Wrong this time", Toast.LENGTH_LONG).show();
            }
        });
    }
}
