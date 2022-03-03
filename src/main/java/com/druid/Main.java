package com.druid;

import com.druid.controllers.LoginController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
    LoginController loginController = new LoginController();
    loginController.setStage(stage);
    stage.setScene(new Scene(root, 300, 200));
    stage.setTitle("Welcome to Magnum");
    stage.show();
  }
}
