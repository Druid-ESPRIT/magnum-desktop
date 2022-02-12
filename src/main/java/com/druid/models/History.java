package com.druid.models;

import com.druid.enums.HistoryActivities;

import java.sql.Timestamp;

public class History {
  private int ID;
  private int userID;
  private HistoryActivities activity;
  private String description;
  private Timestamp time;

  public History() {}

  public History(
      int ID, int userID, HistoryActivities activity, String description, Timestamp time) {
    this.ID = ID;
    this.userID = userID;
    this.activity = activity;
    this.description = description;
    this.time = time;
  }

  public int getID() {
    return ID;
  }

  public void setID(int ID) {
    this.ID = ID;
  }

  public int getUserID() {
    return userID;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

  public HistoryActivities getActivity() {
    return activity;
  }

  public void setActivity(HistoryActivities activity) {
    this.activity = activity;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Timestamp getTime() {
    return time;
  }

  public void setTime(Timestamp time) {
    this.time = time;
  }
}
