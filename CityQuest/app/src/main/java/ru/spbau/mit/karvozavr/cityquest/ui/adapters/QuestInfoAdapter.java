package ru.spbau.mit.karvozavr.cityquest.ui.adapters;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.spbau.mit.karvozavr.cityquest.R;
import ru.spbau.mit.karvozavr.cityquest.quest.QuestInfo;

public class QuestInfoAdapter extends RecyclerView.Adapter<QuestInfoAdapter.QuestInfoViewHolder> {

    private QuestInfo[] quests;

    public QuestInfoAdapter(QuestInfo[] quests) {
        this.quests = quests;
    }

    @Override
    public QuestInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View questInfoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_quest, null);
        QuestInfoViewHolder viewHolder = new QuestInfoViewHolder(questInfoView);

        return viewHolder;
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    @Override
    public void onBindViewHolder(QuestInfoViewHolder holder, int position) {
        Drawable drawable;
        QuestInfo questInfo = quests[position];
        TextView name = holder.questInfoView.findViewById(R.id.quest_title);
        TextView avgDistance = holder.questInfoView.findViewById(R.id.quest_avg_distance);
        TextView description = holder.questInfoView.findViewById(R.id.quest_short_description);
        name.setText(questInfo.name);
        avgDistance.setText(Float.toString(questInfo.averageDistance) + " km");
        description.setText(questInfo.shortDescription);
    }

    @Override
    public int getItemCount() {
        return quests.length;
    }

    public static class QuestInfoViewHolder extends RecyclerView.ViewHolder {

        View questInfoView;

        public QuestInfoViewHolder(View view) {
            super(view);
            questInfoView = view;
        }
    }
}