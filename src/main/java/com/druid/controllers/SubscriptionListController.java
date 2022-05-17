package com.druid.controllers;

import com.druid.models.Offer;
import com.druid.models.Subscription;
import com.druid.services.OfferService;
import com.druid.services.OrderService;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class SubscriptionListController {

  @FXML private AnchorPane anchorPaneSb;

  @FXML private VBox myVbox;
  @FXML private Label lbSdate;

  @FXML private Label lbEdate;

  @FXML private Label status;
  private OfferService os = new OfferService();
  private OrderService order = new OrderService();

  public void setData(Subscription subscription) {
    int ord = order.getOfferId(subscription.getorder_id());
    Offer selected = os.findOffer(ord);
    try {
      FXMLLoader fxmlLoader = new FXMLLoader();
      fxmlLoader.setLocation(getClass().getResource("/views/Item.fxml"));

      AnchorPane anchorPane = fxmlLoader.load();
      ItemController itemController = fxmlLoader.getController();
      itemController.setDataImg(selected);
      myVbox.getChildren().add(anchorPane);
    } catch (IOException e) {
      e.printStackTrace();
    }

    status.setText(subscription.getStatus().toString());
    lbSdate.setText(String.valueOf(subscription.getStart_date()));
    lbEdate.setText(String.valueOf(subscription.getExpire_date()));
  }
}
