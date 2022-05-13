package com.druid.utils;

import com.druid.models.Offer;

public class SelectedOffer<T extends Offer> {

  private static SelectedOffer instance = null;
  private static Class<? extends Offer> SOClass = Offer.class;
  private T offer;

  public SelectedOffer(Class<T> SOClass) {
    try {
      this.SOClass = SOClass;
      this.offer = SOClass.newInstance();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  public static <T> SelectedOffer getInstance() {
    if (instance == null) {
      instance = new SelectedOffer(SOClass);
    }
    return instance;
  }

  public T getOffer() {
    return this.offer;
  }

  public void setOffer(T offer) {
    this.offer = offer;
  }
}
