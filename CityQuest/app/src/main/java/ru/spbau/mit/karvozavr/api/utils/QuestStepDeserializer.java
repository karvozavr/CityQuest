package ru.spbau.mit.karvozavr.api.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import ru.spbau.mit.karvozavr.cityquest.quest.AbstractQuestStep;
import ru.spbau.mit.karvozavr.cityquest.quest.FinalQuestStep;
import ru.spbau.mit.karvozavr.cityquest.quest.GeoQuestStep;
import ru.spbau.mit.karvozavr.cityquest.quest.KeywordQuestStep;


public class QuestStepDeserializer implements JsonDeserializer<AbstractQuestStep> {
    @Override
    public AbstractQuestStep deserialize(JsonElement json,
                                         Type typeOfT,
                                         JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();
        JsonObject jsonQuestInfoFields = jsonObject.getAsJsonObject("fields");

        int stepNum = jsonQuestInfoFields.get("step_number").getAsInt();
        String title = jsonQuestInfoFields.get("title").getAsString();
        String description = jsonQuestInfoFields.get("description").getAsString();
        String stepType = jsonQuestInfoFields.get("step_type").getAsString();
        String goal = jsonQuestInfoFields.get("goal").getAsString();
        String[] keywords = jsonQuestInfoFields.get("keywords").getAsString().split("\n");

        JsonElement jsonElementLatitude = jsonQuestInfoFields.get("latitude");
        Double latitude = jsonElementLatitude.isJsonNull() ?
                60.00953 :
                jsonElementLatitude.getAsDouble();

        JsonElement jsonElementLongitude = jsonQuestInfoFields.get("longitude");
        Double longitude = jsonElementLongitude.isJsonNull() ?
                30.35279 :
                jsonElementLongitude.getAsDouble();

        AbstractQuestStep step;

        switch (stepType) {
            case "key" :
                step = new KeywordQuestStep(title, description, goal, keywords);
                break;
            case "geo" :
                step = new GeoQuestStep(title, description, goal, latitude, longitude);
                break;
            case "final" :
                step = new FinalQuestStep(title, description);
                break;
            default:
                throw new JsonParseException("Wrong type of QuestStep: " + stepType);
        }

        step.stepNum = stepNum;

        return step;
    }
}
