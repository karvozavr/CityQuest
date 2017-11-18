package ru.spbau.mit.karvozavr.api;

import java.util.ArrayList;
import java.util.List;

import ru.spbau.mit.karvozavr.cityquest.quest.Quest;
import ru.spbau.mit.karvozavr.cityquest.quest.QuestController;
import ru.spbau.mit.karvozavr.cityquest.quest.QuestInfo;


public class CityQuestServerAPI {
    public static boolean isEnd = false;
    private static final String SERVER_DOMAIN_NAME = "https://ru.wikipedia.org";

    public static Quest getQuestByID(int id) throws LoadingErrorException {
        return QuestController.getSampleQuest();

        /*String url = SERVER_DOMAIN_NAME + "";  //
        try (InputStream is = new URL(url).openStream()) {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(is));

            return JsonReaderQuestParser.readQuestFromJson(jsonReader);
        } catch (Exception e) {
            throw new LoadingErrorException();
        }*/
    }

    public static QuestInfo getQuestInfoById(int questId) throws LoadingErrorException {
        return QuestController.getSampleQuest().info;

        /*String url = SERVER_DOMAIN_NAME + "";
        try (InputStream is = new URL(url).openStream()) {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(is));

            return JsonReaderQuestParser.readQuestInfoFromJson(jsonReader);
        } catch (Exception e) {
            throw new LoadingErrorException();
        }*/
    }

    public static List<QuestInfo> getQuestInfosFromTo(int startingFrom, int amount)
            throws LoadingErrorException {
        return getQuestInfosFromToByName(startingFrom, amount, "");
    }

    public static List<QuestInfo> getQuestInfosFromToByName(int startingFrom, int amount, String name)
            throws LoadingErrorException {

        ArrayList<QuestInfo> list = new ArrayList<>();
        int border = startingFrom + amount >= numberOfQuests() ? amount : numberOfQuests();
        if (border == 42)

        for (int i = 0; i < border; i++)
            list.add(QuestController.getSampleQuest().info);

        return list;

        /*String url = SERVER_DOMAIN_NAME + "";
        try (InputStream is = new URL(url).openStream()) {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(is));

            isEnd = numberOfQuests() <= startingFrom + amount;

            return JsonReaderQuestParser.readQuestInfosFromJson(jsonReader);
        } catch (Exception e) {
            throw new LoadingErrorException();
        }*/
    }

    private static int numberOfQuests() throws LoadingErrorException {
        return 42;

        /*String url = SERVER_DOMAIN_NAME + "";
        try (InputStream is = new URL(url).openStream()) {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(is));

            jsonReader.beginObject();
            int result = jsonReader.nextInt();
            jsonReader.endObject();

            return result;
        } catch (Exception e) {
            throw new LoadingErrorException();
        }*/
    }
}
