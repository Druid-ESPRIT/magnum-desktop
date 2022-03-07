package com.druid.models;

import com.druid.enums.HistoryActivity;

import java.sql.Timestamp;

public class History {
    private int ID;
    private int userID;
    private HistoryActivity activity;
    private String description;
    private Timestamp time;

    public History() {
    }

    public History(int ID, int userID, HistoryActivity activity, String description, Timestamp time) {
        this.ID = ID;
        this.userID = userID;
        this.activity = activity;
        this.description = description;
        this.time = time;
    }

    @Override
    public String toString() {
        return "History{"
                + "ID="
                + ID
                + ", userID="
                + userID
                + ", activity="
                + activity
                + ", description='"
                + description
                + '\''
                + ", time="
                + time
                + '}';
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

    public HistoryActivity getActivity() {
        return activity;
    }

    public void setActivity(HistoryActivity activity) {
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
