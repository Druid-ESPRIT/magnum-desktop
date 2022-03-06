package com.druid.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;

import javax.xml.soap.Text;
import java.net.URL;
import java.util.ResourceBundle;

public class SecurityController implements Initializable {

    @FXML
    private Text passwordAlert;
    @FXML
    private Text confirmPasswordAlert;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private PasswordField password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

}
