package com.druid.controllers;

import com.druid.errors.login.BannedUserException;
import com.druid.errors.login.InvalidCredentialsException;
import com.druid.errors.login.NoSuchUserException;
import com.druid.models.Administrator;
import com.druid.models.Podcaster;
import com.druid.models.User;
import com.druid.services.AdministratorService;
import com.druid.services.PodcasterService;
import com.druid.services.UserService;
import com.druid.utils.Clearable;
import com.druid.utils.ConnectedUser;
import com.druid.utils.QuickHistory;
import java.io.IOException;
import java.net.NoRouteToHostException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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

public class LoginController implements Initializable {
  private Stage stage;
  private UserService user_svc = new UserService();
  @FXML private Hyperlink forgotPassword;
  @FXML private Hyperlink signUp;
  @FXML private Button confirm;
  @FXML private Text errorAlert;
  @FXML private TextField username;
  @FXML private PasswordField password;

  private Optional<User> authUser() {
    User user = new User();
    user.setUsername(username.getText());
    user.setPassword(password.getText());

    try {
      return Optional.of(user_svc.authenticate(user));
    } catch (InvalidCredentialsException ex) {
      errorAlert.setOpacity(100);
      errorAlert.setText(ex.getMessage());
    } catch (BannedUserException ex) {
      errorAlert.setOpacity(100);
      errorAlert.setText(ex.getMessage());
    } catch (NoSuchUserException ex) {
      errorAlert.setOpacity(100);
      errorAlert.setText(ex.getMessage());
    }

    return Optional.empty();
  }

  private Optional<Administrator> retrieveMappedAdministrator(User user) {
    AdministratorService admin_svc = new AdministratorService();
    Administrator admin = new Administrator();
    admin.setID(user.getID());
    admin.setUsername(user.getUsername());
    admin.setEmail(user.getEmail());
    return admin_svc.fetchOne(admin);
  }

  private Optional<Podcaster> retrieveMappedPodcaster(User user) {
    PodcasterService podcaster_svc = new PodcasterService();
    Podcaster podcaster = new Podcaster();
    podcaster.setID(user.getID());
    podcaster.setUsername(user.getUsername());
    podcaster.setEmail(user.getEmail());
    return podcaster_svc.fetchOne(podcaster);
  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    password.setOnKeyPressed(Clearable.clear(password));
    username.setOnKeyPressed(Clearable.clear(username));

    forgotPassword.setOnAction(
        new EventHandler<ActionEvent>() {
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



    confirm.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            Optional<User> user = authUser();

            if (user.isPresent()) {
              Optional<Administrator> admin = retrieveMappedAdministrator(user.get());
              Optional<Podcaster> podcaster = retrieveMappedPodcaster(user.get());

              if (admin.isPresent()) {
                ConnectedUser.getInstance().setUser(admin.get());
              } else if (podcaster.isPresent()) {
                ConnectedUser.getInstance().setUser(podcaster.get());
              } else {
                ConnectedUser.getInstance().setUser(user.get());
              }

              QuickHistory.logAccountLogin(user.get());

              SceneSwitcher sceneController = new SceneSwitcher();
              try {
                sceneController.showHome(event);
              } catch (IOException e) {
                e.printStackTrace();
              }

            }
          }
        });

    signUp.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent actionEvent) {
            SceneSwitcher sceneController = new SceneSwitcher();
            try {
              sceneController.showAccountTypePicker(actionEvent);
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        });
  }
}
