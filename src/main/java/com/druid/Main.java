package com.druid;

import com.druid.enums.UserStatus;
import com.druid.models.Podcaster;
import com.druid.models.User;
import com.druid.services.PodcasterService;
import com.druid.services.UserService;
import com.druid.utils.Debugger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));
    stage.setTitle("Magnum");
    stage.setScene(new Scene(root, 300, 300));
    stage.show();
  }
}
