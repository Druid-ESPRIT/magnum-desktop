package com.druid.models;

import java.sql.Timestamp;

public class Token {
  private int ID;
  private int userID;
  private String token;
  private Timestamp created;

  public Token() {}

  public Token(int ID, int userID, String token, Timestamp created) {
    this.ID = ID;
    this.userID = userID;
    this.token = token;
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

  public Timestamp getCreated() {
    return created;
  }

  public void setCreated(Timestamp created) {
    this.created = created;
  }
}
