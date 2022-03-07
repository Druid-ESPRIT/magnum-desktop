package com.druid.controllers;

import com.druid.models.Administrator;
import com.druid.models.Podcaster;
import com.druid.utils.ConnectedUser;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class ProfileController implements Initializable {
  ConnectedUser connectedUser = ConnectedUser.getInstance();

  @FXML private AnchorPane pane;
  @FXML private Text name;
  @FXML private Text username;
  @FXML private Text email;
  @FXML private Hyperlink history;
  @FXML private Hyperlink edit;
  @FXML private Hyperlink orders;

  @FXML private Hyperlink subscriptions;

  @FXML private ScrollPane userPane;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    username.setText("@" + connectedUser.getUser().getUsername());
    email.setText(connectedUser.getUser().getEmail());

    if (connectedUser.isPodcaster()) {
      Podcaster podcaster = (Podcaster) connectedUser.getUser();
      name.setVisible(true);
      name.setText(podcaster.getFirstName() + " " + podcaster.getLastName());
    }

    if (connectedUser.isAdministrator()) {
      Administrator admin = (Administrator) connectedUser.getUser();
      name.setVisible(true);
      name.setText(admin.getFirstName() + " " + admin.getLastName());
    }

    edit.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent actionEvent) {
            try {
              AnchorPane editPane = FXMLLoader.load(getClass().getResource("/views/Security.fxml"));
              pane.getChildren().clear();
              pane.getChildren().add(editPane);
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        });
    subscriptions.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent actionEvent) {
            try {
              AnchorPane anchor =
                  FXMLLoader.load(getClass().getResource("/views/SubscriptionManager.fxml"));
              userPane.setContent(anchor);
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        });
    orders.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent actionEvent) {
            try {
              AnchorPane anchor = FXMLLoader.load(getClass().getResource("/views/OrderView.fxml"));
              userPane.setContent(anchor);
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        });
  }
}
