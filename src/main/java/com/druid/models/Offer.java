package com.druid.models;

public class Offer {
    private int id;
    private int user_id;
    private float price;
    private String description;
    private String image;

    public Offer() {
    }

    public Offer(int id, int user_id, float price, String description, String image) {
        this.id = id;
        this.user_id = user_id;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    public Offer(int user_id, float price, String description, String image) {
        this.user_id = user_id;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
