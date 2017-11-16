package ru.spbau.mit.karvozavr.cityquest.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import ru.spbau.mit.karvozavr.cityquest.R;
import ru.spbau.mit.karvozavr.cityquest.quest.QuestController;
import ru.spbau.mit.karvozavr.cityquest.quest.QuestInfo;
import ru.spbau.mit.karvozavr.cityquest.ui.adapters.QuestInfoAdapter;

public class QuestGalleryActivity extends AppCompatActivity {

    private Button questStartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_gallery);

        RecyclerView galleryRecyclerView = findViewById(R.id.gallery_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        galleryRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        galleryRecyclerView.setLayoutManager(mLayoutManager);

        // TODO TEMP
        QuestInfo info = QuestController.getSampleQuest().info;

        // specify an adapter (see also next example)
        RecyclerView.Adapter mAdapter = new QuestInfoAdapter(new QuestInfo[]{info, info, info, info, info, info, info, info, info, info, info, info, info, info, info});
        galleryRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gallery_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) QuestGalleryActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(QuestGalleryActivity.this.getComponentName()));
        }

        return super.onCreateOptionsMenu(menu);
    }
}
