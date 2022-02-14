package com.druid.interfaces;


import com.druid.models.Subscription;
import java.util.List;

public interface ISubscription {
    public void addSubscription(Subscription s);
    public List<Subscription> getSubscriptions();
    public void updateSubscription(Subscription s);
    public void deleteSubscription(Subscription s);
}
