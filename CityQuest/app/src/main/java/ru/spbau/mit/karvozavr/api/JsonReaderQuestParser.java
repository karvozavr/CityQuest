package ru.spbau.mit.karvozavr.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import ru.spbau.mit.karvozavr.api.utils.QuestInfoDeserializer;
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
        ArrayList<AbstractQuestStep> steps = new ArrayList<>();


        return steps;
    }

}
