package com.druid.models;

import com.druid.enums.UserStatus;
import java.nio.file.Path;
import java.util.Objects;

public class User {
  private int ID;
  private Path avatar;
  private String email;
  private String username;
  private String password;
  private UserStatus status;

  public User() {}

  public User(String username, String email, String password, Path avatar, UserStatus status) {
    this.avatar = avatar;
    this.email = email;
    this.username = username;
    this.password = password;
    this.status = status;
  }

  public User(
      int id, String username, String email, String password, Path avatar, UserStatus status) {
    this.ID = id;
    this.avatar = avatar;
    this.email = email;
    this.username = username;
    this.password = password;
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return getID() == user.getID()
        && getEmail().equals(user.getEmail())
        && getUsername().equals(user.getUsername());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getID(), getEmail(), getUsername());
  }

  @Override
  public String toString() {
    return "User{"
        + "ID="
        + ID
        + ", avatar="
        + avatar
        + ", email='"
        + email
        + '\''
        + ", username='"
        + username
        + '\''
        + ", password='"
        + password
        + '\''
        + ", status="
        + status
        + '}';
  }

  public int getID() {
    return ID;
  }

  public User setID(int ID) {
    this.ID = ID;
    return this;
  }

  public Path getAvatar() {
    return avatar;
  }

  public void setAvatar(Path avatar) {
    this.avatar = avatar;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UserStatus getStatus() {
    return status;
  }

  public void setStatus(UserStatus status) {
    this.status = status;
  }
}
