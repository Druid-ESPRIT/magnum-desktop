package com.druid.controllers;

import com.druid.models.User;
import com.druid.services.TokenService;
import com.druid.services.UserService;
import com.druid.utils.Clearable;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ForgotPasswordController implements Initializable {
  TokenService token_svc = new TokenService();
  UserService user_svc = new UserService();

  @FXML private TextField username;

  @FXML private Button send;

  @FXML private Button cancel;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    username.setOnKeyPressed(Clearable.clear(username));

    send.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent actionEvent) {
            User user = new User();
            user.setUsername(username.getText());
            Optional<User> match = user_svc.fetchOne(user);

            if (match.isPresent()) {
              token_svc.generate(match.get());
            }

            SceneSwitcher sceneController = new SceneSwitcher();
            try {
              sceneController.showTokenInput(actionEvent);
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        });

    cancel.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent actionEvent) {
            SceneSwitcher sceneController = new SceneSwitcher();
            try {
              sceneController.showLogin(actionEvent);
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        });
  }
}
