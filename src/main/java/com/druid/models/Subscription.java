package com.druid.models;
import java.nio.file.Path;


public class Subscription {
    private int id;
    private int podcasterID;
    private float price;
    private String description;
    private Path image;

    public Subscription() {
    }

    public Subscription(int podcasterID, float price, String description, Path image) {
        this.podcasterID = podcasterID;
        this.price = price;
        this.description = description;
        this.image = image;
    }



    public Subscription(int id, int podcasterID, float price, String description, Path image) {
        this.id = id;
        this.podcasterID = podcasterID;
        this.price = price;
        this.description = description;
        this.image = image;
    }





    public void setImage(Path image) {
        this.image = image;
    }

    public Path getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    public int getPodcasterID() {
        return podcasterID;
    }

    public float getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPodcasterID(int podcasterID) {
        this.podcasterID = podcasterID;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
