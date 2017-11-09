package ru.spbau.mit.karvozavr.cityquest.quest;

import android.media.Image;
import android.media.Rating;

import java.io.Serializable;

import javax.xml.datatype.Duration;

public class QuestInfo implements Serializable {
    public final int id;
    public final String name;
    public final User author;
    public final Image image;
    public final Duration averageTime;
    public final float averageDistance;
    public final String description;
    public final String shortDescription;
    public final Rating rating;

    public QuestInfo(
            int id,
            String name,
            User author,
            Image image,
            Duration averageTime,
            float averageDistance,
            String description,
            String shortDescription,
            Rating rating) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.image = image;
        this.averageTime = averageTime;
        this.averageDistance = averageDistance;
        this.description = description;
        this.shortDescription = shortDescription;
        this.rating = rating;
    }
}