/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magnum.models;

import magnum.enums.Status;

/**
 *
 * @author Litai
 */
public class User {
    
      private int id;
    private String username;
    private String email;
    private String biography;
    private String profilePicturePath;
    private String password;
    private Status status;
    private boolean admin;
    private boolean banned;
    private int score;

    public User() {
    }
    
    

    public User(int id, String username, String email, String biography, String profilePicturePath, String password, Status status, boolean admin, boolean banned, int score) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.biography = biography;
        this.profilePicturePath = profilePicturePath;
        this.password = password;
        this.status = status;
        this.admin = admin;
        this.banned = banned;
        this.score = score;
    }

    public User(String username, String email, String biography, String profilePicturePath, String password, Status status, boolean admin, boolean banned, int score) {
        this.username = username;
        this.email = email;
        this.biography = biography;
        this.profilePicturePath = profilePicturePath;
        this.password = password;
        this.status = status;
        this.admin = admin;
        this.banned = banned;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getProfilePicturePath() {
        return profilePicturePath;
    }

    public void setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + ", email=" + email + ", biography=" + biography + ", profilePicturePath=" + profilePicturePath + ", password=" + password + ", status=" + status + ", admin=" + admin + ", banned=" + banned + ", score=" + score + '}';
    }
    
}
