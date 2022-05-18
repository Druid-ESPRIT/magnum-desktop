package com.druid.controllers;

import com.druid.enums.OrderStatus;
import com.druid.enums.SubscriptionStatus;
import com.druid.services.CouponService;
import com.druid.services.OrderService;
import com.druid.services.SubscriptionService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Token;
import java.net.URL;
import java.sql.Timestamp;
import java.util.*;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class CompleteOrderController implements Initializable {

  @FXML private AnchorPane pane;

  @FXML private AnchorPane anchorPaneCircle;

  @FXML private VBox turnVbox;

  @FXML private VBox vboxText;

  @FXML private AnchorPane anchorPanePay;

  @FXML private TextField cardNum;

  @FXML private TextField getmonth;

  @FXML private TextField getYear;

  @FXML private TextField getcvc;

  @FXML private AnchorPane anchorPaneCheck;

  @FXML private Label event2;

  @FXML private Label event1;

  @FXML private Label getOrderId;

  CouponService cps = new CouponService();
  Date date = new Date();
  Timestamp d1 = new Timestamp(date.getTime());
  OrderService ors = new OrderService();
  SubscriptionService subs = new SubscriptionService();
  int orderid;

  public void getOrder(int orderid) {
    this.orderid = orderid;
  }

  @FXML
  void PayClicked(ActionEvent event) {

    Stripe.apiKey =
        "sk_test_51KUO54LURk8bHQH66NOA9MpsReXKIXeXkjRe76TuRYFEhjeWw4aFTG1OLaM0oYe62iZFrcGq4Q1kYDQ9ZjNVQeue00pExVMjwm"; // add your api key

    Map<String, Object> retrieveParams = new HashMap<String, Object>();
    List<String> expandList = new ArrayList<String>();
    expandList.add("sources");
    retrieveParams.put("expand", expandList);
    Customer customer = null; // add customer id here : it will start with cus_
    try {
      customer = Customer.retrieve("cus_LGIbSsLqCcbKfg", retrieveParams, null);
    } catch (StripeException e) {
      e.printStackTrace();
    }

    Map<String, Object> cardParam = new HashMap<String, Object>(); // add card details
    cardParam.put("number", cardNum.getText());
    cardParam.put("exp_month", getmonth.getText());
    cardParam.put("exp_year", getYear.getText());
    cardParam.put("cvc", getcvc.getText());

    Map<String, Object> tokenParam = new HashMap<String, Object>();
    tokenParam.put("card", cardParam);

    Token token = null; // create a token
    try {
      token = Token.create(tokenParam);
    } catch (StripeException e) {
      e.printStackTrace();
    }

    Map<String, Object> source = new HashMap<String, Object>();
    source.put("source", token.getId()); // add token as source

    Card card = null; // add the customer details to which card is need to link
    try {
      card = (Card) customer.getSources().create(source);
    } catch (StripeException e) {
      e.printStackTrace();
    }
    String cardDetails = card.toJson();
    System.out.println("Card Details : " + cardDetails);
    try {
      customer =
          Customer.retrieve(
              "cus_LGIbSsLqCcbKfg"); // change the customer id or use to get customer by id.
    } catch (StripeException e) {
      e.printStackTrace();
    }
    System.out.println("After adding card, customer details : " + customer);

    Map<String, Object> params = new HashMap<>();
    params.put("amount", "1000");
    params.put("currency", "usd");
    params.put("customer", "cus_LGIbSsLqCcbKfg");

    Charge charge = null;
    try {
      charge = Charge.create(params);
    } catch (StripeException e) {
      e.printStackTrace();
    }
    anchorPanePay.setVisible(false);
    event1.setVisible(false);
    event2.setVisible(false);
    anchorPaneCheck.setVisible(true);
    anchorPaneCircle.setVisible(false);
    event1.setVisible(true);
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    event2.setVisible(true);
    System.out.println(charge.getStatus());
    String success = "succeeded";
    if (charge.getStatus().equals(success)) {
      ors.updateOrderStatus(OrderStatus.COMPLETED, orderid);
      subs.UpdateSubStatus(SubscriptionStatus.ACTIVE, orderid);
      /*cps.useCoupon(tfcoupon.getText());
      if ((Integer.parseInt(lbPlan.getText()) >= 3) && (Integer.parseInt(lbPlan.getText()) <= 5)) {
          cps.generateCoupon(new Coupon(connectedUser.getID(), "", 10, "false", d1));
      } else if (Integer.parseInt(lbPlan.getText()) >= 6) {
          cps.generateCoupon(new Coupon(connectedUser.getID(), "", 20, "false", d1));
      }*/
    } else {
      ors.updateOrderStatus(OrderStatus.CANCELED, orderid);
      subs.deleteSubByOrder(orderid);
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    anchorPanePay.setVisible(true);
    anchorPaneCircle.setVisible(true);
    anchorPaneCheck.setVisible(false);

    RotateTransition rt = new RotateTransition(Duration.millis(2000), turnVbox);
    rt.setByAngle(360);
    rt.setCycleCount(Animation.INDEFINITE);
    rt.play();
    cardNum
        .textProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  final ObservableValue<? extends String> ov,
                  final String oldValue,
                  final String newValue) {
                if (cardNum.getText().length() > 16) {
                  String s = cardNum.getText().substring(0, 16);
                  cardNum.setText(s);
                }
              }
            });
    getmonth
        .textProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  final ObservableValue<? extends String> ov,
                  final String oldValue,
                  final String newValue) {
                if (getmonth.getText().length() > 2) {
                  String s = getmonth.getText().substring(0, 2);
                  getmonth.setText(s);
                }
              }
            });
    getYear
        .textProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  final ObservableValue<? extends String> ov,
                  final String oldValue,
                  final String newValue) {
                if (getYear.getText().length() > 4) {
                  String s = getYear.getText().substring(0, 4);
                  getYear.setText(s);
                }
              }
            });
    getcvc
        .textProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  final ObservableValue<? extends String> ov,
                  final String oldValue,
                  final String newValue) {
                if (getcvc.getText().length() > 3) {
                  String s = getcvc.getText().substring(0, 3);
                  getcvc.setText(s);
                }
              }
            });
  }
}
