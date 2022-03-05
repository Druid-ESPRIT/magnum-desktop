package com.druid.models;

import com.druid.enums.SubscriptionStatus;
import java.sql.Timestamp;

public class Subscription {
  SubscriptionStatus status;
  private int id;
  private int order_id;
  private Timestamp start_date;
  private Timestamp expire_date;

  public Subscription() {}

  public Subscription(
      int id,
      int order_id,
      Timestamp start_date,
      Timestamp expire_date,
      SubscriptionStatus status) {
    this.id = id;
    this.order_id = order_id;
    this.start_date = start_date;
    this.expire_date = expire_date;
    this.status = status;
  }

  public Subscription(
      int order_id, Timestamp start_date, Timestamp expire_date, SubscriptionStatus status) {
    this.order_id = order_id;
    this.start_date = start_date;
    this.expire_date = expire_date;
    this.status = status;
  }

  public Timestamp getStart_date() {
    return start_date;
  }

  public void setStart_date(Timestamp start_date) {
    this.start_date = start_date;
  }

  public Timestamp getExpire_date() {
    return expire_date;
  }

  public void setExpire_date(Timestamp expire_date) {
    this.expire_date = expire_date;
  }

  public int getOrder_id() {
    return order_id;
  }

  public void setOrder_id(int order_id) {
    this.order_id = order_id;
  }

  public SubscriptionStatus getStatus() {
    return status;
  }

  public void setStatus(SubscriptionStatus status) {
    this.status = status;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getorder_id() {
    return order_id;
  }

  public void setorder_id(int order_id) {
    this.order_id = order_id;
  }

  @Override
  public String toString() {
    return "Subscription{"
        + "id="
        + id
        + ", order_id="
        + order_id
        + ", start_date="
        + start_date
        + ", expire_date="
        + expire_date
        + ", status="
        + status
        + '}';
  }
}
