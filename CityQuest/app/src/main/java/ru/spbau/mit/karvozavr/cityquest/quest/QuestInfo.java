package ru.spbau.mit.karvozavr.cityquest.quest;

import android.graphics.drawable.Drawable;
import android.media.Rating;

import java.io.Serializable;

import java.time.Duration;

public class QuestInfo implements Serializable {
    public final int id;
    public final String name;
    public final User author;
    public final Drawable image;
    public final float averageDistance;
    public final String description;
    public final String shortDescription;
    public final float rating;

    public QuestInfo(
            int id,
            String title,
            User author,
            Drawable image,
            float averageDistance,
            String description,
            String shortDescription,
            float rating) {
        this.id = id;
        this.name = title;
        this.author = author;
        this.image = image;
        this.averageDistance = averageDistance;
        this.description = description;
        this.shortDescription = shortDescription;
        this.rating = rating;
    }
}