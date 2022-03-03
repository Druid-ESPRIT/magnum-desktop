package com.druid.controllers;

import com.druid.utils.Clearable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TokenInputController implements Initializable {
    @FXML
    private TextField username;

    @FXML
    private Button confirm;

    @FXML
    private Button cancel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        username.setOnKeyPressed(Clearable.clear(username));

        confirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                SceneSwitcher sceneController = new SceneSwitcher();
                try {
                    sceneController.showLogin(actionEvent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
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
