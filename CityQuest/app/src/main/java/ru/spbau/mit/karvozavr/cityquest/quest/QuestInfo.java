package ru.spbau.mit.karvozavr.cityquest.quest;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class QuestInfo implements Serializable {
    public final int id;
    public String name;
    public final String author;
    public final String image;
    public final float averageDistance;
    public final String description;
    public final String shortDescription;
    public final float rating;
    public final int usersPassed;

    public static final int shortDescriptionMaxLength = 256;

    public QuestInfo(int id,
                     String title,
                     String author,
                     String image,
                     float averageDistance,
                     String description,
                     float rating,
                     int usersPassed) {
        this.id = id;
        this.name = title;
        this.author = author;
        this.image = image;
        this.averageDistance = averageDistance;
        this.description = description;

        if (description.length() > shortDescriptionMaxLength)
            this.shortDescription = description.substring(0, shortDescriptionMaxLength) + "…";
        else
            this.shortDescription = description;

        this.rating = rating;
        this.usersPassed = usersPassed;
    }
}