package com.druid.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneSwitcher {
  private Scene scene;
  private Parent root;
  private Stage stage;

  public void showLogin(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void showRegisterPodcaster(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/views/RegisterPodcaster.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void showRegisterUser(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/views/RegisterUser.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void showAccountTypePicker(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/views/AccountTypePicker.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void showResetPassword(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/views/ResetPassword.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void showForgotPassword(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/views/ForgotPassword.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void showTokenInput(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/views/TokenInput.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void showHome(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/views/Home.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void showOrder(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/views/placeOrder.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
