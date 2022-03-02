package com.druid;

import com.druid.models.Offer;
import com.druid.services.OfferService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;


public class offerEditController {

    @FXML
    private AnchorPane anchorPane2;

    @FXML
    private TextArea tfdesc;

    @FXML
    private TextField tfprice;

    @FXML
    private Label filechosen;

    @FXML
    private Button addbutton;
    @FXML
    private Label getid;

    OfferService so = new OfferService();

    @FXML
    void btnEditOfferClicked(ActionEvent event) {
        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Edit and offer");
        alert1.setHeaderText("Do you really want to delete your offer ?");
        alert1.setContentText("Offer will be deleted");
        Optional<ButtonType> option = alert1.showAndWait();
        if (option.isPresent() && option.get() == ButtonType.OK)
        {
        Offer o = new Offer(10,Float.parseFloat(tfprice.getText()),tfdesc.getText(),filechosen.getText());
        so.updateOffer(o,Integer.parseInt(getid.getText()));
        anchorPane2.getChildren().clear();}



    }

    @FXML
    void DeleteOffer(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.showAndWait();
        so.deleteOffer(Integer.parseInt(getid.getText()));
        anchorPane2.getChildren().clear();

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
        Stage stage = (Stage)anchorPane2.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if(file != null){
            filechosen.setText(file.getName());
        }
    }
    public void selectedOffer(Offer offer) {
        String id = String.valueOf(offer.getId());
        getid.setText(id);
        tfdesc.setText(offer.getDescription());
        tfprice.setText(String.valueOf(offer.getPrice()));
        filechosen.setText(offer.getImage());
    }

}
