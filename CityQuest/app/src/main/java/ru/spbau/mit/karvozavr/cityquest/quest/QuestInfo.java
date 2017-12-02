package ru.spbau.mit.karvozavr.cityquest.quest;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

import static java.lang.Math.min;

public class QuestInfo implements Serializable {
    public final int id;
    public String name;
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
            float rating) {
        this.id = id;
        this.name = title;
        this.author = author;
        this.image = image;
        this.averageDistance = averageDistance;
        this.description = description;
        this.shortDescription = this.description.substring(0, min(description.length(), 512)) + "…";
        this.rating = rating;
    }
}