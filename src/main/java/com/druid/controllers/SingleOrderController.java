package com.druid.controllers;

import com.druid.models.Order;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class SingleOrderController {

  @FXML private Label orderId;

  @FXML private Label orderPlan;

  @FXML private Label orderTotal;

  @FXML private Label orderDate;
  @FXML private Hyperlink orderStatus;
  @FXML private AnchorPane pane;

  private Order order;

  public void setData(Order order) {
    this.order = order;
    orderId.setText(String.valueOf(order.getId()));
    orderPlan.setText(String.valueOf(order.getPlan()));
    orderTotal.setText(String.valueOf(order.getTotal()));
    orderDate.setText(order.getOrderDate().toString());
    orderStatus.setText(order.getStatus().toString());

    if (order.getStatus().toString().equals("Completed")) {
      orderStatus.setDisable(true);
    }

    orderStatus.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent actionEvent) {
            try {
              FXMLLoader fxmlLoader =
                  new FXMLLoader(getClass().getResource("/views/completeOrder.fxml"));
              AnchorPane editPane = fxmlLoader.load();
              CompleteOrderController controller =
                  fxmlLoader.<CompleteOrderController>getController();
              int orderid = order.getId();
              controller.getOrder(orderid);
              pane.getChildren().clear();
              pane.getChildren().add(editPane);
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        });
  }
}
