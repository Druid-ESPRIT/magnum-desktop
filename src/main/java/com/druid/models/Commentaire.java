/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.druid.models;

import java.sql.Date;

/**
 *
 * @author zeineb
 */
public class Commentaire {
    
    private int id;
    private User userID;
    private Article articleID;
    private String message;
    private Date submitDate;

    public Commentaire() {
    }

    public Commentaire(User userID, Article articleID, String message, Date submitDate) {
        this.userID = userID;
        this.articleID = articleID;
        this.message = message;
        this.submitDate = submitDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public Article getArticleID() {
        return articleID;
    }

    public void setArticleID(Article articleID) {
        this.articleID = articleID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

   
    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    @Override
    public String toString() {
        return
                 userID.getUsername() +
                ": "+message;
    }
}
