package com.druid.controllers;

import com.druid.interfaces.MyListener;
import com.druid.models.Offer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.File;

public class ItemController {

    @FXML
    private ImageView imgView;

    @FXML
    private Label lbdesc;

    @FXML
    private Label lbprice;
    @FXML
    private AnchorPane anchorPane;


    private Offer offer;
    private MyListener myListener;


    @FXML
    void selectedOffer(MouseEvent event) {
        myListener.onClickListener(offer);

    }

    public void setData(Offer offer, MyListener myListener) {
        this.offer = offer;
        this.myListener = myListener;

        File file = new File("C:/Users/asus/Desktop/Git/magnum-desktop/src/main/resources/img/" + offer.getImage());
        Image image = new Image(file.toURI().toString());
        lbdesc.setText(offer.getDescription());
        lbprice.setText(OfferWindow.CURRENCY + offer.getPrice());
        imgView.setImage(image);
    }
}

