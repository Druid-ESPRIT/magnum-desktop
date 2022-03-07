package com.druid.controllers;

import com.druid.components.HistoryCard;
import com.druid.models.History;
import com.druid.services.HistoryService;
import com.druid.utils.ConnectedUser;
import com.druid.utils.Debugger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {
    HistoryService hist_svc = new HistoryService();
    ConnectedUser connectedUser = ConnectedUser.getInstance();

    @FXML
    private AnchorPane pane;
    @FXML
    private GridPane gridPane;
    @FXML
    private Hyperlink profile;
    @FXML
    private Hyperlink security;
    @FXML
    private Hyperlink clear;

    private void reload() {
        try {
            AnchorPane historyPane = FXMLLoader.load(getClass().getResource("/views/History.fxml"));
            pane.getChildren().clear();
            pane.getChildren().add(historyPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        profile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    AnchorPane profilePane = FXMLLoader.load(getClass().getResource("/views/Profile.fxml"));
                    pane.getChildren().clear();
                    pane.getChildren().add(profilePane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hist_svc.clear(connectedUser.getUser());
                reload();
            }
        });

        security.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    AnchorPane securityPane = FXMLLoader.load(getClass().getResource("/views/Security.fxml"));
                    pane.getChildren().clear();
                    pane.getChildren().add(securityPane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        List<History> historyEntries = hist_svc.get(connectedUser.getUser());

        try {
            for (int row = 1 ; row < historyEntries.size(); row++) {
                // Load the component
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/components/HistoryCard.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                HistoryCard card = fxmlLoader.getController();

                // Get the current entry
                History hist = historyEntries.get(row);
                // Populate the controller
                card.load(hist);
                // Add it to the GridPane
                gridPane.add(anchorPane, 0, row);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
