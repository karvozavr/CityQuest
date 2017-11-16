package ru.spbau.mit.karvozavr.cityquest.ui.adapters;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ru.spbau.mit.karvozavr.cityquest.R;
import ru.spbau.mit.karvozavr.cityquest.quest.QuestInfo;
import ru.spbau.mit.karvozavr.cityquest.ui.QuestGalleryActivity;
import ru.spbau.mit.karvozavr.cityquest.ui.QuestStepActivity;

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

        QuestInfo questInfo = quests[position];
        TextView name = holder.questInfoView.findViewById(R.id.quest_title);
        TextView avgDistance = holder.questInfoView.findViewById(R.id.quest_avg_distance);
        TextView description = holder.questInfoView.findViewById(R.id.quest_short_description);
        AppCompatRatingBar ratingBar =  holder.questInfoView.findViewById(R.id.quest_rating_bar);

        ratingBar.setRating(questInfo.rating);
        name.setText(questInfo.name);
        avgDistance.setText(Float.toString(questInfo.averageDistance) + " km");
        description.setText(questInfo.shortDescription);


        Button questStartButton = holder.questInfoView.findViewById(R.id.quest_start_button);
        questStartButton.setOnClickListener((view) -> {
            Intent intent = new Intent(holder.questInfoView.getContext(), QuestStepActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("quest_id", questInfo.id);
            holder.questInfoView.getContext().startActivity(intent);
        });

        // TODO picture
        // TODO info button
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