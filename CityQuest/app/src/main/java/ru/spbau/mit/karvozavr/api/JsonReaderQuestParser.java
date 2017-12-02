package ru.spbau.mit.karvozavr.api;

import android.location.Location;
import android.location.LocationManager;
import android.util.JsonReader;

import java.io.IOException;
import java.util.ArrayList;

import ru.spbau.mit.karvozavr.cityquest.quest.AbstractQuestStep;
import ru.spbau.mit.karvozavr.cityquest.quest.FinalQuestStep;
import ru.spbau.mit.karvozavr.cityquest.quest.GeoQuestStep;
import ru.spbau.mit.karvozavr.cityquest.quest.KeywordQuestStep;
import ru.spbau.mit.karvozavr.cityquest.quest.Quest;
import ru.spbau.mit.karvozavr.cityquest.quest.QuestInfo;

class JsonReaderQuestParser {
    static Quest readQuestFromJson (JsonReader reader) throws IOException {
        QuestInfo info = null;
        ArrayList<AbstractQuestStep> steps = new ArrayList<>();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "quest_info":
                    info = readQuestInfoFromJson(reader);
                    break;
                case "steps":
                    steps = readQuestStepsFromJson(reader);
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();

        return new Quest(info, (AbstractQuestStep[]) steps.toArray());
    }

    static ArrayList<QuestInfo> readQuestInfosFromJson(JsonReader reader)
            throws IOException {
        ArrayList<QuestInfo> infos = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            infos.add(readQuestInfoFromJson(reader));
        }
        reader.endArray();

        return infos;
    }

    static QuestInfo readQuestInfoFromJson(JsonReader reader) throws IOException {
        int id = 0;
        String name = null;
        String author = null;
        String image = null;
        float averageDistance = 0;
        String description = null;
        String shortDescription = null;
        float rating = 0;

        reader.beginObject();
        while (reader.hasNext()) {
            String argName = reader.nextName();
            switch (argName) {
                case "id":
                    id = reader.nextInt();
                    break;
                case "name":
                    name = reader.nextString();
                    break;
                case "author":
                    author = reader.nextString();
                    break;
                case "image":
                    image = reader.nextString();
                    break;
                case "avg_distance":
                    averageDistance = (float) reader.nextDouble();
                    break;
                case "description":
                    description = reader.nextString();
                    break;
                case "short_description":
                    shortDescription = reader.nextString();
                    break;
                case "rating":
                    rating = (float) reader.nextDouble();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();

        return new QuestInfo(id, name, null, null, averageDistance,
                description, shortDescription, rating);
    }

    private static ArrayList<AbstractQuestStep> readQuestStepsFromJson(JsonReader reader)
            throws IOException {
        ArrayList<AbstractQuestStep> steps = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            steps.add(readOneQuestStepFromJson(reader));
        }
        reader.endArray();

        return steps;
    }

    private static AbstractQuestStep readOneQuestStepFromJson(JsonReader reader)
            throws IOException {
        String title = null;
        String description = null;
        String goal = null;
        String stepType = "";
        ArrayList<String> keywords = new ArrayList<>();
        String location = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String argName = reader.nextName();
            switch (argName) {
                case "title":
                    title = reader.nextString();
                    break;
                case "description":
                    description = reader.nextString();
                    break;
                case "goal":
                    goal = reader.nextString();
                    break;
                case "step_type":
                    stepType = reader.nextString();
                    break;
                case "location":
                    location = reader.nextString();
                    break;
                case "keywords":
                    readStringArrayFromJson(reader, keywords);
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();

        switch (stepType) {
            case "final" :
                return new FinalQuestStep(title, description);
            case "geo" :
                return new GeoQuestStep(title, description, goal, null);
            case "key" :
                return new KeywordQuestStep(title, description, goal, (String[]) keywords.toArray());

            default: return null;
        }
    }

    private static void readStringArrayFromJson(JsonReader reader, ArrayList<String> array)
            throws IOException{
        reader.beginArray();
        while (reader.hasNext()) {
            array.add(reader.nextString());
        }
        reader.endArray();
    }
}
