package ru.spbau.mit.karvozavr.cityquest.quest;


import java.util.ArrayList;

import static java.lang.Math.min;

public class ServerMock {

    public static boolean isEnd = false;
    private static final int amount = 42;

    public static ArrayList<QuestInfo> getQuestInfosBatch(int from, int to) {
        ArrayList<QuestInfo> result = new ArrayList<>();
        for (int i = from; i < min(to, amount); i++) {
            QuestInfo info = QuestController.getSampleQuest().info;
            info.name += Integer.valueOf(i + 1).toString();
            result.add(info);
        }

        isEnd = to >= amount;

        return result;
    }
}
