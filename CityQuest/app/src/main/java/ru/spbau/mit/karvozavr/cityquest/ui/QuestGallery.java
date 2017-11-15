package ru.spbau.mit.karvozavr.cityquest.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import ru.spbau.mit.karvozavr.cityquest.R;
import ru.spbau.mit.karvozavr.cityquest.quest.QuestController;
import ru.spbau.mit.karvozavr.cityquest.quest.QuestInfo;
import ru.spbau.mit.karvozavr.cityquest.ui.adapters.QuestInfoAdapter;

public class QuestGallery extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_gallery);

        RecyclerView mRecyclerView = findViewById(R.id.gallery_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // TODO TEMP
        QuestInfo info = QuestController.getCurrentQuest(this).info;

        // specify an adapter (see also next example)
        RecyclerView.Adapter mAdapter = new QuestInfoAdapter(new QuestInfo[]{info, info, info, info, info, info, info, info, info, info, info, info, info, info, info});
        mRecyclerView.setAdapter(mAdapter);
    }
}
