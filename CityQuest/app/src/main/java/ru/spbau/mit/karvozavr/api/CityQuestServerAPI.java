package ru.spbau.mit.karvozavr.api;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import ru.spbau.mit.karvozavr.cityquest.quest.AbstractQuestStep;
import ru.spbau.mit.karvozavr.cityquest.quest.Quest;
import ru.spbau.mit.karvozavr.cityquest.quest.QuestInfo;

public class CityQuestServerAPI {
    private static boolean isEnd = false;
    private static final String SERVER_DOMAIN_NAME = "http://subject.pythonanywhere.com/data/";

    public static Quest getQuestByQuestID(Integer questId) throws LoadingErrorException {
        return new Quest(getQuestInfoByQuestID(questId), getStepsByQuestID(questId));
    }

    private static ArrayList<AbstractQuestStep> getStepsByQuestID(Integer questId)
            throws LoadingErrorException {
        String url = SERVER_DOMAIN_NAME + "get_steps?id=" + questId;

        try (InputStream is = new URL(url).openStream()) {
            return JsonReaderQuestParser.readQuestStepsFromJson(is);
        } catch (Exception e) {
            throw new LoadingErrorException("Failed to fetch data.", e);
        }
    }

    private static QuestInfo getQuestInfoByQuestID(Integer questId)
            throws LoadingErrorException {
        String url = SERVER_DOMAIN_NAME + "get_info?id=" + questId;

        try (InputStream is = new URL(url).openStream()) {
            return JsonReaderQuestParser.readSingleQuestInfoFromJson(is);
        } catch (Exception e) {
            throw new LoadingErrorException("Failed to fetch data.", e);
        }
    }

    public static ArrayList<QuestInfo> getQuestInfosFromTo(int startingFrom, int amount) {
        return getQuestInfosFromToByName(startingFrom, amount, "");
    }

    public static ArrayList<QuestInfo> getQuestInfosFromToByName(int startingFrom, int amount, String name) {
        if (!name.matches("[a-zA-Z0-9 -]*")) {
            return new ArrayList<>();
        }

        String url = SERVER_DOMAIN_NAME + "get_infos?from=" + startingFrom
                + "&len=" + amount
                + "&contains=" + name.replace(' ', '+');

        try (InputStream is = new URL(url).openStream()) {
            isEnd = numberOfQuests() <= startingFrom + amount;

            return JsonReaderQuestParser.readQuestInfosFromJson(is);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static boolean publishRating(Integer questId, Integer ratingUpdate) {
        String request = SERVER_DOMAIN_NAME + "post_rating?id=" + questId
                + "&rate=" + ratingUpdate;

        try {
            //URL url = ;
            (new URL(request)).getContent();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    private static int numberOfQuests() throws LoadingErrorException {
        String url = SERVER_DOMAIN_NAME + "get_number";
        try (InputStream is = new URL(url).openStream(); Scanner scanner = new Scanner(is)) {
            return scanner.nextInt();
        } catch (Exception e) {
            return Integer.MAX_VALUE;
        }
    }

    public static boolean isEndReached() {
        return isEnd;
    }
}
