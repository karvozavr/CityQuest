package ru.spbau.mit.karvozavr.cityquest.quest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

import ru.spbau.mit.karvozavr.api.CityQuestServerAPI;
import ru.spbau.mit.karvozavr.cityquest.ui.QuestStepActivity;


public class QuestController {

  public static String currentQuery = "";
  private static final String savedQuestName = "savedQuest.cqq";
  private static final String savedQuestId = "savedQuestId";
  private static int progress = 0;
  private static Quest currentQuest = null;
  private static Activity context = null;

  public static void invokeQuestController(Activity activity) {
    context = activity;
  }

    /*boolean getCurrentQuestInfo() {
        TODO
    }*/

  @NonNull
  public static AbstractQuestStep getCurrentQuestStep() {
    if (currentQuest == null) {
      throw new NoSuchElementException();
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

    AbstractQuestStep step0 = new GeoQuestStep(
        "Общажка - общажечка",
        "Доберитесь до общажки",
        "Я сказал доберитесь!",
        60.00953,
        30.35279
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

    return new Quest(questInfo, new ArrayList<>(Arrays.asList(step0, step1, step2)));
  }

  public static void startNewQuest(@NonNull QuestInfo questInfo, @NonNull QuestStepActivity context) {
    // TODO kill the progress
    progress = 0;
    context.loadTask.execute(questInfo);
  }

  public static void onQuestLoaded(@NonNull Quest quest) {
    saveQuestLocally(quest);
  }

  private static void saveQuestLocally(@NonNull Quest quest) {
    currentQuest = quest;

    try (FileOutputStream fos = context.openFileOutput(savedQuestName, Context.MODE_PRIVATE);
         ObjectOutputStream os = new ObjectOutputStream(fos)) {
      os.writeObject(currentQuest);
    } catch (IOException e) {
      e.printStackTrace();
    }

    SharedPreferences sharedPreferences = context.getPreferences(Context.MODE_PRIVATE);
    sharedPreferences.edit().putInt(savedQuestId, quest.info.id).apply();
  }

  public static void loadCurrentQuest(@NonNull QuestStepActivity context) {
    SharedPreferences sharedPreferences = context.getPreferences(Context.MODE_PRIVATE);
    QuestInfo questInfo = (QuestInfo) context.getIntent().getSerializableExtra("quest_info");

    if (currentQuest != null && currentQuest.info.id == questInfo.id) {
      context.onQuestLoaded(currentQuest);
      return;
    }

    if (sharedPreferences.getInt(savedQuestId, -1) != questInfo.id) {
      startNewQuest(questInfo, context);
    } else {
      try (FileInputStream inputStream = context.openFileInput(savedQuestName);
           ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
        context.onQuestLoaded((Quest) objectInputStream.readObject());
      } catch (Exception e) {
        startNewQuest(questInfo, context);
      }
    }
  }

  public static void publishRating(float rating) {
    // TODO
  }

  public static ArrayList<QuestInfo> getQuestInfoList(int startingFrom, int amount) {
    ServiceProvider.getInternetAccess(context);

    if (currentQuery.equals("")) {
      return CityQuestServerAPI.getQuestInfosFromTo(startingFrom, amount);
    } else {
      return CityQuestServerAPI.getQuestInfosFromToByName(startingFrom, amount, currentQuery);
    }
  }


}
