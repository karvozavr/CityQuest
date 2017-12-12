package ru.spbau.mit.karvozavr.cityquest.quest;

import java.io.Serializable;

public class UserProgress implements Serializable {
  public int questId;
  public int progress;
  public boolean finished = false;

  public UserProgress(int questId, int progress, boolean finished) {
    this.questId = questId;
    this.progress = progress;
    this.finished = finished;
  }
}
