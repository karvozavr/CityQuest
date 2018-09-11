package ru.spbau.mit.karvozavr.cityquest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.spbau.mit.karvozavr.cityquest.R;
import ru.spbau.mit.karvozavr.cityquest.quest.QuestInfo;

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
            TextView avgDistance = findViewById(R.id.quest_avg_distance);
            TextView description = findViewById(R.id.quest_short_description);
            AppCompatRatingBar ratingBar = findViewById(R.id.quest_rating_bar);
            TextView usersPassed = findViewById(R.id.quest_passed);

            ratingBar.setRating(questInfo.rating);
            name.setText(questInfo.name);
            avgDistance.setText(Float.toString(questInfo.averageDistance) + " km");
            description.setText(questInfo.description);
            usersPassed.setText(questInfo.usersPassed + " passed");

            Button questStartButton = findViewById(R.id.quest_start_button);
            questStartButton.setOnClickListener((view) -> {
                Intent intent = new Intent(this, QuestStepActivity.class);
                intent.putExtra("quest_info", questInfo);
                startActivity(intent);
            });

            Button questInfoButton = findViewById(R.id.quest_info_button);
            questInfoButton.setVisibility(View.GONE);

            // FIXME change URL to real questInfo.image
            ImageView questImage = findViewById(R.id.quest_image);
            Picasso
                .with(this)
                .load(questInfo.image)
                .error(R.mipmap.saint_petersburg)
                .into(questImage);
        }
    }
}
