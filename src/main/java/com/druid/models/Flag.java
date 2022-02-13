package com.druid.models;

import com.druid.enums.FlagOffense;

import java.sql.Timestamp;

public class Flag {
    private int ID;
    private int userID;
    private FlagOffense offense;
    private String description;
    private Timestamp time;

    public Flag() {
    }

    public Flag(int ID, int userID, FlagOffense offenseID, String description, Timestamp time) {
        this.ID = ID;
        this.userID = userID;
        this.offense = offenseID;
        this.description = description;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Flag{" +
                "ID=" + ID +
                ", userID=" + userID +
                ", offense=" + offense +
                ", description='" + description + '\'' +
                ", time=" + time +
                '}';
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

    public FlagOffense getOffense() {
        return offense;
    }

    public void setOffense(FlagOffense offense) {
        this.offense = offense;
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
