package com.druid;

import com.druid.enums.OrderStatus;
import com.druid.models.Coupon;
import com.druid.models.Offer;
import com.druid.models.Order;
import com.druid.services.CouponService;
import com.druid.services.OfferService;
import com.druid.services.OrderService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Slider;

import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.ResourceBundle;

public class PlaceOrderController implements Initializable {


    @FXML
    private Label lbofferid;

    @FXML
    private Label lbOrderPrice;

    @FXML
    private Slider sliderId;

    @FXML
    private Label lbPlan;

    @FXML
    private TextField tfcoupon;
    @FXML
    private Label errormsg;

    @FXML
    private Label lbTotal;
    private OfferService os;
    private Offer o;
     CouponService cps = new CouponService();
    private Coupon c;
    Date date = new Date();
    Timestamp d1 = new Timestamp(date.getTime());
    @FXML
    void btnConfirmOrder(ActionEvent event) {

        Order or = new Order(Integer.parseInt(lbofferid.getText()),Integer.parseInt(lbPlan.getText()),Float.parseFloat(lbTotal.getText()),d1,OrderStatus.PENDING);
        OrderService ors = new OrderService();
        int getid = ors.addOrder(or);
        System.out.println(getid);
        cps.useCoupon(tfcoupon.getText());

    }
     public void useCoupon(){
         tfcoupon.textProperty().addListener((observable, oldValue, newValue) -> {
             if(cps.checkValidity(newValue).isEmpty()) {
                 errormsg.setText("Invalid coupon !");

             }
             else {
                 errormsg.setText("Valid coupon !");
                 int total = Integer.parseInt(lbTotal.getText());
                 double reduction = (cps.getReduction(newValue) * 0.01);
                 float r2 = (total*(float)reduction);
                 System.out.println(reduction);
                 System.out.println(r2);
                 String ntotal = String.valueOf(total-r2);
                 lbTotal.setText(ntotal);
             }
         });

     }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       String price = String.valueOf(20);
        lbofferid.setText("18");
        lbOrderPrice.setText(price);
        this.useCoupon();


    }
    @FXML
    void sliderClicked(MouseEvent event) {
        int dure = (int) sliderId.getValue();
        String plan = String.valueOf(dure);
        lbPlan.setText(plan);
        String total = String.valueOf(dure * 20);
        lbTotal.setText(total);
        this.useCoupon();
    }

}

