package com.druid.controllers;

import com.druid.utils.ConnectedUser;
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
  void acccountPressed(ActionEvent event) {
    try {
      AnchorPane newPane = FXMLLoader.load(getClass().getResource("/views/Profile.fxml"));
      workingPane.getChildren().clear();
      workingPane.getChildren().add(newPane);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  void articlePressed(ActionEvent event) {}

  @FXML
  void discoverPressed(ActionEvent event) {}

  @FXML
  void logoutPressed(ActionEvent event) {
    ConnectedUser.getInstance().disconnect();
    SceneSwitcher sceneController = new SceneSwitcher();
    try {
      sceneController.showLogin(event);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

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
