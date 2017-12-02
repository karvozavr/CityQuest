package ru.spbau.mit.karvozavr.api;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import ru.spbau.mit.karvozavr.cityquest.quest.Quest;
import ru.spbau.mit.karvozavr.cityquest.quest.QuestInfo;
import ru.spbau.mit.karvozavr.cityquest.quest.AbstractQuestStep;


public class CityQuestServerAPI {
    private static boolean isEnd = false;
    private static final String SERVER_DOMAIN_NAME = "http://127.0.0.1:8000/get_data/";

    public static Quest getQuestByQuestInfo(QuestInfo questInfo) throws LoadingErrorException {
        String url = SERVER_DOMAIN_NAME + "get_steps?id=" + questInfo.id;
        try (InputStream is = new URL(url).openStream()) {
            List<AbstractQuestStep> steps = JsonReaderQuestParser.readQuestStepsFromJson(is);

            return new Quest(questInfo, (AbstractQuestStep[]) steps.toArray());
        } catch (Exception e) {
            throw new LoadingErrorException();
        }
    }

    public static List<QuestInfo> getQuestInfosFromTo(int startingFrom, int amount)
            throws LoadingErrorException {
        return getQuestInfosFromToByName(startingFrom, amount, "");
    }

    public static List<QuestInfo> getQuestInfosFromToByName(int startingFrom, int amount, String name)
            throws LoadingErrorException {

        String url = SERVER_DOMAIN_NAME + "get_views/?from=" + startingFrom
                + "&len=" + amount
                + "&contains=" + name;

        try (InputStream is = new URL(url).openStream()) {
            isEnd = numberOfQuests() <= startingFrom + amount;

            return JsonReaderQuestParser.readQuestInfosFromJson(is);
        } catch (Exception e) {
            throw new LoadingErrorException();
        }
    }

    private static int numberOfQuests() throws LoadingErrorException {
        String url = SERVER_DOMAIN_NAME + "get_number";
        try (InputStream is = new URL(url).openStream(); Scanner scanner = new Scanner(is)) {
            return scanner.nextInt();
        } catch (Exception e) {
            throw new LoadingErrorException();
        }
    }

    public static boolean isEndReached() {
        return isEnd;
    }
}
