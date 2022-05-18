/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.druid.models;

public class Review {

  private int id;
  private User user;
  private Event event;
  private String review;

  public Review() {}

  public Review(int id, User user, Event event, String review) {
    this.id = id;
    this.user = user;
    this.event = event;
    this.review = review;
  }

  public Review(User user, Event event, String review) {
    this.user = user;
    this.event = event;
    this.review = review;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Event getEvent() {
    return event;
  }

  public void setEvent(Event event) {
    this.event = event;
  }

  public String getReview() {
    return review;
  }

  public void setReview(String review) {
    this.review = review;
  }

  @Override
  public String toString() {
    return "Review{"
        + "id="
        + id
        + ", user="
        + user
        + ", event="
        + event
        + ", review="
        + review
        + '}';
  }
}
