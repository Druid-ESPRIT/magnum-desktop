package com.druid.controllers;

import com.druid.errors.login.InvalidCredentialsException;
import com.druid.errors.token.ConsumedTokenException;
import com.druid.errors.token.ExpiredTokenException;
import com.druid.errors.token.InvalidTokenException;
import com.druid.models.Token;
import com.druid.models.User;
import com.druid.services.TokenService;
import com.druid.utils.Clearable;
import com.druid.utils.ConnectedUser;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TokenInputController implements Initializable {
  private TokenService token_svc = new TokenService();
  private User connectedUser = ConnectedUser.getInstance().getUser();
  private Stage stage;
  @FXML private TextField token;
  @FXML private Text tokenAlert;
  @FXML private Button confirm;
  @FXML private Button cancel;

  public Stage getStage() {
    return stage;
  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }

  private void alert(Text field, String content) {
    field.setOpacity(100);
    field.setText(content);
  }

  private void hideAlert(Text field) {
    field.setOpacity(0);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    token.setOnKeyPressed(Clearable.clear(token));

    confirm.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            try {
              // Validate the token.
              Token temp_token = new Token();
              temp_token.setToken(token.getText());
              token_svc.validate(temp_token, connectedUser);

              SceneSwitcher sceneController = new SceneSwitcher();
              try {
                sceneController.showResetPassword(event);
              } catch (IOException e) {
                e.printStackTrace();
              }
            } catch (InvalidTokenException err) {
              alert(tokenAlert, err.getMessage());
            } catch (ExpiredTokenException err) {
              alert(tokenAlert, err.getMessage());
            } catch (ConsumedTokenException err) {
              alert(tokenAlert, err.getMessage());
            } catch (InvalidCredentialsException err) {
              alert(tokenAlert, err.getMessage());
            }
          }
        });

    cancel.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent actionEvent) {
            connectedUser = null;
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
