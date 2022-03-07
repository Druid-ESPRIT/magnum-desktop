package com.druid.controllers;

import com.druid.models.Order;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SingleOrderController {

  @FXML private Label orderId;

  @FXML private Label orderPlan;

  @FXML private Label orderTotal;

  @FXML private Label orderStatus;

  @FXML private Label orderDate;

  private Order order;

  public void setData(Order order) {
    this.order = order;
    orderId.setText(String.valueOf(order.getId()));
    orderPlan.setText(String.valueOf(order.getPlan()));
    orderTotal.setText(String.valueOf(order.getTotal()));
    orderDate.setText(order.getOrderDate().toString());
    orderStatus.setText(order.getStatus().toString());
  }
}
