package com.druid.models;

public class PdOffer {
    private int id;
    private int podcast_id;
    private int offer_id;

    public PdOffer() {
    }

    public PdOffer(int id, int podcast_id, int offer_id) {
        this.id = id;
        this.podcast_id = podcast_id;
        this.offer_id = offer_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPodcast_id() {
        return podcast_id;
    }

    public void setPodcast_id(int podcast_id) {
        this.podcast_id = podcast_id;
    }

    public int getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(int offer_id) {
        this.offer_id = offer_id;
    }
}
