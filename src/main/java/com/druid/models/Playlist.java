/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.druid.models;

import java.util.Date;

/** @author tahaj */
public class Playlist {
  private int idplaylist;
  private int userid;
  private String description;
  private Date dateplaylist;

  public Playlist(int idplaylist, int userid, String description, Date dateplaylist) {
    this.idplaylist = idplaylist;
    this.userid = userid;
    this.description = description;
    this.dateplaylist = dateplaylist;
  }

  public Playlist() {}

  public Playlist(int userid, String description, Date dateplaylist) {
    this.userid = userid;
    this.description = description;
    this.dateplaylist = dateplaylist;
  }

  public int getIdplaylist() {
    return idplaylist;
  }

  public void setIdplaylist(int idplaylist) {
    this.idplaylist = idplaylist;
  }

  public int getUserid() {
    return userid;
  }

  public void setUserid(int userid) {
    this.userid = userid;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getDateplaylist() {
    return dateplaylist;
  }

  public void setDateplaylist(Date dateplaylist) {
    this.dateplaylist = dateplaylist;
  }

  @Override
  public String toString() {
    return "Playlist{"
        + "idplaylist="
        + idplaylist
        + ", userid="
        + userid
        + ", description="
        + description
        + ", dateplaylist="
        + dateplaylist
        + '}';
  }
}
