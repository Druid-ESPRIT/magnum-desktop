package com.druid.interfaces;

import com.druid.models.Offer;
import com.druid.models.Podcaster;

public interface MyListener {
  public void onClickListener(Offer offer);

  public void onClickListener2(Podcaster podcaster);
}
