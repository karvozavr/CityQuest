package ru.spbau.mit.karvozavr.cityquest.quest;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.Rating;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class QuestController {

    private static final String savedQuestName = "savedQuest.cqq";
    private static final String hasSavedQuest = "hasSavedQuest";
    private static int progress = 0;
    private static Quest currentQuest = null;

    public static AbstractQuestStep getCurrentQuestStep(Activity context) {
        if (currentQuest == null) {
            currentQuest = getCurrentQuest(context);
        }

        return currentQuest.getStep(getCurrentQuestProgress(currentQuest));
    }

    private static int getCurrentQuestProgress(Quest quest) {
        return progress;
    }

    public static void proceedToNextStep() {
        ++progress;
    }

    public static Quest getSampleQuest() {
        // THIS IS TEMPORAL TODO
        AbstractQuestStep step1 = new KeywordQuestStep(
                "Загадочная улица",
                "Перед вами находится улица с очень запоминающимся названием.",
                "Назовите того, в честь кого названа улица, на которой расположен дом Колотушкина.",
                new String[]{"пушкин", "пушкина", "А.С. Пушкин", "Aлександр Сергеевич Пушкин"}

        );

        AbstractQuestStep step2 = new FinalQuestStep("Поздравляем!", "Вы прошли этот квест. Надеемся он вам понравился.");

        QuestInfo questInfo = new QuestInfo(
                2,
                "Тайны писателей Петербурга",
                null,
                null,
                33.5f,
                "Lorem ipsum dolor sit amet. Through the gates of hell, as we make our way to heaven. Through the Nazi lines. Primo victoria.",
                "Lorem ipsum dolor sit amet. Through the gates of hell, as we make our way to heaven. Through the Nazi lines. Primo victoria.",
                3.5f
        );

        return new Quest(questInfo, new AbstractQuestStep[]{step1, step2});
    }

    public static void startNewQuest(int questID, Activity context) {

        SharedPreferences sharedPreferences = context.getPreferences(Context.MODE_PRIVATE);

        Quest quest = getSampleQuest();
        try (FileOutputStream fos = context.openFileOutput(savedQuestName, Context.MODE_PRIVATE);
             ObjectOutputStream os = new ObjectOutputStream(fos)) {
            os.writeObject(quest);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        sharedPreferences.edit().putBoolean(hasSavedQuest, true);
    }

    public static Quest getCurrentQuest(Activity context) {
        SharedPreferences sharedPreferences = context.getPreferences(Context.MODE_PRIVATE);
        if (!sharedPreferences.getBoolean(hasSavedQuest, false)) {
            startNewQuest(context.getIntent().getIntExtra("quest_id", 0), context);
        }

        try (FileInputStream inputStream = context.openFileInput(savedQuestName);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            return (Quest) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            // download from server
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void publishRating(float rating) {
        // TODO
    }

    public static QuestInfo[] getQuestInfoList(int amount, int from) {
        return new QuestInfo[]{};
    }

    public static QuestInfo[] getQuestInfoList(String query) {
        return new QuestInfo[]{};
    }
}
