package com.druid.models;
import java.nio.file.Path;


public class Offer {
    private int id;
    private int podcasterID;
    private float price;
    private String description;
    private String image;

    public Offer() {
    }

    public Offer(int podcasterID, float price, String description, String image) {
        this.podcasterID = podcasterID;
        this.price = price;
        this.description = description;
        this.image = image;
    }



    public Offer(int id, int podcasterID, float price, String description, String image) {
        this.id = id;
        this.podcasterID = podcasterID;
        this.price = price;
        this.description = description;
        this.image = image;
    }





    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
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

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", podcasterID=" + podcasterID +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", image=" + image +
                '}';
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
