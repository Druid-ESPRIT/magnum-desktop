/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.druid.models;

/** @author zeineb */
public class Article {
  private int id;
  private Podcaster authorID;
  private String title;
  private String content;
  private String url;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Article() {}

  public Article(Podcaster authorID, String title, String content) {
    this.authorID = authorID;
    this.title = title;
    this.content = content;
  }

  public int getId() {
    return id;
  }

  public Podcaster getAuthorID() {
    return authorID;
  }

  public void setAuthorID(Podcaster authorID) {
    this.authorID = authorID;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public String toString() {
    return "Article{"
        + "id="
        + id
        + ", authorID="
        + authorID
        + ", title="
        + title
        + ", content="
        + content
        + '}';
  }
}
