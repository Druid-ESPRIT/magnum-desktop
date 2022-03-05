package com.druid.Controllers;

import com.druid.interfaces.MyListener;
import com.druid.models.Offer;
import com.druid.models.Subscription;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;

public class SubscriptionListController {

    @FXML
    private AnchorPane anchorPaneSb;

    @FXML
    private ImageView imgV;

    @FXML
    private Label lbSdate;

    @FXML
    private Label lbEdate;

    @FXML
    private Label status;

    public void setData(Subscription subscription){


        File file = new File("C:/Users/asus/Desktop/Git/magnum-desktop/src/main/resources/img/test1.png");
        Image image = new Image(file.toURI().toString());
        status.setText("Active");
        lbSdate.setText(String.valueOf(subscription.getStart_date()));
        lbEdate.setText(String.valueOf(subscription.getExpire_date()));
        imgV.setImage(image);
    }

}
