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
        String stepType = jsonQuestInfoFields.get("step_type").getAsString();
        String title = jsonQuestInfoFields.get("title").getAsString();
        String description = jsonQuestInfoFields.get("description").getAsString();
        String goal = jsonQuestInfoFields.get("goal").getAsString();

        AbstractQuestStep step;


        // FIXME crutch for compilation
        final String imageUrl = "https://upload.wikimedia.org/wikipedia/en/2/24/Lenna.png";

        switch (stepType) {
            case "key":
                String keywords = jsonQuestInfoFields.get("keywords").getAsString();
                step = new KeywordQuestStep(title, description, goal, keywords.split("\n"), imageUrl);
                break;
            case "geo":
                Double latitude = jsonQuestInfoFields.get("latitude").getAsDouble();
                Double longitude = jsonQuestInfoFields.get("longitude").getAsDouble();
                step = new GeoQuestStep(title, description, goal, latitude, longitude, imageUrl);
                break;
            case "final":
                step = new FinalQuestStep(title, description, imageUrl);
                break;
            default:
                throw new JsonParseException("Wrong type of QuestStep: " + stepType);
        }

        step.stepNum = stepNum;

        return step;
    }
}
