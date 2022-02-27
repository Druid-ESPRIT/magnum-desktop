package com.druid;

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
    Parent root = FXMLLoader.load(getClass().getResource("/register.fxml"));
    Scene scene = new Scene(root, 300, 300);
    String css = this.getClass().getResource("/general.css").toExternalForm();

    stage.setTitle("Magnum");
    scene.getStylesheets().add(css);
    stage.setScene(scene);
    stage.show();
  }
}
