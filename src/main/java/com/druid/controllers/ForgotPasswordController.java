package com.druid.controllers;

import com.druid.errors.login.InvalidCredentialsException;
import com.druid.errors.token.TokenTimeoutException;
import com.druid.models.User;
import com.druid.services.TokenService;
import com.druid.services.UserService;
import com.druid.utils.Clearable;
import com.druid.utils.ConnectedUser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ForgotPasswordController implements Initializable {
    TokenService token_svc = new TokenService();
    UserService user_svc = new UserService();
    private User connectedUser = ConnectedUser.getInstance().getUser();

    @FXML
    private TextField username;
    @FXML
    private Text usernameAlert;
    @FXML
    private Button send;
    @FXML
    private Button cancel;
    @FXML
    private Hyperlink tokenAlreadyAcquired;

    private void alert(Text field, String content) {
        field.setOpacity(100);
        field.setText(content);
    }

    private void hideAlert(Text field) {
        field.setOpacity(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setOnKeyPressed(Clearable.clear(username));

        send.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        User user = new User();
                        user.setUsername(username.getText());

                        connectedUser.setUsername(user.getUsername());
                        try {
                            // If a token has been generated with no issues,
                            // redirect the user to the token input view.
                            token_svc.generate(connectedUser);
                            SceneSwitcher sceneController = new SceneSwitcher();
                            try {
                                sceneController.showTokenInput(actionEvent);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } catch (InvalidCredentialsException err) {
                            alert(usernameAlert, err.getMessage());
                        } catch (TokenTimeoutException err) {
                            alert(usernameAlert, err.getMessage());
                        }
                    }
                });

        tokenAlreadyAcquired.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        User user = new User();
                        user.setUsername(username.getText());
                        connectedUser.setUsername(user.getUsername());
                        Optional<User> match = user_svc.fetchOne(connectedUser);

                        if (!match.isPresent()) {
                            alert(usernameAlert, "We couldn't find anyone with that username.");
                            return;
                        }

                        // Redirect the user to the token input view.
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
