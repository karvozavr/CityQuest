package ru.spbau.mit.karvozavr.cityquest.quest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;


public class QuestController {

    public static String currentQuery = "";
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

    public static void proceedToNextStep(Activity context) {
        ++progress;

        // refresh activity
        Intent intent = context.getIntent();
        context.finish();
        context.startActivity(intent);
    }

    public static Quest getSampleQuest() {
        // THIS IS TEMPORAL TODO

        Location location = new Location("Hotel");
        location.setLatitude(60.00953);
        location.setLongitude(30.35279);
        AbstractQuestStep step0 = new GeoQuestStep(
                "Общажка - общажечка",
                "Доберитесь до общажки",
                "Я сказал доберитесь!",
                location
        );

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
                "Lorem ipsum dolor sit amet. Through the gates of hell As we make our way to heaven Through the Nazi lines Primo victoria We've been training for years Now we're ready to strike As the great operation begins We're the first wave on the shore We're the first ones to fall Yet soldiers have fallen before In the dawn they will pay With their lives as the price History's written today In this burning inferno Know that nothing remains As our forces advance on the beach Aiming for heaven though serving in hell Victory is ours their forces will fall Through the gates of hell As we make our way to heaven Through the Nazi lines Primo victoria On the 6th of June On the shores of western Europe 1944 D-day upon us We've been here before Used to this kind of war Crossfire grind through the sand Our orders were easy It's kill or be killed Blood on both sides will be spilled In the dawn they will pay With their lives as the price History's written today Now that we are at war With the axis again This time we know what will come Aiming for heaven though serving in hell Victory is ours their forces will fall Through the gates of hell As we make our way to heaven Through the Nazi lines Primo victoria On 6th of June On the shores of western Europe 1944 D-day upon us 6th of June 1944 Allies are turning the war Normandy state of anarchy Overlord Aiming for heaven though serving in hell Victory is ours their forces will fall Through the gates of hell As we make our way to heaven Through the Nazi lines Primo victoria On 6th of June On the shores of western Europe 1944 D-day upon us Through the gates of hell As we make our way to heaven Through the Nazi lines Primo victoria On 6th of June On the shores of western Europe 1944 Primo victoria",
                3.5f,
                25);

        return new Quest(questInfo, new AbstractQuestStep[]{step0, step1, step2});
    }

    public static void startNewQuest(int questID, Activity context) {

        SharedPreferences sharedPreferences = context.getPreferences(Context.MODE_PRIVATE);

        Quest quest = getSampleQuest();
        try (FileOutputStream fos = context.openFileOutput(savedQuestName, Context.MODE_PRIVATE);
             ObjectOutputStream os = new ObjectOutputStream(fos)) {
            os.writeObject(quest);
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
            // TODO download from server
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void publishRating(float rating) {
        // TODO
    }

    public static List<QuestInfo> getQuestInfoList(int from, int to) {
        if (currentQuery.equals("")) {
            return ServerMock.getQuestInfosBatch(from, to);
        } else {
            return ServerMock.getQuestInfosBatch(from, to).subList(5, 10);
        }
    }
}
