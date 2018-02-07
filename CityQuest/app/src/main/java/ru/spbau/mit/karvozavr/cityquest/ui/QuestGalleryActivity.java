package ru.spbau.mit.karvozavr.cityquest.ui;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import ru.spbau.mit.karvozavr.cityquest.R;
import ru.spbau.mit.karvozavr.cityquest.quest.QuestController;
import ru.spbau.mit.karvozavr.cityquest.ui.util.EndlessRecyclerViewOnScrollListener;
import ru.spbau.mit.karvozavr.cityquest.ui.util.GoogleServicesActivity;
import ru.spbau.mit.karvozavr.cityquest.ui.util.adapters.QuestInfoAdapter;

public class QuestGalleryActivity extends GoogleServicesActivity {

    private RecyclerView galleryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_gallery);
        QuestController.invokeQuestController(QuestGalleryActivity.this);

        signIn();
        loadGallery();
    }

    /**
     * Setup layout & load gallery content.
     */
    private void loadGallery() {
        galleryRecyclerView = findViewById(R.id.gallery_recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        galleryRecyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter questInfoAdapter = new QuestInfoAdapter();
        galleryRecyclerView.setAdapter(questInfoAdapter);
        galleryRecyclerView.setOnFlingListener(new EndlessRecyclerViewOnScrollListener(galleryRecyclerView));

        initRefreshLayout();
    }

    /**
     * Setup RefreshLayout adapter & etc.
     */
    private void initRefreshLayout() {
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.gallery_swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(() -> new Handler().post(() -> {
            galleryRecyclerView.setAdapter(new QuestInfoAdapter());
            Toast.makeText(QuestGalleryActivity.this, "Updated", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.gallery_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) QuestGalleryActivity.this.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = null;

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }

        if (searchView != null && searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(QuestGalleryActivity.this.getComponentName()));
            searchView.setOnCloseListener(() -> {
                Toast.makeText(QuestGalleryActivity.this, "Close", Toast.LENGTH_SHORT).show();
                QuestController.currentQuery = "";

                // refresh gallery
                loadGallery();
                return false;
            });

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    QuestController.currentQuery = query;
                    Toast.makeText(QuestGalleryActivity.this, "Query", Toast.LENGTH_SHORT).show();

                    // refresh gallery
                    loadGallery();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login_as_another_user:
                changeUser();
                return true;
            case R.id.continue_current_quest:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
