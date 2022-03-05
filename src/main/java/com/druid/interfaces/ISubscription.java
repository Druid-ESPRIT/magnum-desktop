package com.druid.interfaces;

import com.druid.models.Subscription;

import java.util.List;

public interface ISubscription {
    public void addSubscription(Subscription sub);

    public List<Subscription> getSubscriptions();

    public void updateSubscription(Subscription sub);

    public void deleteSubscription(int id);
}
