package com.druid.Controllers;

import com.druid.enums.OrderStatus;
import com.druid.enums.SubscriptionStatus;
import com.druid.models.Coupon;
import com.druid.models.Offer;
import com.druid.models.Order;
import com.druid.models.Subscription;
import com.druid.services.CouponService;
import com.druid.services.OfferService;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class PlaceOrderController implements Initializable {

  @FXML private AnchorPane anchorPanePay;

  @FXML private AnchorPane anchorPaneConfirm;

  @FXML private Label lbofferid;

  @FXML private Label lbOrderPrice;

  @FXML private Slider sliderId;

  @FXML private Label lbPlan;

  @FXML private TextField tfcoupon;

  @FXML private Label errormsg;
  @FXML private AnchorPane anchorPaneCircle;

  @FXML private VBox turnVbox;
  @FXML private TextField cardNum;

  @FXML private TextField getmonth;

  @FXML private TextField getYear;

  @FXML private TextField getcvc;
  @FXML private AnchorPane anchorPaneCheck;

  @FXML private Label event2;

  @FXML private Label event1;

  @FXML private VBox vboxText;
  @FXML private Label getOrderId;

  @FXML private Label lbTotal;
  private OfferService os;
  private Offer o;
  CouponService cps = new CouponService();
  private Coupon c;
  Date date = new Date();
  Timestamp d1 = new Timestamp(date.getTime());
  OrderService ors = new OrderService();
  SubscriptionService subs = new SubscriptionService();
  Order or;
  Subscription sub;

  @FXML
  void btnConfirmOrder(ActionEvent event) {
    Order or =
        new Order(
            Integer.parseInt(lbofferid.getText()),
            1,
            Integer.parseInt(lbPlan.getText()),
            Float.parseFloat(lbTotal.getText()),
            d1,
            OrderStatus.PENDING);
    int getid = ors.addOrder(or);
    Subscription sub =
        new Subscription(
            getid,
            d1,
            Timestamp.valueOf(
                d1.toLocalDateTime().plusDays(Integer.parseInt(lbPlan.getText()) * 30)),
            SubscriptionStatus.ON_HOLD);
    subs.addSubscription(sub);
    System.out.println(getid);
    getOrderId.setText(String.valueOf(getid));
    cps.useCoupon(tfcoupon.getText());
    anchorPaneConfirm.setVisible(false);
    anchorPanePay.setVisible(true);
    anchorPaneCircle.setVisible(true);

    RotateTransition rt = new RotateTransition(Duration.millis(2000), turnVbox);
    rt.setByAngle(360);
    rt.setCycleCount(Animation.INDEFINITE);
    rt.play();
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
    params.put("amount", Integer.parseInt(lbTotal.getText()) + "00");
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
      ors.updateOrderStatus(OrderStatus.COMPLETED, Integer.parseInt(getOrderId.getText()));
      subs.UpdateSubStatus(SubscriptionStatus.ACTIVE, Integer.parseInt(getOrderId.getText()));
    } else {
      ors.updateOrderStatus(OrderStatus.CANCELED, Integer.parseInt(getOrderId.getText()));
      subs.deleteSubByOrder(Integer.parseInt(getOrderId.getText()));
    }
  }

  public void useCoupon() {
    tfcoupon
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (cps.checkValidity(newValue).isEmpty()) {
                errormsg.setText("Invalid coupon !");
              } else {
                errormsg.setText("Valid coupon !");
                int total = Integer.parseInt(lbTotal.getText());
                double reduction = (cps.getReduction(newValue) * 0.01);
                float r2 = (total * (float) reduction);
                System.out.println(reduction);
                System.out.println(r2);
                String ntotal = String.valueOf(total - r2);
                lbTotal.setText(ntotal);
              }
            });
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    anchorPaneConfirm.setVisible(true);
    anchorPanePay.setVisible(false);
    anchorPaneCircle.setVisible(false);
    anchorPaneCheck.setVisible(false);
    String price = String.valueOf(20);
    lbofferid.setText("44");
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
