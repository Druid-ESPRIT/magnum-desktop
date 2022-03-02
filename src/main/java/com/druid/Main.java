package com.druid;

import com.druid.enums.OrderStatus;
import com.druid.models.Coupon;
import com.druid.models.Order;
import com.druid.models.Offer;
import com.druid.models.Subscription;
import com.druid.services.CouponService;
import com.druid.services.OrderService;
import com.druid.services.OfferService;
import com.druid.services.SubscriptionService;


import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;

public class Main {


    public static void main(String[] args) {
	// write your code here
        Offer s = new Offer(2,1400,40,"testing", "");
        Offer s1 = new Offer(444,40,"","");
        OfferService ss = new OfferService();

      int plan = 2;
         Date date = new Date();
        Timestamp d1 = new Timestamp(date.getTime());

        Order or = new Order(18, plan,(s.getPrice() * plan), d1,OrderStatus.PENDING);
        OrderService ors = new OrderService();
        CouponService cps =new CouponService();


        Subscription sub =new Subscription(1,47,3,d1,Timestamp.valueOf(d1.toLocalDateTime().plusDays(plan * 30)));
        SubscriptionService subs = new SubscriptionService();
        cps.generateCoupon(new Coupon(1,2,"",10,"false",d1));
       //ors.addOrder(or);

        ss.searchOffer("khaled");
        int user=1;
        String test ="36JIcGskEE6M";
        if(cps.checkValidity(test).isEmpty()){
            System.out.println("Non valid");}
            else {
                System.out.println("valid");
                double reduction = (cps.getReduction(test) * 0.01);
                float r2 = ((s.getPrice()*4)*(float)reduction);
                System.out.println(reduction);
                ors.addOrder(new Order(18,4,(s.getPrice()*4)-r2,d1,OrderStatus.PENDING));
                cps.useCoupon(test);
             //System.out.println(cps.getReduction(test));

            }


        //ors.addOrder(new Order(18,4,(s.getPrice()*4),d1,OrderStatus.PENDING));

       //  ss.deleteOffer(s);



        // ors.deleteOrder(or);
        //or.setTotal(500);

        String search = "hl";

        //ss.findSubscription(12);
        System.out.println(ss.searchOffer(search));

      //System.out.println(ss.findOffer(2));
      //System.out.println(ors.findOrder(18));

       ors.getOrders().stream()
                .filter(find_id -> find_id.getId() == 29)
                .forEach(x->System.out.println(x));

        /*ss.getOffers().stream()
                .sorted(Comparator.comparing(Offer::getDescription))
                .forEach(x->System.out.println(x));*/

       /* int orderId = subs.getOrderId(67);
      boolean expireTest = d1.after(subs.getSubscriptionTimeById(67));
       if (expireTest){
            System.out.println("Auto-renewed subscription !");
            subs.deleteSubscription(67);
            ors.addOrder( new Order(ors.getOfferId(orderId),1,ors.getOfferPrice(orderId),d1,OrderStatus.PENDING));

         }*/
       //or.setStatus(OrderStatus.COMPLETED);
     //ors.updateOrderStatus(or.getStatus(),47); //GET ID FROM UI

        if (or.getStatus() == OrderStatus.COMPLETED){

            if (or.getPlan()==1){
                System.out.println("hello");
                subs.addSubscription( new Subscription(44,3,d1,Timestamp.valueOf(d1.toLocalDateTime().plusDays(30))));
            }
            else{
            subs.addSubscription(sub);
            System.out.println(or.getPlan());
             if (or.getPlan() == 2 ){
               cps.generateCoupon(new Coupon(1,2,"",10,"true",d1));
                }
            }
        }
       // cps.cleanCoupons();

     //System.out.println(subs.getSubscriptionTimeById(26));


    }
}