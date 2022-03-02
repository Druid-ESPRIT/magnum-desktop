package com.druid.controllers;

import com.druid.enums.UserStatus;
import com.druid.interfaces.Clearable;
import com.druid.models.User;
import com.druid.services.UserService;

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
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class RegisterController implements Initializable, Clearable {
  UserService user_svc = new UserService();

  @FXML
  private TextField email;
  @FXML
  private TextField username;
  @FXML
  private PasswordField password;
  @FXML
  private PasswordField passwordConfirm;
  @FXML
  private Button registerButton;
  @FXML
  private Text emailAlert;
  @FXML
  private Text usernameAlert;
  @FXML
  private Text passwordAlert;
  @FXML
  private Text passwordConfirmAlert;

  private void alert(Text field, String contentText) {
    field.setOpacity(100);
    field.setText(contentText);
  }

  private void hideAlert(Text field) {
      field.setOpacity(0);
  }

  private boolean areFieldsInvalid() {
    try {
      new InternetAddress(email.getText()).validate();
    } catch (AddressException ex) {
      alert(emailAlert, "This is not a valid email.");
      return true;
    } finally {
      hideAlert(emailAlert);
    }

    if (!password.getText().equals(passwordConfirm.getText())) {
      alert(passwordConfirmAlert, "The password and its confirmation don't match.");
      return true;
    } else hideAlert(passwordConfirmAlert);

    if (!isAlphaNumeric(username.getText())) {
      alert(usernameAlert, "This field can only contain letters and digits.");
      return true;
    } else hideAlert(usernameAlert);

    if (username.getText().length() > 40) {
      alert(usernameAlert, "Your username can't be longer than 40 characters.");
      return true;
    } else hideAlert(usernameAlert);

    return false;
  }

  private boolean areFieldsEmpty() {
    if (email.getText().length() == 0) {
      alert(emailAlert, "This field is required.");
      return true;
    } else {
      hideAlert(emailAlert);
    }

    if (username.getText().length() == 0) {
      alert(usernameAlert, "This field is required.");
      return true;
    } else {
      hideAlert(usernameAlert);
    }

    if (password.getText().length() == 0) {
      alert(passwordAlert, "This field is required.");
      return true;
    } else {
      hideAlert(passwordAlert);
    }

    if (passwordConfirm.getText().length() == 0) {
      alert(passwordConfirmAlert, "This field is required.");
      return true;
    } else {
      hideAlert(passwordConfirmAlert);
    }

    return false;
  }

  private void register() {
    if (areFieldsEmpty()) {
      return;
    };

    if (areFieldsInvalid()) {
      return;
    };

    addUser();
  }

  public boolean isAlphaNumeric(String text){
    return text.matches("^[a-zA-Z0-9]*$");
  }

  private void addUser() {
    User user = new User();
    user.setEmail(email.getText());
    user.setUsername(username.getText());
    user.setPassword(password.getText());
    user.setStatus(UserStatus.ACTIVE);
    user_svc.add(user);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    registerButton.setOnMouseClicked(new EventHandler<MouseEvent>()
    {
      @Override
      public void handle(MouseEvent event) {
        register();
      }
    });

    passwordConfirm.setOnKeyPressed(new EventHandler<KeyEvent>()
    {
      @Override
      public void handle(KeyEvent event) {
      if (event.getCode() == KeyCode.ENTER) {
          register();
        }
      }
    });

    email.setOnKeyPressed(Clearable.clear(email));
    username.setOnKeyPressed(Clearable.clear(username));
    password.setOnKeyPressed(Clearable.clear(password));
    passwordConfirm.setOnKeyPressed(Clearable.clear(passwordConfirm));
  }
}
