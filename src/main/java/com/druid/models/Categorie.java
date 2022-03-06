/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.druid.models;

/**
 *
 * @author tahaj
 */
public class Categorie {
    
    private int idcateg;
    private String namecateg;
    private String descriptioncateg; 
    
    //constructors

    public Categorie(String namecateg, String descriptioncateg) {
        this.namecateg = namecateg;
        this.descriptioncateg = descriptioncateg;
    }

    public Categorie() {

    }    
    //getters&setters

    public Categorie(int idcateg,String namecateg, String descriptioncateg) {
        this.idcateg = idcateg;
        this.namecateg = namecateg;
        this.descriptioncateg = descriptioncateg;
    }

    public int getIdcateg() {
        return idcateg;
    }

    public void setIdcateg(int idcateg) {
        this.idcateg = idcateg;
    }

    public String getNamecateg() {
        return namecateg;
    }

    public void setNamecateg(String namecateg) {
        this.namecateg = namecateg;
    }

    public String getDescriptioncateg() {
        return descriptioncateg;
    }

    public void setDescriptioncateg(String descriptioncateg) {
        this.descriptioncateg = descriptioncateg;
    }

    @Override
    public String toString() {
        return  namecateg + " (" + descriptioncateg + ')';
    }
    
    
    
    
}
