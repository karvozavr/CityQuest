package ru.spbau.mit.karvozavr.api.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import ru.spbau.mit.karvozavr.cityquest.quest.QuestInfo;


public class QuestInfoDeserializer implements JsonDeserializer<QuestInfo> {
    @Override
    public QuestInfo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();
        int id = jsonObject.get("pk").getAsInt();

        JsonObject jsonQuestInfoFields = jsonObject.getAsJsonObject("fields");
        int peoplePassed = jsonQuestInfoFields.get("people_passed").getAsInt();
        String title = jsonQuestInfoFields.get("name").getAsString();
        String author = jsonQuestInfoFields.get("author").getAsString();
        String image = jsonQuestInfoFields.get("image").getAsString();
        float avgDistance = jsonQuestInfoFields.get("avg_distance").getAsFloat();
        String description = jsonQuestInfoFields.get("description").getAsString();
        float rating = jsonQuestInfoFields.get("rating").getAsFloat();

        return new QuestInfo(id, title, null, null, avgDistance, description, rating, peoplePassed);
    }
}
