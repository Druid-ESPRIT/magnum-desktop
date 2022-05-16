package com.druid.controllers;

import com.druid.enums.FlagOffense;
import com.druid.models.Flag;
import com.druid.models.User;
import com.druid.services.FlagService;
import com.druid.services.UserService;
import com.druid.utils.ConnectedUser;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class FlagController implements Initializable {
  FlagService flag_svc = new FlagService();
  UserService user_svc = new UserService();
  ConnectedUser connectedUser = ConnectedUser.getInstance();

  @FXML private AnchorPane pane;
  @FXML private TextField username;
  @FXML private Text usernameAlert;
  @FXML private TextArea description;
  @FXML private Button send;
  @FXML private Button cancel;
  @FXML private RadioButton harassment;
  @FXML private RadioButton spam;
  @FXML private RadioButton violence;
  @FXML private Hyperlink back;

  private void alert(Text field, String content) {
    field.setVisible(true);
    field.setText(content);
  }

  private void hideAlert(Text field) {
    if (field.getOpacity() == 100) {
      field.setVisible(false);
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    harassment.setSelected(true);

    harassment
        .selectedProperty()
        .addListener(
            new ChangeListener<Boolean>() {
              @Override
              public void changed(
                  ObservableValue<? extends Boolean> observableValue,
                  Boolean oldValue,
                  Boolean newValue) {
                if (newValue) {
                  spam.setSelected(false);
                  violence.setSelected(false);
                }
              }
            });

    spam.selectedProperty()
        .addListener(
            new ChangeListener<Boolean>() {
              @Override
              public void changed(
                  ObservableValue<? extends Boolean> observableValue,
                  Boolean oldValue,
                  Boolean newValue) {
                if (newValue) {
                  harassment.setSelected(false);
                  violence.setSelected(false);
                }
              }
            });

    violence
        .selectedProperty()
        .addListener(
            new ChangeListener<Boolean>() {
              @Override
              public void changed(
                  ObservableValue<? extends Boolean> observableValue,
                  Boolean oldValue,
                  Boolean newValue) {
                if (newValue) {
                  spam.setSelected(false);
                  harassment.setSelected(false);
                }
              }
            });

    send.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            User flagged = new User();
            flagged.setUsername(username.getText());

            Optional<User> match = user_svc.fetchOne(flagged);

            if (!match.isPresent()) {
              alert(usernameAlert, "This user doesn't exist.");
              return;
            }

            Flag flag = new Flag();
            flag.setFlaggerID(connectedUser.getUser().getID());
            flag.setFlaggedID(match.get().getID());
            flag.setDescription(description.getText());
            flag.setTime(new Timestamp(new Date().getTime()));

            if (harassment.isSelected()) {
              flag.setOffense(FlagOffense.HARASSMENT);
            } else if (spam.isSelected()) {
              flag.setOffense(FlagOffense.SPAM);
            } else if (violence.isSelected()) {
              flag.setOffense(FlagOffense.VIOLENCE);
            }

            flag_svc.flag(flag);

            try {
              AnchorPane profilePane =
                  FXMLLoader.load(getClass().getResource("/views/Profile.fxml"));
              pane.getChildren().clear();
              pane.getChildren().add(profilePane);
            } catch (IOException e) {
              e.printStackTrace();
            }

            return;
          }
        });

    cancel.setOnAction(
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
