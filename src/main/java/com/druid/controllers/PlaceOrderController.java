package com.druid.controllers;

import com.druid.enums.OrderStatus;
import com.druid.enums.SubscriptionStatus;
import com.druid.models.*;
import com.druid.services.CouponService;
import com.druid.services.OfferService;
import com.druid.services.OrderService;
import com.druid.services.SubscriptionService;
import com.druid.utils.ConnectedUser;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Token;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.*;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class PlaceOrderController implements Initializable {

  CouponService cps = new CouponService();
  Date date = new Date();
  Timestamp d1 = new Timestamp(date.getTime());
  OrderService ors = new OrderService();
  SubscriptionService subs = new SubscriptionService();
  Order or;
  Subscription sub;
  @FXML private AnchorPane anchorPaneConfirm;

  @FXML private Label lbofferid;

  @FXML private Slider sliderId;

  @FXML private Label lbPlan;

  @FXML private TextField tfcoupon;

  @FXML private CheckBox Mychecker;

  @FXML private Label errormsg;

  @FXML private Label lbTotal;

  @FXML private Label oldPrice;

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
  @FXML private HBox myVbox;

  @FXML private Label getOrderId;
  private OfferService os = new OfferService();
  private Offer o;
  private Coupon c;
  int price = os.getOfferPrice(96);
  Offer selected = os.findOffer(96);

  private User connectedUser = ConnectedUser.getInstance().getUser();

  @FXML
  void checked(ActionEvent event) {

    if (Mychecker.isSelected()) {
      tfcoupon.setDisable(false);
    } else tfcoupon.setDisable(true);
    errormsg.setText("");
    tfcoupon.setText("");
    errormsg.setText("");
  }

  @FXML
  void btnConfirmOrder(ActionEvent event) {
    Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
    alert1.setTitle("Place Order ");
    alert1.setHeaderText("Do you really want to place your order?");
    alert1.setContentText("Order will be placed !");
    Optional<ButtonType> option = alert1.showAndWait();
    if (option.isPresent() && option.get() == ButtonType.OK) {
      Order or =
          new Order(
              Integer.parseInt(lbofferid.getText()),
              connectedUser.getID(),
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
      anchorPaneConfirm.setVisible(false);
      anchorPanePay.setVisible(true);
      anchorPaneCircle.setVisible(true);

      RotateTransition rt = new RotateTransition(Duration.millis(2000), turnVbox);
      rt.setByAngle(360);
      rt.setCycleCount(Animation.INDEFINITE);
      rt.play();
    }
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
      cps.useCoupon(tfcoupon.getText());
      if ((Integer.parseInt(lbPlan.getText()) >= 3) && (Integer.parseInt(lbPlan.getText()) <= 5)) {
        cps.generateCoupon(new Coupon(connectedUser.getID(), "", 10, "false", d1));
      } else if (Integer.parseInt(lbPlan.getText()) >= 6) {
        cps.generateCoupon(new Coupon(connectedUser.getID(), "", 20, "false", d1));
      }
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
              if (cps.checkValidity(newValue, connectedUser.getID()).isEmpty()) {
                errormsg.setText("Invalid coupon !");
                sliderId.setValue(1);
                lbTotal.setText(String.valueOf(price));
              } else {
                oldPrice.setVisible(true);
                errormsg.setText("Reduction by " + cps.getReduction(newValue) + "%");
                float total = Float.parseFloat(lbTotal.getText());
                double reduction = (cps.getReduction(newValue) * 0.01);
                float r2 = (total * (float) reduction);
                String ntotal = String.valueOf(Math.round(total - r2));
                lbTotal.setText(ntotal);
              }
            });
  }

  public void Slider() {
    oldPrice.setVisible(false);
    lbofferid.setText("96");
    // lbOrderPrice.setText(Stprice);
    sliderId
        .valueProperty()
        .addListener(
            new ChangeListener<Number>() {
              public void changed(
                  ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                lbPlan.setText(String.valueOf(newValue.intValue()));
                String total = String.valueOf((newValue.intValue()) * price);
                lbTotal.setText(total);
              }
            });
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader();
      fxmlLoader.setLocation(getClass().getResource("/views/item.fxml"));

      AnchorPane anchorPane = fxmlLoader.load();
      ItemController itemController = fxmlLoader.getController();
      itemController.setDataSingle(selected);
      myVbox.getChildren().add(anchorPane);
    } catch (IOException e) {
      e.printStackTrace();
    }
    Slider();
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

    anchorPaneConfirm.setVisible(true);
    anchorPanePay.setVisible(false);
    anchorPaneCircle.setVisible(false);
    anchorPaneCheck.setVisible(false);
    tfcoupon.setDisable(true);

    useCoupon();
  }

  /* @FXML
  void sliderClicked(MouseEvent event) {
      int dure = (int) sliderId.getValue();
      String plan = String.valueOf(dure);
      lbPlan.setText(plan);
      String total = String.valueOf(dure * 20);
      lbTotal.setText(total);
      this.useCoupon();
  }*/

}
