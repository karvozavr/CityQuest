package ru.spbau.mit.karvozavr.api.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class QuestStepDeserializer implements JsonDeserializer<QuestStepParsed> {

    @Override
    public QuestStepParsed deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();
        JsonObject jsonQuestInfoFields = jsonObject.getAsJsonObject("fields");

        int stepNum = jsonQuestInfoFields.get("step_number").getAsInt();
        String title = jsonQuestInfoFields.get("title").getAsString();
        String description = jsonQuestInfoFields.get("description").getAsString();
        String stepType = jsonQuestInfoFields.get("step_type").getAsString();
        String goal = jsonQuestInfoFields.get("goal").getAsString();
        String keywords = jsonQuestInfoFields.get("keywords").getAsString();

        JsonElement jsonElementLatitude = jsonQuestInfoFields.get("latitude");
        Double latitude = jsonElementLatitude.isJsonNull() ? 0.0 : jsonElementLatitude.getAsDouble();

        JsonElement jsonElementLongitude = jsonQuestInfoFields.get("longitude");
        Double longitude = jsonElementLongitude.isJsonNull() ?
                0.0 :
                jsonElementLongitude.getAsDouble();

        return new QuestStepParsed(stepNum, title, description, goal, stepType, keywords, latitude, longitude);
    }
}
