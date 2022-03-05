package com.druid.controllers;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestHomeController extends Application {

  @FXML
  void gotoOrder(ActionEvent event) {
    try {
      Parent home_page_parent = FXMLLoader.load(getClass().getResource("/placeorder.fxml"));

      Scene home_page_scene = new Scene(home_page_parent);
      Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      app_stage.setScene(home_page_scene);
      app_stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  void gotooffer(ActionEvent event) {
    try {
      Parent home_page_parent = FXMLLoader.load(getClass().getResource("/views/offermanager.fxml"));
      Scene home_page_scene = new Scene(home_page_parent);
      Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      app_stage.setScene(home_page_scene);
      app_stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  void OrderList(ActionEvent event) {
    try {
      Parent home_page_parent = FXMLLoader.load(getClass().getResource("/views/OrderManager.fxml"));
      Scene home_page_scene = new Scene(home_page_parent);
      Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      app_stage.setScene(home_page_scene);
      app_stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  void gotosubs(ActionEvent event) {
    try {
      Parent home_page_parent =
          FXMLLoader.load(getClass().getResource("/subscriptionManager.fxml"));
      Scene home_page_scene = new Scene(home_page_parent);
      Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      app_stage.setScene(home_page_scene);
      app_stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/views/testHome.fxml"));
    primaryStage.setTitle("test");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }
}
