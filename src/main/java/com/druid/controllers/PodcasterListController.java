package com.druid.controllers;

import com.druid.interfaces.MyListener;
import com.druid.models.Offer;
import com.druid.models.Podcaster;
import com.druid.models.User;
import com.druid.services.PodcasterService;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

public class PodcasterListController implements Initializable {

  @FXML private GridPane grid;

  @FXML private ScrollPane showOffers;
  PodcasterService us = new PodcasterService();
  List<Podcaster> users = us.fetchAll();
  private MyListener myListener;

  public void loadData(List<Podcaster> users) {
    if (users.size() > 0) {
      // setChosenOffer(offers.get(0));
      myListener =
          new MyListener() {
            @Override
            public void onClickListener(Offer offer) {}

            @Override
            public void onClickListener2(User user) {
              System.out.println("poke poke");
            }
          };
    }

    int column = 0;
    int row = 1;

    try {
      for (int i = 0; i < users.size(); i++) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/PodcasterIcon.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();
        PodcasterIconController itemController = fxmlLoader.getController();
        itemController.showPodcaster(users.get(i), myListener);
        if (column == 5) {
          column = 0;
          row++;
        }
        grid.add(anchorPane, column++, row);
        GridPane.setMargin(anchorPane, new Insets(10));
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

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    loadData(users);
  }
}
