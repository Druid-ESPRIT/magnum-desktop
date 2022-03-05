package com.druid;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

   /*public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/acceuil.fxml"));
        LoginController loginController = new LoginController();
        loginController.setStage(stage);
        stage.setScene(new Scene(root, 300, 200));
        stage.setTitle("Welcome to Magnum");
        stage.show();
    }*/
   private Stage stage;
    private Parent parent;
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.stage = primaryStage;
        parent = FXMLLoader.load(getClass().getResource("/views/acceuil.fxml"));
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }
  /* public static void main(String[] args) {
      launch(args);
  }

  @Override
  public void start(Stage stage) throws IOException {
      Parent root = FXMLLoader.load(getClass().getResource("/views/acceuil.fxml"));
      LoginController loginController = new LoginController();
      loginController.setStage(stage);
      stage.setScene(new Scene(root, 300, 200));
      stage.setTitle("Welcome to Magnum");
      stage.show();
  }*/


  /** @param args the command line arguments */
  public static void main(String[] args) {
    launch(args);
  }
}
