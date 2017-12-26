package ru.spbau.mit.karvozavr.cityquest.ui.util;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import ru.spbau.mit.karvozavr.api.CityQuestServerAPI;
import ru.spbau.mit.karvozavr.cityquest.ui.adapters.QuestInfoAdapter;

public class EndlessRecyclerViewOnScrollListener extends RecyclerView.OnFlingListener {

    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private final int visibleThresholdToLoadNew = 15;


    public EndlessRecyclerViewOnScrollListener(RecyclerView recyclerView) {
        super();
        layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        this.recyclerView = recyclerView;
    }

    @Override
    public boolean onFling(int velocityX, int velocityY) {

        // Only one load at one time
        synchronized (this) {
            int currentPosition = layoutManager.findFirstVisibleItemPosition();
            if (velocityY > 0) {
                QuestInfoAdapter adapter = (QuestInfoAdapter) recyclerView.getAdapter();
                if (!adapter.loading && !CityQuestServerAPI.isEndReached() && (adapter.nextToLoad - currentPosition) <= visibleThresholdToLoadNew) {
                    adapter.loadNextBatch();
                }
            }
        }

        return false;
    }
}
