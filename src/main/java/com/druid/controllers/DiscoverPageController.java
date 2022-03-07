package com.druid.controllers;

import com.druid.interfaces.MyListener;
import com.druid.models.Offer;
import com.druid.models.Podcaster;
import com.druid.models.User;
import com.druid.services.OrderService;
import com.druid.services.PodcasterService;
import com.druid.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DiscoverPageController implements Initializable {

    @FXML
    private GridPane grid;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
}
