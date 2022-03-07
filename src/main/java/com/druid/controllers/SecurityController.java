package com.druid.controllers;

import com.druid.enums.UserStatus;
import com.druid.errors.register.PasswordCheckException;
import com.druid.models.User;
import com.druid.services.UserService;
import com.druid.utils.ConnectedUser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SecurityController implements Initializable {
    UserService user_svc = new UserService();
    ConnectedUser connectedUser = ConnectedUser.getInstance();

    @FXML
    private AnchorPane pane;
    @FXML
    private Text passwordAlert;
    @FXML
    private Text passwordConfirmAlert;
    @FXML
    private PasswordField passwordConfirm;
    @FXML
    private PasswordField password;
    @FXML
    private Button save;
    @FXML
    private Hyperlink disableAccount;
    @FXML
    private Hyperlink back;

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

    private void disableAccountListener() {
        disableAccount.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                User user = connectedUser.getUser();
                user.setStatus(UserStatus.DISABLED);
                user_svc.update(user);

                connectedUser.disconnect();
                SceneSwitcher sceneController = new SceneSwitcher();
                try {
                    sceneController.showLogin(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        disableAccountListener();
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

                        try {
                            AnchorPane profilePane =
                                    FXMLLoader.load(getClass().getResource("/views/Profile.fxml"));
                            pane.getChildren().clear();
                            pane.getChildren().add(profilePane);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

        back.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            AnchorPane profilePane =
                                    FXMLLoader.load(getClass().getResource("/views/Profile.fxml"));
                            pane.getChildren().clear();
                            pane.getChildren().add(profilePane);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
