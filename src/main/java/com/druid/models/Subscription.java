package com.druid.models;

import java.sql.Timestamp;

public class Subscription {
    private int id;
    private int order_id;
    private int user_id;
    private Timestamp start_date;
    private Timestamp expire_date;

    public Subscription() {
    }

    public Subscription(int id, int order_id, int user_id, Timestamp start_date, Timestamp expire_date) {
        this.id = id;
        this.order_id = order_id;
        this.user_id = user_id;
        this.start_date = start_date;
        this.expire_date = expire_date;
    }

    public Subscription(int order_id, int user_id, Timestamp start_date, Timestamp expire_date) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.start_date = start_date;
        this.expire_date = expire_date;
    }



    public void setStart_date(Timestamp start_date) {
        this.start_date = start_date;
    }

    public void setExpire_date(Timestamp expire_date) {
        this.expire_date = expire_date;
    }


    public Timestamp getStart_date() {
        return start_date;
    }

    public Timestamp getExpire_date() {
        return expire_date;
    }

    public int getId() {
        return id;
    }

    public int getorder_id() {
        return order_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setorder_id(int order_id) {
        this.order_id = order_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", order_id=" + order_id +
                ", user_id=" + user_id +
                ", start_date=" + start_date +
                ", expire_date=" + expire_date +
                '}';
    }
}
