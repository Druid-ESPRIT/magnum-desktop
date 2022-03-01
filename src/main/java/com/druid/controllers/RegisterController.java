package com.druid.controllers;

import com.druid.enums.UserStatus;
import com.druid.models.User;
import com.druid.services.UserService;
import com.druid.utils.Debugger;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class RegisterController implements Initializable {
  UserService user_svc = new UserService();

  @FXML private TextField email;
  @FXML private TextField username;
  @FXML private TextField password;
  @FXML private TextField passwordConfirm;

  @FXML private Text emailAlert;
  @FXML private Text usernameAlert;
  @FXML private Text passwordAlert;
  @FXML private Text passwordConfirmAlert;

  private void alert(Text field, String contentText) {
    field.setOpacity(100);
    field.setText(contentText);
  }

  private void hideAlert(Text field) {
    field.setOpacity(0);
  }

  @FXML
  private void register(ActionEvent event) {
    if (email.getText().length() == 0) {
      alert(emailAlert, "The email field is required.");
    } else {
      hideAlert(emailAlert);
    }

    if (username.getText().length() == 0) {
      alert(usernameAlert, "The username field is required.");
    } else {
      hideAlert(usernameAlert);
    }

    if (password.getText().length() == 0) {
      alert(passwordAlert, "The password field is required.");
    } else {
      hideAlert(passwordAlert);
    }

    if (passwordConfirm.getText().length() == 0) {
      alert(passwordConfirmAlert, "The password confirmation field is required.");
    } else {
      hideAlert(passwordConfirmAlert);
    }

    // Verify that the email is correct.
    try {
      new InternetAddress(email.getText()).validate();
    } catch (AddressException ex) {
      alert(emailAlert, "This email is not valid.");
    } finally {
      hideAlert(emailAlert);
    }

    // Verify that the password and its confirmation are identical.
    if (!password.getText().equals(passwordConfirm.getText())) {
      alert(passwordConfirmAlert, "The password and its confirmation don't match.");
      return;
    } else {
      hideAlert(passwordConfirmAlert);
    }

    User user = new User();
    user.setEmail(email.getText());
    user.setUsername(username.getText());
    user.setPassword(password.getText());
    user.setStatus(UserStatus.ACTIVE);
    Debugger.log(user);

    user_svc.add(user);
    return;
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    return;
  }
}
