package ru.spbau.mit.karvozavr.api;

import android.util.Log;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Scanner;

import ru.spbau.mit.karvozavr.cityquest.quest.AbstractQuestStep;
import ru.spbau.mit.karvozavr.cityquest.quest.Quest;
import ru.spbau.mit.karvozavr.cityquest.quest.QuestInfo;

public class CityQuestServerAPI {
    private static final String SERVER_DOMAIN_NAME = "http://subject.pythonanywhere.com/data/";
    //http://vasalf.net:1791

    private static final String TAG = "CityQuestServerAPI";

    public static ArrayList<QuestInfo> getQuestInfosFromTo(int startingFrom, int amount) {
        return getQuestInfosFromToByName(startingFrom, amount, "");
    }

    public static ArrayList<QuestInfo> getQuestInfosFromToByName(
            int startingFrom, int amount, String name) {
        String url = SERVER_DOMAIN_NAME + "get_infos?from=" + startingFrom + "&len=" + amount;

        try {
            url += "&contains=" + URLEncoder.encode(name, "UTF-8");
        } catch (UnsupportedEncodingException e){
            // This should NEVER happen.
            return new ArrayList<>();
        }

        try (InputStream is = new URL(url).openStream()) {
            return JsonReaderQuestParser.readQuestInfosFromJson(is);
        } catch (Exception e) {
            Log.e(TAG, "Error when tried to upload multiple QuestInfo: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static Quest getQuestByQuestID(Integer questId) throws LoadingErrorException {
        return new Quest(getQuestInfoByQuestID(questId), getStepsByQuestID(questId));
    }

    private static ArrayList<AbstractQuestStep> getStepsByQuestID(Integer questId)
            throws LoadingErrorException {
        String url = SERVER_DOMAIN_NAME + "get_steps?id=" + questId;

        try (InputStream is = new URL(url).openStream()) {
            return JsonReaderQuestParser.readQuestStepsFromJson(is);
        } catch (Exception e) {
            Log.e(TAG, "Error when tried to upload QuestSteps: " + e.getMessage());
            throw new LoadingErrorException("Failed to fetch data.", e);
        }
    }

    private static QuestInfo getQuestInfoByQuestID(Integer questId)
            throws LoadingErrorException {
        String url = SERVER_DOMAIN_NAME + "get_info?id=" + questId;

        try (InputStream is = new URL(url).openStream()) {
            return JsonReaderQuestParser.readSingleQuestInfoFromJson(is);
        } catch (Exception e) {
            Log.e(TAG, "Error when tried to upload QuestInfo by ID: " + e.getMessage());
            throw new LoadingErrorException("Failed to fetch data.", e);
        }
    }

    public static boolean publishRating(Integer questId, Integer ratingUpdate) {
        String request = SERVER_DOMAIN_NAME + "post_rating?id=" + questId
                + "&rate=" + ratingUpdate;

        try (InputStream is = new URL(request).openStream(); Scanner scanner = new Scanner(is)) {
            String s = scanner.nextLine();
            return s.equals("done");
        } catch (Exception e) {
            Log.e(TAG, "Error when tried to publish rating: " + e.getMessage());
            return false;
        }
    }

    /*private static int numberOfQuests() throws LoadingErrorException {
        String url = SERVER_DOMAIN_NAME + "get_number";

        try (InputStream is = new URL(url).openStream(); Scanner scanner = new Scanner(is)) {
            return scanner.nextInt();
        } catch (Exception e) {
            return Integer.MAX_VALUE;
        }
    }*/
}
