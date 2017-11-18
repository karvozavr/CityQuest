package ru.spbau.mit.karvozavr.cityquest.quest;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.DoubleStream;

public class ServerMock {

    public static ArrayList<QuestInfo> getQuestInfosBatch(int from, int to) {
        QuestInfo info = QuestController.getSampleQuest().info;
        ArrayList<QuestInfo> result = new ArrayList<>();
        for (int i = from; i < to; i++) {
            result.add(info);
        }

        return result;
    }
}
