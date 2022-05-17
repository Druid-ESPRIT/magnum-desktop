package com.druid.controllers;

import com.druid.models.Article;
import com.druid.models.Podcaster;
import com.druid.models.User;
import com.druid.services.ArticleService;
import com.druid.services.PodcasterService;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.druid.utils.ConnectedUser;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import javax.imageio.ImageIO;
import javax.swing.*;


public class AddArticle implements Initializable {

  @FXML private TextField AuthorName;

  @FXML private Button add;

  @FXML private Text authornameControl;

  @FXML private TextField content;

  @FXML private Text contentControl;

  @FXML private Button retour;
  @FXML private Button fileselector;

  @FXML private TextField title;

  @FXML private Text titlecontrol;

  @FXML private TextField url;
  @FXML private AnchorPane anchorPane;
  @FXML private Label filechosen;

  @FXML private Text urlcontrol;
  ArticleService as = new ArticleService();
  PodcasterService ps = new PodcasterService();
  User connectedUser = ConnectedUser.getInstance().getUser();


  private void alert(Text field, String content) {
    field.setOpacity(100);
    field.setText(content);
  }

  private void hideAlert(Text field) {
    if (field.getOpacity() == 100) {
      field.setOpacity(0);
    }
  }

  @FXML
  void add(ActionEvent event) throws IOException {


    boolean valid = true;
    /*List<User> Podcasters = ps.getPodcasters();
    Podcaster podcaster =
        ps.getPodcasters().stream()
            .filter(p -> p.getFirstName().toUpperCase().equals(AuthorName.getText().toUpperCase()))
            .findFirst()
            .orElse(null);*/
    Article a = new Article();

    a.setAuthorID(connectedUser);
    a.setContent(content.getText());
    a.setUrl(url.getText());
    a.setTitle(title.getText());
    /*if (AuthorName.getText().equals("")) {
      valid = false;
      alert(authornameControl, "ce champ est oblegatoire");

    } else {

      /*if (podcaster == null) {
        alert(authornameControl, "invalid author Name");
        valid = false;
      } else hideAlert(authornameControl);
    }*/

    if (!content.getText().equals("")) {
      hideAlert(contentControl);
    }
    if (!url.getText().equals("")) {
      hideAlert(urlcontrol);
    }
    if (!title.getText().equals("")) {
      hideAlert(titlecontrol);

    } else {
      /*if (podcaster == null) alert(authornameControl, "invalid author Name");
      valid = false;*/
    }
    if (content.getText().equals("")) {
      alert(contentControl, "ce champ est oblegatoire");
      valid = false;
    }
    if (url.getText().equals("")) {
      alert(urlcontrol, "ce champ est oblegatoire");
      valid = false;
    }
    if (title.getText().equals("")) {
      alert(titlecontrol, "ce champ est oblegatoire");
      valid = false;
    }

    if (valid) {
      as.addArticle(a);
      Stage stage = (Stage) retour.getScene().getWindow();
      stage.close();
      Stage Stage = new Stage();
      Parent root = FXMLLoader.load(getClass().getResource("/views/acceuil.fxml"));
      Stage.setTitle("list Article");
      Stage.setScene(new Scene(root));
      Stage.show();
    }
  }

  @FXML
  void retour(ActionEvent event) throws IOException {
    Stage stage = (Stage) retour.getScene().getWindow();
    stage.close();
    Stage Stage = new Stage();
    Parent root = FXMLLoader.load(getClass().getResource("/views/acceuil.fxml"));
    Stage.setTitle("update comment");
    Stage.setScene(new Scene(root));
    Stage.show();
  }
  /*@FXML
  void fileselector(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    fileChooser
            .getExtensionFilters()
            .addAll(
                    new FileChooser.ExtensionFilter("All Images", "*.*"),
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png"));
    Stage stage = (Stage) anchorPane.getScene().getWindow();
    File file = fileChooser.showOpenDialog(stage);
    if (file != null) {
      filechosen.setText(file.getName());
    }
  }*/
  /*@FXML
  private void imageAdd(MouseEvent event) {

    FileChooser fc = new FileChooser();

    FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
    FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
    fc.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
    File selectedFile = fc.showOpenDialog(null);
    try {
      BufferedImage bufferedImage = ImageIO.read(selectedFile);
      Image image = SwingFXUtils.toFXImage(bufferedImage, null);
      //img.setImage(image);
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
    }
  }*/


  /*private void attachActionPerformed(java.awt.event.ActionEvent event){
    JFileChooser chooser = new JFileChooser();
    chooser.showOpenDialog(null);
    File f = chooser.getSelectedFile();
    String filename =f.getAbsolutePath();
    content.setText(filename);*/





  @Override
  public void initialize(URL location, ResourceBundle resources) {
    AuthorName.setText(connectedUser.getUsername());
    AuthorName.setDisable(true);

  }
}
