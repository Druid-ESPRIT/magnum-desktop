package com.druid.controllers;

import com.druid.models.Administrator;
import com.druid.models.Podcaster;
import com.druid.utils.ConnectedUser;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringJoiner;
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

  @FXML private AnchorPane fileChooserPane;
  @FXML private AnchorPane pane;
  @FXML private Text name;
  @FXML private Hyperlink flag;
  @FXML private Text username;
  @FXML private Text email;
  @FXML private Hyperlink history;
  @FXML private Hyperlink orders;
  @FXML private Hyperlink security;

  @FXML private Hyperlink subscriptions;

  @FXML private ScrollPane userPane;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    username.setText("@" + connectedUser.getUser().getUsername());
    email.setText(connectedUser.getUser().getEmail());

    if (connectedUser.isPodcaster()) {
      Podcaster podcaster = (Podcaster) connectedUser.getUser();
      name.setVisible(true);

      StringJoiner _name = new StringJoiner(" ");
      if (podcaster.getFirstName() != null) {
        _name.add(podcaster.getFirstName());
      }

      if (podcaster.getLastName() != null) {
        _name.add(podcaster.getLastName());
      }

      _name.add("(Podcaster)");

      name.setText(_name.toString());
    }

    if (connectedUser.isAdministrator()) {
      Administrator administrator = (Administrator) connectedUser.getUser();
      name.setVisible(true);

      StringJoiner _name = new StringJoiner(" ");
      if (administrator.getFirstName() != null) {
        _name.add(administrator.getFirstName());
      }

      if (administrator.getLastName() != null) {
        _name.add(administrator.getLastName());
      }

      _name.add("(Administrator)");

      name.setText(_name.toString());
    }

    security.setOnAction(
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

    history.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            try {
              AnchorPane historyPane =
                  FXMLLoader.load(getClass().getResource("/views/History.fxml"));
              pane.getChildren().clear();
              pane.getChildren().add(historyPane);
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

    flag.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            try {
              AnchorPane flagService = FXMLLoader.load(getClass().getResource("/views/Flag.fxml"));
              pane.getChildren().clear();
              pane.getChildren().add(flagService);
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        });
  }
}
