package com.druid.interfaces;

import com.druid.models.Offer;
import com.druid.models.User;

public interface MyListener {
    public void onClickListener(Offer offer);
    public void onClickListener2(User user);
}