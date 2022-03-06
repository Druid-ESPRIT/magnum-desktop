package com.druid.controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MenuController implements Initializable {
    @FXML
    private Button pageusercomment;
    @FXML
    private void useracceuil(ActionEvent event) throws IOException {

            Stage stage = (Stage)pageusercomment.getScene().getWindow();
            stage.close();
            Stage Stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/views/userAcceuil.fxml"));
            Stage.setScene(new Scene(root));
            Stage.show();}


    public void initialize(URL location, ResourceBundle resources) {

    }
}




