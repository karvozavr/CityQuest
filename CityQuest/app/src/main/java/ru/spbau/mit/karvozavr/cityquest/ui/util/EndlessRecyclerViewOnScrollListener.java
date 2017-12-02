package ru.spbau.mit.karvozavr.cityquest.ui.util;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import ru.spbau.mit.karvozavr.cityquest.R;
import ru.spbau.mit.karvozavr.cityquest.quest.ServerMock;
import ru.spbau.mit.karvozavr.cityquest.ui.adapters.QuestInfoAdapter;

public class EndlessRecyclerViewOnScrollListener extends RecyclerView.OnFlingListener {

    private LinearLayoutManager layoutManager;
    private QuestInfoAdapter adapter;

    private final int batchSize;
    private final int visibleThresholdToLoadNew = 5;
    private boolean loading = false;


    public EndlessRecyclerViewOnScrollListener(RecyclerView recyclerView) {
        super();
        layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        adapter = (QuestInfoAdapter) recyclerView.getAdapter();
        batchSize = adapter.batchSize;
    }

    /**
     * Get previous batch.
     * We know, that current batch is not first.
     */
    private void onLoadPrev() {
        adapter.loadPrevBatch();
        int position = layoutManager.findFirstVisibleItemPosition() + batchSize;
        layoutManager.scrollToPosition(position);
        loading = false;
    }

    /**
     * Get next batch.
     */
    private void onLoadNext() {
        adapter.loadNextBatch();
        int position = layoutManager.findFirstVisibleItemPosition() - batchSize;
        layoutManager.scrollToPosition(position);
        loading = false;
    }

    @Override
    public boolean onFling(int velocityX, int velocityY) {

        // Only one load at one time
        synchronized (this) {
            // TODO server
            int currentPosition = layoutManager.findFirstVisibleItemPosition();
            if (velocityY > 0) {
                if (!loading && !ServerMock.isEnd && (batchSize * 2 - currentPosition) <= visibleThresholdToLoadNew) {
                    loading = true;
                    onLoadNext();
                }
            } else if (!loading && adapter.firstLoaded != 0 && (currentPosition) <= visibleThresholdToLoadNew) {
                loading = true;
                onLoadPrev();
            }
        }

        return false;
    }
}
