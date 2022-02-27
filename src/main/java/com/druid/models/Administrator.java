package com.druid.models;

import com.druid.enums.UserStatus;

import java.nio.file.Path;

public class Administrator extends User {
  private String firstName;
  private String lastName;

  public Administrator() {}

  public Administrator(
      String username,
      String email,
      String password,
      Path avatar,
      UserStatus status,
      String firstName,
      String lastName) {
    super(username, email, password, avatar, status);
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Administrator(
      int id,
      String username,
      String email,
      String password,
      Path avatar,
      UserStatus status,
      String firstName,
      String lastName) {
    super(id, username, email, password, avatar, status);
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
}
