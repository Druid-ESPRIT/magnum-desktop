/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author user
 */
public class MainController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private Button button;
    Image Mag = new Image("3.png");

    //private final String[] Cat = {"salem","hi"}; 
    Connection cnx = MaConnexion.getInstance().getCnx();
    @FXML
    private Button button2;
    @FXML
    private Button Cons;

    @FXML
    void handleButtonAction(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Choix.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();

        Stage stage = new Stage();
stage.setTitle("Envoyer une Reclamation");

        stage.setScene(new Scene(root1));
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void button2(ActionEvent event) throws IOException {
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Resolver.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();

        Stage stage = new Stage();
stage.setTitle("Resolver");
        stage.setScene(new Scene(root1));
        stage.show();
        
    }

    @FXML
    private void Cons(ActionEvent event) throws IOException {
        
        
         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Consulter.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();

        Stage stage = new Stage();
        stage.setTitle("Consulter une Reclamation");
        stage.setScene(new Scene(root1));
        stage.show();
        
        
    }

}
