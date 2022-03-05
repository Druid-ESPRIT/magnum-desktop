package com.druid.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OfferWindow extends Application {

    public static final String CURRENCY = "$";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(
                "/views/offermanager.fxml"));
        primaryStage.setTitle("Create offers");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
}
