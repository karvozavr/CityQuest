package ru.spbau.mit.karvozavr.cityquest.quest;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class QuestController {

    private static final String savedQuestName = "savedQuest.cqq";
    private static final String hasSavedQuest = "hasSavedQuest";
    public static int progress = 0;

    public static AbstractQuestStep getCurrentQuestStep(Activity context) {
        Quest quest = getCurrentQuest(context);
        return quest.getStep(getCurrentQuestProgress(quest));
    }

    private static int getCurrentQuestProgress(Quest quest) {
        return progress;
    }

    public static void proceedToNextStep() {
        ++progress;
    }

    public static Quest getCurrentQuest(Activity context) {
        SharedPreferences sharedPreferences = context.getPreferences(Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(hasSavedQuest, false)) {
            try (FileInputStream inputStream = context.openFileInput(savedQuestName);
                 ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
                return (Quest) objectInputStream.readObject();
            } catch (FileNotFoundException e) {
                // download from server
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            // THIS IS TEMPORAL TODO
            AbstractQuestStep step1 = new KeywordQuestStep(
                    "Step1",
                    "This is step1",
                    "enter lol or kek",
                    new String[]{"lol", "kek"}

            );

            AbstractQuestStep step2 = new FinalQuestStep("Step2", "This is step2");

            QuestInfo questInfo = new QuestInfo(
                    1,
                    "Cool quest",
                    null,
                    null,
                    null,
                    33.5f,
                    "This is a cool quest.",
                    null,
                    null
            );

            Quest quest = new Quest(questInfo, new AbstractQuestStep[]{step1, step2});
            try (FileOutputStream fos = context.openFileOutput(savedQuestName, Context.MODE_PRIVATE);
                 ObjectOutputStream os = new ObjectOutputStream(fos)) {
                os.writeObject(quest);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            sharedPreferences.edit().putBoolean(hasSavedQuest, true);


            return quest;
        }
        return null;
    }

}
