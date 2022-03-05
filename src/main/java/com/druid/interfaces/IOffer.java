package com.druid.interfaces;

import com.druid.models.Offer;
import java.util.List;

public interface IOffer {
  public void addOffer(Offer s);

  public List<Offer> getOffers();

  public void updateOffer(Offer s, int id);

  public void deleteOffer(int id);
}
