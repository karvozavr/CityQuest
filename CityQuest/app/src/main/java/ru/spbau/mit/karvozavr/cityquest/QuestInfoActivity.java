package ru.spbau.mit.karvozavr.cityquest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ru.spbau.mit.karvozavr.cityquest.quest.QuestInfo;
import ru.spbau.mit.karvozavr.cityquest.ui.QuestStepActivity;

public class QuestInfoActivity extends AppCompatActivity {

    private QuestInfo questInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_info);

        loadContent();
        renderContent();
    }

    void loadContent() {
        Bundle bundle = getIntent().getExtras();
        questInfo = (QuestInfo) bundle.getSerializable("quest_info");
    }

    void renderContent() {
        if (questInfo != null) {
            TextView name = findViewById(R.id.quest_title);
            TextView avgDistance =findViewById(R.id.quest_avg_distance);
            TextView description = findViewById(R.id.quest_short_description);
            AppCompatRatingBar ratingBar = findViewById(R.id.quest_rating_bar);

            ratingBar.setRating(questInfo.rating);
            name.setText(questInfo.name);
            avgDistance.setText(Float.toString(questInfo.averageDistance) + " km");
            description.setText(questInfo.description);

            Button questStartButton = findViewById(R.id.quest_start_button);
            questStartButton.setOnClickListener((view) -> {
                Intent intent = new Intent(QuestInfoActivity.this, QuestStepActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("quest_id", questInfo.id);
                QuestInfoActivity.this.startActivity(intent);
            });

            Button questInfoButton = findViewById(R.id.quest_info_button);
            questInfoButton.setVisibility(View.GONE);
        }
    }
}
