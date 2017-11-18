package ru.spbau.mit.karvozavr.api;

import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import ru.spbau.mit.karvozavr.cityquest.quest.AbstractQuestStep;
import ru.spbau.mit.karvozavr.cityquest.quest.Quest;
import ru.spbau.mit.karvozavr.cityquest.quest.QuestController;
import ru.spbau.mit.karvozavr.cityquest.quest.QuestInfo;


public class CityQuestServerAPI {
    public static boolean isEnd = false;

    public Quest getQuestByID(int id) {
        String url = "https://ru.wikipedia.org";
        try (InputStream is = new URL(url).openStream()) {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(is));

            return readQuestFromJson(jsonReader);
        } catch (Exception e) {

        }
        return null;
    }

    public QuestInfo getQuestInfo(int questId) {
        return null;
    }

    public QuestInfo[] getQuestInfosFromToByName(int startingFrom, int amount, String name) {
        return null;
    }

    public QuestInfo[] getQuestInfosFromTo(int startingFrom, int amount) {
        return null;
    }

    private Quest readQuestFromJson (JsonReader reader) throws IOException {
        QuestInfo info = null;
        //ArrayList<AbstractQuestStep> steps = null;
        AbstractQuestStep[] steps = new AbstractQuestStep[0];

        /*reader.beginObject();
        while (reader.hasNext()) {
            info = readQuestInfoFromJson(reader);
            steps = readQuestStepsFromJson(reader);
        }
        reader.endObject();*/

        return new Quest(info, steps);
    }
}
