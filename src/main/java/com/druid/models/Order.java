package com.druid.models;

import com.druid.enums.OrderStatus;

import java.util.Date;
import java.sql.Timestamp;

public class Order {
    private int id;
    private int offerId;
    private int user_id;
    private int plan;
    private float total;
    private Timestamp orderDate;
    OrderStatus status;



    public Order() {
    }

    public Order(int id, int offerId, int user_id, int plan, float total, Timestamp orderDate, OrderStatus status) {
        this.id = id;
        this.offerId = offerId;
        this.user_id = user_id;
        this.plan = plan;
        this.total = total;
        this.orderDate = orderDate;
        this.status = status;
    }

    public Order(int offerId, int user_id, int plan, float total, Timestamp orderDate, OrderStatus status) {
        this.offerId = offerId;
        this.user_id = user_id;
        this.plan = plan;
        this.total = total;
        this.orderDate = orderDate;
        this.status = status;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getOfferId() {
        return offerId;
    }

    public int getPlan() {
        return plan;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }


    public int getofferId() {
        return offerId;
    }


    public float getTotal() {
        return total;
    }

    public Date getOrderDate() {
        return orderDate;
    }


    public void setId(int id) {
        this.id = id;
    }


    public void setofferId(int offerId) {
        this.offerId = offerId;
    }


    public void setTotal(float total) {
        this.total = total;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", offerId=" + offerId +
                ", total=" + total +
                ", orderDate=" + orderDate +
                ", status=" + status +
                '}';
    }
}
