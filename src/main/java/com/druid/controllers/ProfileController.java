package com.druid.controllers;

import com.druid.models.Administrator;
import com.druid.models.Podcaster;
import com.druid.services.UserService;
import com.druid.utils.ConnectedUser;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ProfileController implements Initializable {
  ConnectedUser connectedUser = ConnectedUser.getInstance();

  @FXML private AnchorPane fileChooserPane;
  @FXML private AnchorPane pane;
  @FXML private Text name;
  @FXML private Hyperlink flag;
  @FXML private Text username;
  @FXML private Text email;
  @FXML private Hyperlink history;
  @FXML private Hyperlink security;

  @FXML
  void fileChooser() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    fileChooser
        .getExtensionFilters()
        .addAll(
            new FileChooser.ExtensionFilter("All Images", "*.*"),
            new FileChooser.ExtensionFilter("JPG", "*.jpg"),
            new FileChooser.ExtensionFilter("PNG", "*.png"));
    Stage stage = (Stage) fileChooserPane.getScene().getWindow();
    File file = fileChooser.showOpenDialog(stage);
    if (file != null) {
      UserService user_svc = new UserService();
      connectedUser.getUser().setAvatar(Paths.get(file.getPath()));
      user_svc.update(connectedUser.getUser());
    }
  }

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
