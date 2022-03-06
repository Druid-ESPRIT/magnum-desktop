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
public class Podcast {
    //att
    
    private int id;
    private String title;
    private String description;
    private String file;
    private int rating;
    private int views;
    private int idcateg;
    private String image;
   //

    
    //constructors

    public Podcast(String title, String description, String file, int rating, int views, int idcateg) {
        this.title = title;
        this.description = description;
       
        this.file = file;
        this.rating = rating;
        this.views = views;
        this.idcateg = idcateg;        
    }
    
    
    //...
    
    public Podcast(){
        
    }

    public Podcast(int id,String title, String description, String file, int rating, int views, int idcateg) {
        this.id = id;
        this.description = description;
        this.file = file;
        this.rating = rating;
        this.views = views;
        this.idcateg = idcateg;
        

    }

    public Podcast(String title, String description, String file, int rating, int views, int idcateg, String image) {
        this.title = title;
        this.description = description;
        this.file = file;
        this.rating = rating;
        this.views = views;
        this.idcateg = idcateg;
        this.image = image;
    }
    
    
    public String getImage() {
        return image;
    }

    //Getters and setters
    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

   

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getIdcateg() {
        return idcateg;
    }

    public void setIdcateg(int idcateg) {
        this.idcateg = idcateg;
    }
    

    //AFF

    @Override
    public String toString() {
        return title + " :" +rating+" ( "+description+" )";
    }



    
    
    
    
    
}
