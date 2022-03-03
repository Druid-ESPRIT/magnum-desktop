package com.druid.Controllers;

import com.druid.models.Offer;
import com.druid.services.OfferService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


public class OfferController{

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextArea tfdesc;

    @FXML
    private TextField tfprice;

    @FXML
    private Label filechosen;

    @FXML
    private Button addbutton;


    @FXML
    void btnCreateOfferClicked(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.showAndWait();
        Offer o = new Offer(10,Float.parseFloat(tfprice.getText()),tfdesc.getText(),filechosen.getText());
        OfferService so = new OfferService();
        so.addOffer(o);


        Stage stage = (Stage) addbutton.getScene().getWindow();
        stage.close();







    }
    @FXML
    void fileselector(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        Stage stage = (Stage)anchorPane.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);



        if(file != null){
            filechosen.setText(file.getName());
        }
    }
}
