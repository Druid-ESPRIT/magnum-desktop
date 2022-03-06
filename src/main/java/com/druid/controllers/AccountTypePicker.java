package com.druid.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;

public class AccountTypePicker implements Initializable {

  @FXML private CheckBox isPodcaster;

  @FXML private CheckBox isUser;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    isUser.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            isPodcaster.setSelected(false);

            SceneSwitcher sceneController = new SceneSwitcher();
            try {
              sceneController.showRegisterUser(event);
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        });

    isPodcaster.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            isUser.setSelected(false);

            SceneSwitcher sceneController = new SceneSwitcher();
            try {
              sceneController.showRegisterPodcaster(event);
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        });
  }
}
