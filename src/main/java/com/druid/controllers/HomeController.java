package com.druid.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class HomeController implements Initializable {

  @FXML private Pane workingPane;
  @FXML private Button discoverButton;

  public Pane getWorkingPane() {
    return workingPane;
  }

  public void setWorkingPane(Pane workingPane) {
    this.workingPane = workingPane;
  }

  @FXML
  void acccountPressed(ActionEvent event) {}

  @FXML
  void articlePressed(ActionEvent event) {}

  @FXML
  void discoverPressed(ActionEvent event) {}

  @FXML
  void logoutPressed(ActionEvent event) {}

  @FXML
  void playlistPressed(ActionEvent event) {}

  @FXML
  void offerManagementPressed(ActionEvent event) {
    try {
      AnchorPane newLoadedPane =
          FXMLLoader.load(getClass().getResource("/views/OfferManager.fxml"));
      workingPane.getChildren().clear();
      workingPane.getChildren().add(newLoadedPane);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {}
}
