package com.druid;

import com.druid.interfaces.MyListener;
import com.druid.models.Offer;
import com.druid.models.Subscription;
import com.druid.services.OfferService;
import com.druid.services.SubscriptionService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SubscriptionManagerController implements Initializable {

    @FXML
    private TextField tfsearch;

    @FXML
    private Label chid;

    @FXML
    private GridPane grid;

    SubscriptionService subs = new SubscriptionService();
    List<Subscription> subscriptions = subs.getSubscriptions();

    @FXML
    void SearchClicked(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
     loadData(subscriptions);
    }
    public void loadData(List<Subscription> subs) {

        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < subs.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/subscriptionList.fxml"));

                AnchorPane anchorPaneSb = fxmlLoader.load();
                SubscriptionListController itemController = fxmlLoader.getController();
                itemController.setData(subs.get(i));


                if (column == 1) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPaneSb, column++, row);
                GridPane.setMargin(anchorPaneSb, new Insets(10));

                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);


                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
