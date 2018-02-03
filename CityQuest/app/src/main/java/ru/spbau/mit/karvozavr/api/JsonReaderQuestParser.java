package ru.spbau.mit.karvozavr.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

import ru.spbau.mit.karvozavr.api.utils.QuestInfoDeserializer;
import ru.spbau.mit.karvozavr.api.utils.QuestStepDeserializer;
import ru.spbau.mit.karvozavr.api.utils.QuestStepParsed;
import ru.spbau.mit.karvozavr.cityquest.quest.AbstractQuestStep;
import ru.spbau.mit.karvozavr.cityquest.quest.QuestInfo;


class JsonReaderQuestParser {

    static QuestInfo readSingleQuestInfoFromJson(InputStream is) {
        return (QuestInfo) getObjectFromJson(QuestInfo.class, new QuestInfoDeserializer(),
                QuestInfo.class, is);
    }

    static ArrayList<QuestInfo> readQuestInfosFromJson(InputStream is) {
        Type collectionType = new TypeToken<ArrayList<QuestInfo>>(){}.getType();
        return (ArrayList<QuestInfo>) getObjectFromJson(QuestInfo.class,
                new QuestInfoDeserializer(), collectionType, is);
    }

    static ArrayList<AbstractQuestStep> readQuestStepsFromJson(InputStream is) {
        Type collectionType = new TypeToken<ArrayList<QuestStepParsed>>(){}.getType();
        ArrayList<QuestStepParsed> parsedSteps = (ArrayList<QuestStepParsed>) getObjectFromJson(
                        QuestStepParsed.class, new QuestStepDeserializer(), collectionType, is);

        //It is not guaranteed that objects are returned in the same order as they were in Json
        Collections.sort(parsedSteps, (s1, s2) -> s1.getStepNum() - s2.getStepNum());

        ArrayList<AbstractQuestStep> abstractQuestSteps = new ArrayList<>();
        for (QuestStepParsed step : parsedSteps) {
            abstractQuestSteps.add(step.getQuestStep());
        }

        return abstractQuestSteps;
    }

    private static Object getObjectFromJson(Class<?> c, JsonDeserializer<?> deserializer,
                                            Type objectType, InputStream is) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(c, deserializer);
        Gson gson = gsonBuilder.serializeNulls().create();

        return gson.fromJson(new BufferedReader(new InputStreamReader(is)), objectType);
    }

}
