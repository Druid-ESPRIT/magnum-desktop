package com.druid.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {
    private Scene scene;
    private Parent root;
    private Stage stage;

    public void showLogin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showRegisterPodcaster(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/RegisterPodcaster.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showAccountTypePicker(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/AccountTypePicker.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showRegisterUser(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/RegisterUser.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showForgotPassword(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/ForgotPassword.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showResetPassword(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/ResetPassword.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showTokenInput(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/TokenInput.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
