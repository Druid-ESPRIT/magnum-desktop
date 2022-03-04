package com.druid.controllers;

import com.druid.errors.login.InvalidCredentialsException;
import com.druid.errors.token.ConsumedTokenException;
import com.druid.errors.token.ExpiredTokenException;
import com.druid.errors.token.InvalidTokenException;
import com.druid.models.Token;
import com.druid.models.User;
import com.druid.services.UserService;
import com.druid.utils.Clearable;
import com.druid.utils.ConnectedUser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ResetPasswordController implements Initializable {
    private Stage stage;
    private UserService user_svc = new UserService();
    private User connectedUser = ConnectedUser.getInstance().getUser();

    @FXML
    private Button confirm;
    @FXML
    private Button cancel;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField passwordConfirm;
    @FXML
    private Text passwordAlert;
    @FXML
    private Text passwordConfirmAlert;

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
        password.setOnKeyPressed(Clearable.clear(password));
        passwordConfirm.setOnKeyPressed(Clearable.clear(passwordConfirm));

        cancel.setOnAction(new EventHandler<ActionEvent>() {
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

        password.focusedProperty().addListener((_arg, input, output) -> {
            if (!output) {
                if (!password.getText().equals(passwordConfirm.getText())) {
                    alert(passwordConfirmAlert, "The password and its confirmation don't match.");
                    confirm.setDisable(true);
                    return;
                }

                if (password.getText().isEmpty()) {
                    alert(passwordAlert, "This field is required.");
                    confirm.setDisable(true);
                    return;
                }

                hideAlert(passwordConfirmAlert);
                confirm.setDisable(false);
            }
        });

        passwordConfirm.focusedProperty().addListener((_arg, input, output) -> {
            if (!output) {
                if (passwordConfirm.getText().isEmpty()) {
                    alert(passwordConfirmAlert, "This field is required.");
                    confirm.setDisable(true);
                    return;
                }

                hideAlert(passwordConfirmAlert);
                confirm.setDisable(false);
            }
        });

        confirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Optional<User> match = user_svc.fetchOne(connectedUser);

                if (match.isPresent()) {
                    match.get().setPassword(password.getText());
                    user_svc.update(match.get());
                    SceneSwitcher sceneController = new SceneSwitcher();
                    try {
                        sceneController.showLogin(event);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
