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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author user
 */
public class ChoixController implements Initializable {

    @FXML
    TextField uu;

    @FXML
    private Button button;
    @FXML
    private Label label;
    @FXML
    private ImageView Logo;
    private ImageView Logo1;
    Image Mag = new Image("3.png");

    @FXML
    ChoiceBox<Categorie> Tcat1 = new ChoiceBox<Categorie>();

    Connection cnx = MaConnexion.getInstance().getCnx();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        Tcat1.getItems().add(new Categorie("1","Feature Request"));
        Tcat1.getItems().add(new Categorie("2","Missing Podcast"));
        Tcat1.getItems().add(new Categorie("3","Failed Payment"));
        Tcat1.getItems().add(new Categorie("4","Account Recovery"));
        Tcat1.getItems().add(new Categorie("5","Other"));

        Logo.setImage(Mag);
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void handleButtonAction(ActionEvent event) throws IOException {
        if (!"".equals(Tcat1.getValue().toString())) {

            //Categorie sa = new Categorie(Tcat1.getValue().getCat(),Tcat1.getValue().getIcat());
            System.out.println(Tcat1.getValue().getIcat());
            //String s = Tcat1.getValue().toString();
            String CS =Tcat1.getValue().toString();
            String CI =Tcat1.getValue().getIcat().toString();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DetTicket.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            
            DetTicketController DetTicketController = fxmlLoader.getController();
            DetTicketController.Useed(CI,CS);
            Stage stage = new Stage();
            stage.setTitle(CS);
            stage.setScene(new Scene(root1));
            stage.show();
        }

    }

}
