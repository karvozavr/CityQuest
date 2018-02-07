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
import ru.spbau.mit.karvozavr.cityquest.quest.AbstractQuestStep;
import ru.spbau.mit.karvozavr.cityquest.quest.QuestInfo;


class JsonReaderQuestParser {

    static QuestInfo readSingleQuestInfoFromJson(InputStream is) {
        return (QuestInfo) jsonStreamToObject(is,
                QuestInfo.class,
                new QuestInfoDeserializer(),
                QuestInfo.class);
    }

    static ArrayList<QuestInfo> readQuestInfosFromJson(InputStream is) {
        Type collectionType = new TypeToken<ArrayList<QuestInfo>>(){}.getType();
        return (ArrayList<QuestInfo>) jsonStreamToObject(is,
                QuestInfo.class,
                new QuestInfoDeserializer(), 
                collectionType);
    }

    static ArrayList<AbstractQuestStep> readQuestStepsFromJson(InputStream is) {
        Type collectionType = new TypeToken<ArrayList<AbstractQuestStep>>(){}.getType();
        ArrayList<AbstractQuestStep> steps = (ArrayList<AbstractQuestStep>) jsonStreamToObject(is,
                AbstractQuestStep.class, 
                new QuestStepDeserializer(), 
                collectionType);

        // It is not guaranteed that objects are returned in the same order as they were in Json
        Collections.sort(steps, (s1, s2) -> s1.stepNum - s2.stepNum);

        return steps;
    }

    private static Object jsonStreamToObject(InputStream is,
                                             Class<?> c,
                                             JsonDeserializer<?> deserializer,
                                             Type objectType) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(c, deserializer);
        Gson gson = gsonBuilder.serializeNulls().create();

        return gson.fromJson(new BufferedReader(new InputStreamReader(is)), objectType);
    }

}
