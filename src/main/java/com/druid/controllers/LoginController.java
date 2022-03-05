package com.druid.controllers;

import com.druid.errors.login.BannedUserException;
import com.druid.errors.login.InvalidCredentialsException;
import com.druid.models.User;
import com.druid.services.UserService;
import com.druid.utils.Clearable;
import com.druid.utils.ConnectedUser;
import com.druid.utils.Debugger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private Stage stage;
    private UserService user_svc = new UserService();
    private User connectedUser = ConnectedUser.getInstance().getUser();

    @FXML
    private Hyperlink forgotPassword;
    @FXML
    private Hyperlink signUp;
    @FXML
    private Button confirm;
    @FXML
    private Text errorAlert;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        password.setOnKeyPressed(Clearable.clear(password));
        username.setOnKeyPressed(Clearable.clear(username));

        forgotPassword.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                SceneSwitcher sceneController = new SceneSwitcher();
                try {
                    sceneController.showForgotPassword(actionEvent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        confirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                connectedUser.setUsername(username.getText());
                connectedUser.setPassword(password.getText());
                try {
                    Optional<User> match = user_svc.authenticate(connectedUser);
                    if (match.isPresent()) {
                        connectedUser.setStatus(match.get().getStatus());
                        connectedUser.setEmail(match.get().getEmail());
                        connectedUser.setID(match.get().getID());
                        connectedUser.setAvatar(match.get().getAvatar());
                        Debugger.log("User (with ID=" + connectedUser.getID() + ") successfully logged in.");
                        SceneSwitcher sceneController = new SceneSwitcher();
                        try {
                            sceneController.showResolver(event);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (BannedUserException err) {
                    errorAlert.setOpacity(100);
                    errorAlert.setText(err.getMessage());
                } catch (InvalidCredentialsException err) {
                    errorAlert.setOpacity(100);
                    errorAlert.setText(err.getMessage());
                }
            }
        });

        signUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                SceneSwitcher sceneController = new SceneSwitcher();
                try {
                    sceneController.showRegister(actionEvent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
