/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.druid.controllers;

import com.druid.models.Podcast;
import com.druid.services.CategorieService;
import com.druid.services.PodcastService;
import com.druid.utils.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author zeineb
 */
public class UpdatePodController implements Initializable {

  @FXML private TextField titleup;
  @FXML private TextField fileup;
  @FXML private TextField descriptionUp;
  @FXML private Button update;
  @FXML private Button retourP;
  PodcastService ps = new PodcastService();
  CategorieService cs = new CategorieService();
  @FXML private TextField categorieName;
  @FXML private ChoiceBox<String> categorieName1;

  /** Initializes the controller class. */
  Connection cnx = DBConnection.getInstance().getConnection();

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    titleup.setText(FXMLController.paudio.getTitle());
    fileup.setText(FXMLController.paudio.getFile());
    descriptionUp.setText(FXMLController.paudio.getDescription());
    categorieName.setText(cs.getCategorie(FXMLController.paudio.getIdcateg()).getNamecateg());
    try {

      ResultSet rs = cnx.createStatement().executeQuery("SELECT * FROM categorie ");

      while (rs.next()) {
        categorieName1.getItems().addAll(rs.getString("namecateg"));
      }

    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  @FXML
  private void update(ActionEvent event) throws IOException {

    String title = titleup.getText();
    String file = fileup.getText();
    String desc = descriptionUp.getText();
    String cate = categorieName1.getSelectionModel().getSelectedItem();
    // String cate=categorieName1.getSelectionModel().getSelectedItem();

    Podcast p = new Podcast();
    p.setId(FXMLController.paudio.getId());
    p.setFile(file);
    p.setDescription(desc);
    p.setTitle(title);
    System.err.println(cs.getCategorieByName(cate));

    p.setIdcateg(cs.getCategorieByName(cate).getIdcateg());

    ps.modifierPodcast(p);
    Stage stage = (Stage) update.getScene().getWindow();
    stage.close();
    Stage Stage = new Stage();
    Parent root = FXMLLoader.load(getClass().getResource("/views/FXML.fxml"));
    Stage.setTitle("home");
    Stage.setScene(new Scene(root));
    Stage.show();
  }

  @FXML
  private void retourP(ActionEvent event) throws IOException {
    Stage stage = (Stage) update.getScene().getWindow();
    stage.close();
    Stage Stage = new Stage();
    Parent root = FXMLLoader.load(getClass().getResource("/views/FXML.fxml"));
    Stage.setTitle("home");
    Stage.setScene(new Scene(root));
    Stage.show();
  }
}
