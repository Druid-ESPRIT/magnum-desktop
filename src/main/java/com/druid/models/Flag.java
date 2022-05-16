package com.druid.models;

import com.druid.enums.FlagOffense;
import java.sql.Timestamp;

public class Flag {
  private int ID;
  private int flaggerID;
  private int flaggedID;
  private FlagOffense offense;
  private String description;
  private Timestamp time;

  public Flag() {}

  public Flag(
      int ID,
      int flaggerID,
      int flaggedID,
      FlagOffense offenseID,
      String description,
      Timestamp time) {
    this.ID = ID;
    this.flaggerID = flaggerID;
    this.flaggedID = flaggedID;
    this.offense = offenseID;
    this.description = description;
    this.time = time;
  }

  @Override
  public String toString() {
    return "Flag{"
        + "ID="
        + ID
        + ", flaggerID="
        + flaggerID
        + ", flaggedID="
        + flaggedID
        + ", offense="
        + offense
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

  public int getFlaggerID() {
    return flaggerID;
  }

  public void setFlaggerID(int flaggerID) {
    this.flaggerID = flaggerID;
  }

  public int getFlaggedID() {
    return flaggedID;
  }

  public void setFlaggedID(int flaggedID) {
    this.flaggedID = flaggedID;
  }
}
