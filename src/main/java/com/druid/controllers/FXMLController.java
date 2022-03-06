/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.druid.controllers;

import com.druid.models.Categorie;
import com.druid.models.Podcast;
import com.druid.services.CategorieService;
import com.druid.services.PlaylistService;
import com.druid.services.PodcastService;
import com.druid.utils.DBConnection;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author bessem
 */
public class FXMLController implements Initializable {

  /** Initializes the controller class. */
  static AudioClip music;

  List<String> names = new ArrayList();
  List<String> audios = new ArrayList();
  List<Categorie> list2 = new ArrayList();
  CategorieService cs = new CategorieService();
  PlaylistService pl = new PlaylistService();
  PodcastService pods = new PodcastService();
  List<Podcast> list = new ArrayList();
  static Podcast paudio;
  @FXML private Button play;
  @FXML private Button stop;
  @FXML private TextField namecatg;
  @FXML private TextField descatg;
  @FXML private Button addcateg;
  @FXML private ListView<Categorie> listCat;
  private TextField namecateg2;
  private TextField descateg2;
  private TextField idcateg;
  @FXML private TextField titelp;
  @FXML private TextField desp;
  @FXML private TextField filep;
  @FXML private Button addpod;
  @FXML private ListView<Podcast> listAudio;
  @FXML private Button plusvolume;
  @FXML private Button volumedown;
  @FXML private Button mute;
  @FXML private Text volumeLevel;
  @FXML private Button deletep;
  @FXML private Button updatep;
  static Categorie cat;
  @FXML private Button deleteCat;
  @FXML private Button upcat;
  @FXML private ChoiceBox<String> idcatp1;

  Connection cnx = DBConnection.getInstance().getConnection();
  @FXML private Label chosenfile;
  @FXML private AnchorPane anchorPaneAddPod;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    list = pods.getPodcastByRating();
    ObservableList<Podcast> items2 = FXCollections.observableArrayList(list);
    listAudio.setItems(items2);
    paudio = listAudio.getSelectionModel().getSelectedItem();
    list2 = cs.afficherCategorie();
    ObservableList<Categorie> items = FXCollections.observableArrayList(list2);
    listCat.setItems(items);
    cat = listCat.getSelectionModel().getSelectedItem();

    try {

      ResultSet rs = cnx.createStatement().executeQuery("SELECT * FROM categorie ");

      while (rs.next()) {
        idcatp1.getItems().addAll(rs.getString("namecateg"));
      }

    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  @FXML
  private void addateg(ActionEvent event) {
    Categorie c = new Categorie();
    c.setDescriptioncateg(this.descatg.getText());
    c.setNamecateg(this.namecatg.getText());
    cs.ajouterCategorie(c);
    list2 = cs.afficherCategorie();
    ObservableList<Categorie> items = FXCollections.observableArrayList(list2);
    listCat.setItems(items);
  }

  @FXML
  private void paly(ActionEvent event) {
    paudio = listAudio.getSelectionModel().getSelectedItem();
    music = new AudioClip(paudio.getFile());
    music.play(1.0);
  }

  @FXML
  private void stop(ActionEvent event) {

    music.stop();
  }

  @FXML
  private void plusvolume(ActionEvent event) {

    Double v = Double.valueOf(volumeLevel.getText()) / 100;
    if (v + 0.1 <= 1) volumeLevel.setText(String.valueOf((v + 0.1) * 100));
    music.setVolume(v + 0.1);
  }

  @FXML
  private void volumedown(ActionEvent event) {
    Double v = Double.valueOf(volumeLevel.getText()) / 100;
    if (v - 0.1 > 0) volumeLevel.setText(String.valueOf((v - 0.1) * 100));
    music.setVolume(v - 0.1);
  }

  @FXML
  private void mute(ActionEvent event) {

    music.setVolume(0);
  }

  @FXML
  private void addpod(ActionEvent event) {

    Podcast p = new Podcast();
    p.setDescription(this.desp.getText());
    p.setFile(this.filep.getText());
    p.setTitle(this.titelp.getText());
    p.setImage(this.chosenfile.getText());

    try {

      ResultSet rs =
          cnx.createStatement()
              .executeQuery(
                  "SELECT * FROM categorie where namecateg='"
                      + idcatp1.getSelectionModel().getSelectedItem()
                      + "' ");

      while (rs.next()) {
        // idcatp1.getItems().addAll(rs.getString("namecateg"));

        p.setIdcateg(rs.getInt("idcategorie"));
      }

    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    pods.ajouterPodcast(p);
    list = pods.getPodcastByRating();
    ObservableList<Podcast> items2 = FXCollections.observableArrayList(list);
    listAudio.setItems(items2);
  }

  @FXML
  private void deletep(ActionEvent event) {
    Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
    alert1.setTitle("Edit an offer");
    alert1.setHeaderText("Do you really want to delete this Podcast ?");
    alert1.setContentText("podcast will be deleted");
    Optional<ButtonType> option = alert1.showAndWait();
    if (option.isPresent() && option.get() == ButtonType.OK) {
      paudio = listAudio.getSelectionModel().getSelectedItem();
      if (paudio != null) {
        pods.supprimerPodcast(paudio.getId());
        list = pods.getPodcastByRating();
        ObservableList<Podcast> items2 = FXCollections.observableArrayList(list);
        listAudio.setItems(items2);
      }
    }
  }

  @FXML
  private void updatep(ActionEvent event) throws IOException {
    paudio = listAudio.getSelectionModel().getSelectedItem();
    if (paudio != null) {
      Stage stage = (Stage) mute.getScene().getWindow();
      stage.close();
      Stage Stage = new Stage();
      Parent root = FXMLLoader.load(getClass().getResource("/views/updatePod.fxml"));
      Stage.setTitle("Update Podcaster");
      Stage.setScene(new Scene(root));
      Stage.show();
    }
  }

  @FXML
  private void deleteCat(ActionEvent event) {
    Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
    alert1.setTitle("Edit an offer");
    alert1.setHeaderText("Do you really want to delete your categorie ?");
    alert1.setContentText("Categorie will be deleted");
    Optional<ButtonType> option = alert1.showAndWait();
    if (option.isPresent() && option.get() == ButtonType.OK) {
      cat = listCat.getSelectionModel().getSelectedItem();
      if (cat != null) {
        cs.supprimerCategorie(cat.getIdcateg());
        list2 = cs.afficherCategorie();
        ObservableList<Categorie> items = FXCollections.observableArrayList(list2);
        listCat.setItems(items);
      }
    }
  }

  @FXML
  private void upcat(ActionEvent event) throws IOException {
    cat = listCat.getSelectionModel().getSelectedItem();
    if (cat != null) {
      Stage stage = (Stage) mute.getScene().getWindow();
      stage.close();
      Stage Stage = new Stage();
      Parent root = FXMLLoader.load(getClass().getResource("updateCat.fxml"));
      Stage.setTitle("Update Podcaster");
      Stage.setScene(new Scene(root));
      Stage.show();
    }
  }

  @FXML
  private void uploadFile(ActionEvent event) {

    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");

    Stage stage = (Stage) anchorPaneAddPod.getScene().getWindow();
    File file = fileChooser.showOpenDialog(stage);
    if (file != null) {
      chosenfile.setText(file.getName());
    }
  }
}
