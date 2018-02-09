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
        JsonObject jsonQuestStepFields = jsonObject.getAsJsonObject("fields");

        int stepNum = jsonQuestStepFields.get("step_number").getAsInt();
        String stepType = jsonQuestStepFields.get("step_type").getAsString();
        String title = jsonQuestStepFields.get("title").getAsString();
        String description = jsonQuestStepFields.get("description").getAsString();
        String goal = jsonQuestStepFields.get("goal").getAsString();

        // FIXME: in case of trouble, delete on release
        final String troubleImageUrl = "https://upload.wikimedia.org/wikipedia/en/2/24/Lenna.png";
        String image;
        if (jsonQuestStepFields.has("image")) {
            image = jsonQuestStepFields.get("image").getAsString();
        } else {
            image = troubleImageUrl;
        }

        if (image == null || image.equals(""))
            image = "empty_link";


        AbstractQuestStep step;

        switch (stepType) {
            case "key":
                String keywords = jsonQuestStepFields.get("keywords").getAsString();
                step = new KeywordQuestStep(title, description, goal, keywords.split("\n"), image);
                break;
            case "geo":
                Double latitude = jsonQuestStepFields.get("latitude").getAsDouble();
                Double longitude = jsonQuestStepFields.get("longitude").getAsDouble();
                step = new GeoQuestStep(title, description, goal, latitude, longitude, image);
                break;
            case "final":
                step = new FinalQuestStep(title, description, image);
                break;
            default:
                throw new JsonParseException("Wrong type of QuestStep: " + stepType);
        }

        step.stepNum = stepNum;

        return step;
    }
}
