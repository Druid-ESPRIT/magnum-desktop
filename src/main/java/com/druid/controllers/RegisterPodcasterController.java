package com.druid.controllers;

import com.druid.enums.UserStatus;
import com.druid.errors.register.*;
import com.druid.models.Podcaster;
import com.druid.models.User;
import com.druid.services.PodcasterService;
import com.druid.services.UserService;
import com.druid.utils.Clearable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
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

public class RegisterPodcasterController implements Initializable {
    public Stage stage;

    private PodcasterService p_svc = new PodcasterService();
    @FXML
    private TextField email;
    @FXML
    private TextField username;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextArea biography;
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
        if (field.getOpacity() == 100) {
            field.setOpacity(0);
        }
    }

    public boolean isAlphaNumeric(String text) {
        return text.matches("^[a-z0-9]*$");
    }

    public void verifyEmail() throws AddressException {
        new InternetAddress(email.getText()).validate();
    }

    public void verifyUsername() throws UsernameLengthException, UsernameIllegalSymbols {
        if (username.getText().isEmpty()) {
            throw new UsernameLengthException("This field is required.");
        }

        if (username.getText().length() > 40) {
            throw new UsernameLengthException("Your username can't be longer than 40 characters.");
        }

        if (!isAlphaNumeric(username.getText())) {
            throw new UsernameIllegalSymbols("This field can only contain lowercase letters and digits.");
        }
    }

    public void verifyPassword() throws PasswordCheckException {
        if (!password.getText().equals(passwordConfirm.getText())) {
            throw new PasswordCheckException("The password and its confirmation don't match.");
        }
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
                // Email verification
                try {
                    verifyEmail();
                    hideAlert(emailAlert);
                } catch (AddressException e) {
                    alert(emailAlert, "This is not a valid email.");
                    return;
                }

                // Username verification
                try {
                    verifyUsername();
                    hideAlert(usernameAlert);
                } catch (UsernameLengthException err) {
                    alert(usernameAlert, err.getMessage());
                    return;
                } catch (UsernameIllegalSymbols err) {
                    alert(usernameAlert, err.getMessage());
                    return;
                }

                // Password verification
                try {
                    verifyPassword();
                    hideAlert(passwordConfirmAlert);
                } catch (PasswordCheckException err) {
                    alert(passwordConfirmAlert, err.getMessage());
                    return;
                }

                // Register the user
                try {
                    Podcaster podcaster = new Podcaster();
                    podcaster.setEmail(email.getText().trim());
                    podcaster.setFirstName(firstName.getText().trim());
                    podcaster.setLastName(lastName.getText().trim());
                    podcaster.setBiography(biography.getText().trim());
                    podcaster.setUsername(username.getText().trim());
                    podcaster.setPassword(password.getText());
                    podcaster.setStatus(UserStatus.ACTIVE);
                    p_svc.add(podcaster);
                } catch (EmailTakenException err) {
                    alert(emailAlert, err.getMessage());
                    return;
                } catch (UsernameTakenException err) {
                    alert(usernameAlert, err.getMessage());
                    return;
                }

                // Switch to the login scene
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
            if (!output) {
                try {
                    verifyEmail();
                    hideAlert(emailAlert);
                } catch (AddressException e) {
                    alert(emailAlert, "This is not a valid email.");
                }
            }
        });

        username.focusedProperty().addListener((_arg, input, output) -> {
            if (!output) {
                try {
                    verifyUsername();
                    hideAlert(usernameAlert);
                } catch (UsernameLengthException err) {
                    alert(usernameAlert, err.getMessage());
                } catch (UsernameIllegalSymbols err) {
                    alert(usernameAlert, err.getMessage());
                }

            }
        });

        password.focusedProperty().addListener((_arg, input, output) -> {
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

        passwordConfirm.focusedProperty().addListener((_arg, input, output) -> {
            if (!output) {
                if (passwordConfirm.getText().isEmpty()) {
                    alert(passwordConfirmAlert, "This field is required.");
                } else {
                    hideAlert(passwordConfirmAlert);
                }
            }
        });
    }
}
