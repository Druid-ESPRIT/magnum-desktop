package com.druid.controllers;

import com.druid.enums.SubscriptionStatus;
import com.druid.models.Offer;
import com.druid.models.Subscription;
import com.druid.services.OfferService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;

public class SubscriptionListController {

    @FXML
    private AnchorPane anchorPaneSb;

    @FXML
    private VBox myVbox;
    @FXML
    private Label lbSdate;

    @FXML
    private Label lbEdate;

    @FXML
    private Label status;
    private OfferService os = new OfferService();
    Offer selected = os.findOffer(96);

    public void setData(Subscription subscription) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/views/item.fxml"));

            AnchorPane anchorPane = fxmlLoader.load();
            ItemController itemController = fxmlLoader.getController();
            itemController.setDataImg(selected);
            myVbox.getChildren().add(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }

        status.setText(subscription.getStatus().toString());
        lbSdate.setText(String.valueOf(subscription.getStart_date()));
        lbEdate.setText(String.valueOf(subscription.getExpire_date()));
    }

}
