package com.druid.models;

import com.druid.enums.UserStatus;

import java.nio.file.Path;
import java.util.Objects;

public class User {
    private int id;
    private int score;
    private Path avatar;
    private String firstName;
    private String lastName;
    private String biography;
    private String email;
    private String username;
    private String password;
    private boolean admin;
    private UserStatus status;

    public User() {
    }

    public User(String firstName, String lastName, String username, String email, String password, String biography, Path avatar, UserStatus status, boolean admin, int score) {
        this.id = id;
        this.score = score;
        this.avatar = avatar;
        this.firstName = firstName;
        this.lastName = lastName;
        this.biography = biography;
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
        return getId() == user.getId() && getEmail().equals(user.getEmail()) && getUsername().equals(user.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getUsername());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Path getAvatar() {
        return avatar;
    }

    public void setAvatar(Path avatar) {
        this.avatar = avatar;
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

    public boolean isAdmin() {
        return this.admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
