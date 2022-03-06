package com.druid.controllers;

import com.druid.models.Order;
import com.druid.models.User;
import com.druid.services.OrderService;
import com.druid.utils.ConnectedUser;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

public class OrderViewController implements Initializable {

  private User connectedUser = ConnectedUser.getInstance().getUser();
  OrderService ors = new OrderService();
  List<Order> orders = ors.getOrderByUser(connectedUser.getID());

  @FXML private TextField tfsearch;

  @FXML private GridPane grid;

  @FXML private Label chid;

  public void loadData(List<Order> orders) {

    int column = 0;
    int row = 1;
    try {
      for (int i = 0; i < orders.size(); i++) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/SingleOrder.fxml"));

        AnchorPane anchorPaneSb = fxmlLoader.load();
        SingleOrderController itemController = fxmlLoader.getController();
        itemController.setData(orders.get(i));

        if (column == 1) {
          column = 0;
          row++;
        }

        grid.add(anchorPaneSb, column++, row);
        GridPane.setMargin(anchorPaneSb, new Insets(10));

        grid.setMinWidth(Region.USE_COMPUTED_SIZE);
        grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
        grid.setMaxWidth(Region.USE_PREF_SIZE);

        grid.setMinHeight(Region.USE_COMPUTED_SIZE);
        grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
        grid.setMaxHeight(Region.USE_PREF_SIZE);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void refreshData() {
    grid.getChildren().clear();
    orders.clear();
  }

  public void listenForSearchInput() {
    tfsearch
        .textProperty()
        .addListener(
            (observable, previouslySearched, searchInput) -> {
              if (!searchInput.isEmpty()) {
                refreshData();
                loadData(ors.searchOrders(searchInput));
              } else {
                loadData(orders);
              }
            });
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    listenForSearchInput();
    loadData(orders);
  }
}
