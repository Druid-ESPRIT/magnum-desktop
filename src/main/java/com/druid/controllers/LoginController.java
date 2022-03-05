package com.druid.controllers;

import com.druid.errors.login.BannedUserException;
import com.druid.errors.login.InvalidCredentialsException;
import com.druid.interfaces.IUser;
import com.druid.models.Administrator;
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

    private boolean authUser() {
        try {
            User user = new User();
            user.setUsername(username.getText());
            user.setPassword(password.getText());
            Optional<User> u_match = (Optional<User>) IUser.authenticate(user);

            if (u_match.isPresent()) {
                ConnectedUser connectedUser = ConnectedUser.getInstance(User.class);
                connectedUser.setUser(u_match.get());
                Debugger.log(connectedUser.getUser());
                return true;
            }

        } catch (InvalidCredentialsException err) {
            errorAlert.setOpacity(100);
            errorAlert.setText(err.getMessage());
        } catch (BannedUserException err) {
            errorAlert.setOpacity(100);
            errorAlert.setText(err.getMessage());
        }

        return false;
    }

    private boolean authAdmin() {
        try {
            Administrator admin = new Administrator();
            admin.setUsername(username.getText());
            admin.setPassword(password.getText());
            Optional<Administrator> a_match = (Optional<Administrator>) IUser.authenticate(admin);

            if (a_match.isPresent()) {
                ConnectedUser connectedUser = ConnectedUser.getInstance(Administrator.class);
                connectedUser.setUser(a_match.get());
                Debugger.log(connectedUser.getUser());
                return true;
            }

        } catch (InvalidCredentialsException err) {
            errorAlert.setOpacity(100);
            errorAlert.setText(err.getMessage());
        } catch (BannedUserException err) {
            errorAlert.setOpacity(100);
            errorAlert.setText(err.getMessage());
        }

        return false;
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
                if (authAdmin()) {
                    SceneSwitcher sceneController = new SceneSwitcher();
                    try {
                        sceneController.showMain(event);
                        return;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (authUser()) {
                    SceneSwitcher sceneController = new SceneSwitcher();
                    try {
                        sceneController.showMain(event);
                        return;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
