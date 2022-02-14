package com.druid.models;

import java.util.Date;

public class Order {
    private int id;
    private int userId;
    private int subscriptionId;
    private int duration;
    private float total;
    private Date orderDate;
    private Date startDate;
    private Date endDate;

    public Order() {
    }

    public Order(int id, int userId, int subscriptionId, int duration, float total, Date orderDate, Date startDate, Date endDate) {
        this.id = id;
        this.userId = userId;
        this.subscriptionId = subscriptionId;
        this.duration = duration;
        this.total = total;
        this.orderDate = orderDate;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Order(int userId, int subscriptionId, int duration, float total, Date orderDate, Date startDate, Date endDate) {
        this.userId = userId;
        this.subscriptionId = subscriptionId;
        this.duration = duration;
        this.total = total;
        this.orderDate = orderDate;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getSubscriptionId() {
        return subscriptionId;
    }

    public int getDuration() {
        return duration;
    }

    public float getTotal() {
        return total;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setSubscriptionId(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
