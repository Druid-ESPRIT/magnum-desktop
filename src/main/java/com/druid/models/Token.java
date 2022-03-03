package com.druid.models;

import java.sql.Timestamp;

public class Token {
  private int ID;
  private int userID;
  private String token;
  private boolean consumed;
  private Timestamp created;

  public Token() {}

  public Token(int ID, int userID, String token, boolean consumed, Timestamp created) {
    this.ID = ID;
    this.userID = userID;
    this.token = token;
    this.consumed = consumed;
    this.created = created;
  }

  @Override
  public String toString() {
    return "Token{"
        + "ID="
        + ID
        + ", userID="
        + userID
        + ", token='"
        + token
        + '\''
        + ", consumed="
        + consumed
        + ", created="
        + created
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

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public boolean isConsumed() {
    return consumed;
  }

  public void setConsumed(boolean consumed) {
    this.consumed = consumed;
  }

  public Timestamp getCreated() {
    return created;
  }

  public void setCreated(Timestamp created) {
    this.created = created;
  }
}
