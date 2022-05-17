package com.druid.controllers;

import com.druid.models.Subscription;
import com.druid.models.User;
import com.druid.services.SubscriptionService;
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

public class SubscriptionManagerController implements Initializable {

  private User connectedUser = ConnectedUser.getInstance().getUser();
  SubscriptionService subs = new SubscriptionService();
  List<Subscription> subscriptions = subs.getSubsByUser(connectedUser.getID());
  @FXML private TextField tfsearch;
  @FXML private Label chid;
  @FXML private GridPane grid;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    tfsearch
        .textProperty()
        .addListener(
            (observable, previouslySearched, searchInput) -> {
              if (!searchInput.isEmpty()) {
                refreshData();
                loadData(subs.searchSubs(searchInput));
              } else {
                loadData(subscriptions);
              }
            });

    loadData(subscriptions);
  }

  public void refreshData() {
    grid.getChildren().clear();
    subscriptions.clear();
  }

  public void loadData(List<Subscription> subs) {

    int column = 0;
    int row = 1;
    try {
      for (int i = 0; i < subs.size(); i++) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/subscriptionList.fxml"));

        AnchorPane anchorPaneSb = fxmlLoader.load();
        SubscriptionListController itemController = fxmlLoader.getController();
        itemController.setData(subs.get(i));

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
}
