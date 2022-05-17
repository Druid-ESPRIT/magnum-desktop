package com.druid.models; /*
                           * To change this license header, choose License Headers in Project Properties.
                           * To change this template file, choose Tools | Templates
                           * and open the template in the editor.
                           */

/**
 * @author user
 */
public class Rcategorie {
  private String Cat;
  private int Icat;

  public Rcategorie(int Icat, String Cat) {
    this.Icat = Icat;
    this.Cat = Cat;
  }

  public void setCat(String Cat) {
    this.Cat = Cat;
  }

  public int getIcat() {
    return Icat;
  }

  @Override
  public String toString() {
    return Cat;
  }

  public Rcategorie(String Cat, int Icat) {
    this.Cat = Cat;
    this.Icat = Icat;
  }

  public Rcategorie(String Cat) {
    this.Cat = Cat;
  }
}
