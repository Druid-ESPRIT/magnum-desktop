package com.druid.controllers;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.druid.models.Rcategorie;
import com.druid.utils.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    //Image Mag = new Image("3.png");

    @FXML
    ChoiceBox<Rcategorie> Tcat1 = new ChoiceBox<Rcategorie>();

    Connection cnx = DBConnection.getInstance().getConnection();
        private ObservableList<Rcategorie> list;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
              list = FXCollections.observableArrayList();

        try {

            ResultSet rs = cnx.createStatement().executeQuery("SELECT * FROM `tickettype`");

            while (rs.next()) {

                Tcat1.getItems().add(new Rcategorie(rs.getInt("Itype"),rs.getString("Type")));
               

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        /*
        Tcat1.getItems().add(new Categorie("1","Feature Request"));
        Tcat1.getItems().add(new Categorie("2","Missing Podcast"));
        Tcat1.getItems().add(new Categorie("3","Failed Payment"));
        Tcat1.getItems().add(new Categorie("4","Account Recovery"));
        Tcat1.getItems().add(new Categorie("5","Other"));

        Logo.setImage(Mag);*/
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void handleButtonAction(ActionEvent event) throws IOException {
        if (!Tcat1.getValue().toString().isEmpty()) {

            System.out.println(Tcat1.getValue().getIcat());
            String CS =Tcat1.getValue().toString();
            int CI =Tcat1.getValue().getIcat();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/DetTicket.fxml"));
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
