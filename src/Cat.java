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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author user
 */
public class Cat implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private Button button;
    @FXML
    private ImageView Logo;
    Image Mag =new Image("3.png");
    
         @FXML
    ChoiceBox<Categorie> Tcat= new ChoiceBox<Categorie>();
         
    //private final String[] Cat = {"salem","hi"}; 
         
         Connection cnx =MaConnexion.getInstance().getCnx(); 
    @FXML
     void handleButtonAction(ActionEvent event) throws IOException {
        if(!"".equals(Tcat.getValue().toString())){
            
           Categorie sa = Tcat.getValue();
            System.out.println(sa);
        
           
            
        
         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DetTicket.fxml"));
Parent root1 = (Parent) fxmlLoader.load();

Stage stage = new Stage();
       
stage.setScene(new Scene(root1));
stage.show();
     }   
    }
    
    
   
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
    Tcat.getItems().add(new Categorie("1","Feature Request"));
    Tcat.getItems().add(new Categorie("2","Missing Podcast"));
    Tcat.getItems().add(new Categorie("3","Failed Payment"));
    Tcat.getItems().add(new Categorie("4","Account Recovery"));
    Tcat.getItems().add(new Categorie("5","Other"));
        
        Logo.setImage(Mag);
    }  
    
    
    

    
}
