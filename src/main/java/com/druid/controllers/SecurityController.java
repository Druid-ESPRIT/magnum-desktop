package com.druid.controllers;

import com.druid.errors.register.PasswordCheckException;
import com.druid.models.User;
import com.druid.services.UserService;
import com.druid.utils.ConnectedUser;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;

public class SecurityController implements Initializable {
  UserService user_svc = new UserService();
  ConnectedUser connectedUser = ConnectedUser.getInstance();

  @FXML private Text passwordAlert;
  @FXML private Text passwordConfirmAlert;
  @FXML private PasswordField passwordConfirm;
  @FXML private PasswordField password;
  @FXML private Button save;

  public void verifyPassword() throws PasswordCheckException {
    if (!password.getText().equals(passwordConfirm.getText())) {
      throw new PasswordCheckException("The password and its confirmation don't match.");
    }
  }

  private void alert(Text field, String content) {
    field.setOpacity(100);
    field.setText(content);
  }

  private void hideAlert(Text field) {
    if (field.getOpacity() == 100) {
      field.setOpacity(0);
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    password
        .focusedProperty()
        .addListener(
            (_arg, input, output) -> {
              if (!output) { // When we lose focus
                if (password.getText().isEmpty()) {
                  alert(passwordAlert, "This field is required.");
                }

                try {
                  verifyPassword();
                  hideAlert(passwordConfirmAlert);
                } catch (PasswordCheckException err) {
                  alert(passwordConfirmAlert, err.getMessage());
                }
              }
            });

    passwordConfirm
        .focusedProperty()
        .addListener(
            (_arg, input, output) -> {
              if (!output) {
                if (passwordConfirm.getText().isEmpty()) {
                  alert(passwordConfirmAlert, "This field is required.");
                } else {
                  hideAlert(passwordConfirmAlert);
                }
              }
            });

    save.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent actionEvent) {
            // Password verification
            try {
              verifyPassword();
              hideAlert(passwordConfirmAlert);
            } catch (PasswordCheckException err) {
              alert(passwordConfirmAlert, err.getMessage());
              return;
            }

            User user = connectedUser.getUser();
            user.setPassword(password.getText());
            user_svc.update(user);
          }
        });
  }
}
