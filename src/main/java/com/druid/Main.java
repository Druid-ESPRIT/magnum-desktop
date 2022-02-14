package com.druid;

import com.druid.models.Order;
import com.druid.models.Subscription;
import com.druid.services.OrderService;
import com.druid.services.SubscriptionService;

import java.nio.file.Paths;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Subscription s = new Subscription(123,20,"", Paths.get(""));
        Subscription s1 = new Subscription(444,40,"",Paths.get(""));
        SubscriptionService ss = new SubscriptionService();
        Date d1 = new Date();

        Order or = new Order(6,2, 0, 0, 0, d1, d1, d1);
        OrderService ors = new OrderService();

        ors.addOrder(or);
        // ors.deleteOrder(or);
        or.setTotal(500);

        ors.updateOrder(or);




        // ss.addSubscription(s);
        //  ss.deleteSubscription(s);
        // s.setPrice(800);
        // ss.updateSubscription(s);
    }
}
