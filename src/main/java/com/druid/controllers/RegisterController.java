package com.druid.controllers;

import com.druid.services.UserService;
import com.druid.utils.Clearable;
import com.druid.utils.Debugger;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class RegisterController implements Initializable {
  public Stage stage;

  private UserService user_svc = new UserService();
  @FXML private TextField email;
  @FXML private TextField username;
  @FXML private PasswordField password;
  @FXML private PasswordField passwordConfirm;
  @FXML private Button registerButton;
  @FXML private Text emailAlert;
  @FXML private Text usernameAlert;
  @FXML private Text passwordAlert;
  @FXML private Text passwordConfirmAlert;

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

  private void register() {
    if (!password.getText().equals(passwordConfirm.getText())) {
      alert(passwordConfirmAlert, "The password and its confirmation don't match.");
    } else {
      hideAlert(passwordConfirmAlert);
    }

    if (!isAlphaNumeric(username.getText())) {
      alert(usernameAlert, "This field can only contain letters and digits.");
    } else if (username.getText().length() > 40) {
      alert(usernameAlert, "Your username can't be longer than 40 characters.");
    } else if (username.getText().isEmpty()) {
      alert(usernameAlert, "This field is required");
    } else {
      hideAlert(usernameAlert);
    }

    //        User user = new User();
    //        user.setEmail(email.getText().trim());
    //        user.setUsername(username.getText().trim());
    //        user.setPassword(password.getText());
    //        user.setStatus(UserStatus.ACTIVE);
    //        user_svc.add(user);
    Debugger.log("Looks alright!");
  }

  public boolean isAlphaNumeric(String text) {
    return text.matches("^[a-zA-Z0-9]*$");
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    email.setOnKeyPressed(Clearable.clear(email));
    username.setOnKeyPressed(Clearable.clear(username));
    password.setOnKeyPressed(Clearable.clear(password));
    passwordConfirm.setOnKeyPressed(Clearable.clear(passwordConfirm));

    registerButton.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            register();
          }
        });

    passwordConfirm.setOnKeyPressed(
        new EventHandler<KeyEvent>() {
          @Override
          public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.ENTER) {
              register();
            }
          }
        });

    email
        .focusedProperty()
        .addListener(
            (_arg, input, output) -> {
              if (!output) { // When we lose focus
                try {
                  new InternetAddress(email.getText()).validate();
                  hideAlert(emailAlert);
                  registerButton.setDisable(false);
                } catch (AddressException ex) {
                  alert(emailAlert, "This is not a valid email.");
                  registerButton.setDisable(true);
                }
              }
            });

    username
        .focusedProperty()
        .addListener(
            (_arg, input, output) -> {
              if (!output) { // When we lose focus
                String text = username.getText();
                if (!isAlphaNumeric(text)) {
                  alert(usernameAlert, "Your username can only contain letters and digits.");
                  registerButton.setDisable(true);
                  return;
                }

                if (text.isEmpty()) {
                  alert(usernameAlert, "This field is required.");
                  registerButton.setDisable(true);
                  return;
                }

                hideAlert(usernameAlert);
                registerButton.setDisable(false);
              }
            });

    password
        .focusedProperty()
        .addListener(
            (_arg, input, output) -> {
              if (!output) { // When we lose focus
                if (!password.getText().equals(passwordConfirm.getText())) {
                  alert(passwordConfirmAlert, "The password and its confirmation don't match.");
                  registerButton.setDisable(true);
                  return;
                }

                if (password.getText().isEmpty()) {
                  alert(passwordAlert, "This field is required.");
                  registerButton.setDisable(true);
                  return;
                }

                hideAlert(passwordConfirmAlert);
                registerButton.setDisable(false);
              }
            });

    passwordConfirm
        .focusedProperty()
        .addListener(
            (_arg, input, output) -> {
              if (!output) {
                if (passwordConfirm.getText().isEmpty()) {
                  alert(passwordConfirmAlert, "This field is required.");
                  registerButton.setDisable(true);
                  return;
                }

                hideAlert(passwordConfirmAlert);
                registerButton.setDisable(false);
              }
            });
  }
}
