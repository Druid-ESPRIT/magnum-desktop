package com.druid.controllers;

import com.druid.components.HistoryCard;
import com.druid.models.History;
import com.druid.services.HistoryService;
import com.druid.utils.ConnectedUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
    private GridPane gridPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<History> historyEntries = hist_svc.get(connectedUser.getUser());

        try {
            for (int row = 0 ; row < historyEntries.size(); row++) {
                // Load the component
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/components/HistoryCard.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                HistoryCard card = fxmlLoader.getController();

                // Populate it
                History hist = historyEntries.get(row);
                card.setActivity(new Text(hist.getActivity().toString()));
                card.setDescription(new Text(hist.getDescription()));
                card.setTime(new Text(hist.getTime().toString()));

                gridPane.add(anchorPane, 0, row);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
