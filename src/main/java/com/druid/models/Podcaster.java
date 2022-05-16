package com.druid.models;

import com.druid.enums.UserDiscriminator;
import com.druid.enums.UserStatus;
import java.nio.file.Path;

public class Podcaster extends User {
  private String firstName;
  private String lastName;
  private String biography;

  public Podcaster() {}

  public Podcaster(
      String username,
      String email,
      String password,
      Path avatar,
      UserStatus status,
      UserDiscriminator discriminator,
      String firstName,
      String lastName,
      String biography) {
    super(username, email, password, avatar, status, discriminator);
    this.firstName = firstName;
    this.lastName = lastName;
    this.biography = biography;
  }

  public Podcaster(
      int id,
      String username,
      String email,
      String password,
      Path avatar,
      UserStatus status,
      UserDiscriminator discriminator,
      String firstName,
      String lastName,
      String biography) {
    super(id, username, email, password, avatar, status, discriminator);
    this.firstName = firstName;
    this.lastName = lastName;
    this.biography = biography;
  }

  @Override
  public String toString() {
    return "Podcaster{"
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
        + ", biography='"
        + biography
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

  public String getBiography() {
    return biography;
  }

  public void setBiography(String biography) {
    this.biography = biography;
  }
}
