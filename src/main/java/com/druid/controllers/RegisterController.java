package com.druid.controllers;

import com.druid.enums.UserStatus;
import com.druid.errors.register.EmailTakenException;
import com.druid.errors.register.UsernameTakenException;
import com.druid.models.User;
import com.druid.services.UserService;
import com.druid.utils.Clearable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    public Stage stage;

    private UserService user_svc = new UserService();
    @FXML
    private TextField email;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField passwordConfirm;
    @FXML
    private Button register;
    @FXML
    private Button cancel;
    @FXML
    private Text emailAlert;
    @FXML
    private Text usernameAlert;
    @FXML
    private Text passwordAlert;
    @FXML
    private Text passwordConfirmAlert;

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

    public boolean isAlphaNumeric(String text) {
        return text.matches("^[a-zA-Z0-9]*$");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        email.setOnKeyPressed(Clearable.clear(email));
        username.setOnKeyPressed(Clearable.clear(username));
        password.setOnKeyPressed(Clearable.clear(password));
        passwordConfirm.setOnKeyPressed(Clearable.clear(passwordConfirm));

        register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!password.getText().equals(passwordConfirm.getText())) {
                    alert(passwordConfirmAlert, "The password and its confirmation don't match.");
                } else {
                    hideAlert(passwordConfirmAlert);
                }

                if (!isAlphaNumeric(username.getText())) {
                    alert(usernameAlert, "This field can only contain letters and digits.");
                }

                if (username.getText().isEmpty()) {
                    alert(usernameAlert, "This field is required.");
                } else if (username.getText().length() > 40) {
                    alert(usernameAlert, "Your username can't be longer than 40 characters.");
                } else {
                    hideAlert(usernameAlert);
                }

                try {
                    // Create the user.
                    User user = new User();
                    user.setEmail(email.getText().trim());
                    user.setUsername(username.getText().trim());
                    user.setPassword(password.getText());
                    user.setStatus(UserStatus.ACTIVE);
                    user_svc.add(user);
                } catch (EmailTakenException err) {
                    alert(emailAlert, err.getMessage());
                    return;
                } catch (UsernameTakenException err) {
                    alert(usernameAlert, err.getMessage());
                    return;
                }

                // Switch to the login scene.
                SceneSwitcher sceneController = new SceneSwitcher();
                try {
                    sceneController.showLogin(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneSwitcher sceneController = new SceneSwitcher();
                try {
                    sceneController.showLogin(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        passwordConfirm.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    register.fire();
                }
            }
        });

        email.focusedProperty().addListener((_arg, input, output) -> {
            if (!output) { // When we lose focus
                try {
                    new InternetAddress(email.getText()).validate();
                    hideAlert(emailAlert);
                    register.setDisable(false);
                } catch (AddressException ex) {
                    alert(emailAlert, "This is not a valid email.");
                    register.setDisable(true);
                }
            }
        });

        username.focusedProperty().addListener((_arg, input, output) -> {
            if (!output) { // When we lose focus
                String text = username.getText();
                if (!isAlphaNumeric(text)) {
                    alert(usernameAlert, "Your username can only contain letters and digits.");
                    register.setDisable(true);
                    return;
                }

                if (text.isEmpty()) {
                    alert(usernameAlert, "This field is required.");
                    register.setDisable(true);
                    return;
                }

                hideAlert(usernameAlert);
                register.setDisable(false);
            }
        });

        password.focusedProperty().addListener((_arg, input, output) -> {
            if (!output) { // When we lose focus
                if (!password.getText().equals(passwordConfirm.getText())) {
                    alert(passwordConfirmAlert, "The password and its confirmation don't match.");
                    register.setDisable(true);
                    return;
                }

                if (password.getText().isEmpty()) {
                    alert(passwordAlert, "This field is required.");
                    register.setDisable(true);
                    return;
                }

                hideAlert(passwordAlert);
                register.setDisable(false);
            }
        });

        passwordConfirm.focusedProperty().addListener((_arg, input, output) -> {
            if (!output) {
                if (passwordConfirm.getText().isEmpty()) {
                    alert(passwordConfirmAlert, "This field is required.");
                    register.setDisable(true);
                    return;
                }

                hideAlert(passwordConfirmAlert);
                register.setDisable(false);
            }
        });
    }
}
