package com.druid.models;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author user
 */
public class Ticket {

    private int ID;
    private String Subject;
    private String Description;
    private String CreationDate;
    private String ReSolverId;
    private int USERID;
    private String STATUS;
    private String Categorie;

    public Ticket(int ID, String Subject, String Description, String CreationDate, String ReSolverId, int USERID, String STATUS,String Categorie) {
        this.ID = ID;
        this.Subject = Subject;
        this.Description = Description;
        this.CreationDate = CreationDate;
        this.ReSolverId = ReSolverId;
        this.USERID = USERID;
        this.STATUS = STATUS;
        this.Categorie = Categorie;
    }

    public String getCategorie() {
        return Categorie;
    }

    public void setCategorie(String Categorie) {
        this.Categorie = Categorie;
    }
    

    public String getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(String CreationDate) {
        this.CreationDate = CreationDate;
    }

    public int getUSERID() {
        return USERID;
    }

    public void setUSERID(int USERID) {
        this.USERID = USERID;
    }

  
    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public Ticket() {
    }

    Ticket(String string) {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }



    public String getSubject() {
        return Subject;
    }

    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

 

    public String getReSolverId() {
        return ReSolverId;
    }

    public void setReSolverId(String ReSolverId) {
        this.ReSolverId = ReSolverId;
    }

}
