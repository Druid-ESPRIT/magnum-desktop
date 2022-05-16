package com.druid.models;

import com.druid.enums.UserDiscriminator;
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
      UserDiscriminator discriminator,
      String firstName,
      String lastName) {
    super(username, email, password, avatar, status, discriminator);
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
      UserDiscriminator discriminator,
      String firstName,
      String lastName) {
    super(id, username, email, password, avatar, status, discriminator);
    this.firstName = firstName;
    this.lastName = lastName;
  }

  @Override
  public String toString() {
    return "Administrator{"
        + "ID="
        + this.getID()
        + ", avatar="
        + this.getAvatar()
        + ", email='"
        + this.getEmail()
        + '\''
        + ", username='"
        + this.getUsername()
        + '\''
        + ", password='"
        + this.getPassword()
        + '\''
        + ", status="
        + this.getStatus()
        + ", discriminator='"
        + this.getDiscriminator()
        + '\''
        + ", firstName='"
        + firstName
        + '\''
        + ", lastName='"
        + lastName
        + '\''
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    return super.equals(o);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
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
