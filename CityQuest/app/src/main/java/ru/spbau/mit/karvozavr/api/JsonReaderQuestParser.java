package ru.spbau.mit.karvozavr.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
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

    static ArrayList<QuestInfo> readQuestInfosFromJson(InputStream is)
            throws IOException {

        InputStreamReader reader = new InputStreamReader(is);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(QuestInfo.class, new QuestInfoDeserializer());
        Gson gson = gsonBuilder.create();

        Type collectionType = new TypeToken<ArrayList<QuestInfo>>(){}.getType();
	    return gson.fromJson(reader, collectionType);
    }

    static ArrayList<AbstractQuestStep> readQuestStepsFromJson(InputStream is)
            throws IOException {
        InputStreamReader reader = new InputStreamReader(is);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(QuestStepParsed.class, new QuestStepDeserializer());
        Gson gson = gsonBuilder.serializeNulls().create();

        Type collectionType = new TypeToken<ArrayList<QuestStepParsed>>(){}.getType();

        ArrayList<QuestStepParsed> parsedSteps = gson.fromJson(reader, collectionType);
        //It is not guaranteed that objects are returned in the same order as they were in Json
        Collections.sort(parsedSteps, (s1, s2) -> s1.getStepNum() - s2.getStepNum());

        ArrayList<AbstractQuestStep> abstractQuestSteps = new ArrayList<>();
        for (QuestStepParsed step : parsedSteps) {
            abstractQuestSteps.add(step.getQuestStep());
        }

        return abstractQuestSteps;
    }

}
