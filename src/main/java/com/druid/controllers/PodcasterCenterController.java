package com.druid.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class PodcasterCenterController {

  @FXML private AnchorPane pane;

  @FXML
  void articles(ActionEvent event) {
    try {
      AnchorPane newLoadedPane = FXMLLoader.load(getClass().getResource("/views/acceuil.fxml"));
      pane.getChildren().clear();
      pane.getChildren().add(newLoadedPane);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  void events(ActionEvent event) {
    try {
      AnchorPane newLoadedPane = FXMLLoader.load(getClass().getResource("/views/eventsHome.fxml"));
      pane.getChildren().clear();
      pane.getChildren().add(newLoadedPane);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  void offers(ActionEvent event) {
    try {
      AnchorPane newLoadedPane =
          FXMLLoader.load(getClass().getResource("/views/OfferManager.fxml"));
      pane.getChildren().clear();
      pane.getChildren().add(newLoadedPane);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  void podcasts(ActionEvent event) {
    try {
      AnchorPane newLoadedPane = FXMLLoader.load(getClass().getResource("/views/FXML.fxml"));
      pane.getChildren().clear();
      pane.getChildren().add(newLoadedPane);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
