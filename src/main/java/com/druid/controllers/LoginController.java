package com.druid.controllers;

import com.druid.services.UserService;
import com.druid.utils.Clearable;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginController implements Initializable {
  private Stage stage;

  private UserService user_svc = new UserService();

  @FXML private Hyperlink forgotPassword;

  @FXML private TextField username;

  @FXML private PasswordField password;

  public Stage getStage() {
    return stage;
  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    password.setOnKeyPressed(Clearable.clear(password));
    username.setOnKeyPressed(Clearable.clear(username));

    forgotPassword.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent actionEvent) {
            SceneSwitcher sceneController = new SceneSwitcher();
            try {
              sceneController.showForgotPassword(actionEvent);
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        });
  }

  public void login(MouseEvent mouseEvent) {}
}
