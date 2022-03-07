package com.druid.controllers;

import com.druid.models.Article;
import com.druid.services.ArticleService;
import com.druid.services.CommentaireService;
import com.druid.services.PodcasterService;
import com.druid.services.UserService;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CommentCotroller implements Initializable {

  UserService us = new UserService();
  CommentaireService commentaireService = new CommentaireService();

  @FXML private Button Pdf1;

  @FXML private Button Pdf2;

  @FXML private Button Pdf3;

  @FXML private Button get;

  @FXML private TextField getByAuthor;

  @FXML private ImageView image1;

  @FXML private ImageView image2;

  @FXML private ImageView image3;

  @FXML private ImageView image4;

  @FXML private Pagination pagination;

  @FXML private Button pdf4;

  @FXML private Button show1;

  @FXML private Button show2;

  @FXML private Button show3;

  @FXML private Button show4;

  @FXML private Text title1;

  @FXML private Text title2;

  @FXML private Text title3;

  @FXML private Text title4;
  ArticleService as = new ArticleService();
  PodcasterService ps = new PodcasterService();
  List<Article> articles = as.afficherArticle();
  List<Article> articles2 = as.afficherArticle();
  static int id1;
  static int id2;
  static int id3;
  static int id4;
  static int ids;
  int itemsPerPage = 4;

  public VBox createPage(int pageIndex) {
    VBox box = new VBox();
    this.title1.setText("");
    this.image1.setImage(null);
    this.title2.setText("");
    this.image2.setImage(null);
    this.title3.setText("");
    this.image3.setImage(null);
    this.title4.setText("");
    this.image4.setImage(null);
    int page = pageIndex * itemsPerPage;
    if (this.articles.size() - page >= 4) {
      this.title1.setText(this.articles.get(page).getTitle());
      this.image1.setImage(new Image(this.articles.get(page).getUrl()));
      id1 = this.articles.get(page).getId();
      this.title2.setText(this.articles.get(page + 1).getTitle());
      this.image2.setImage(new Image(this.articles.get(page + 1).getUrl()));
      id2 = this.articles.get(page + 1).getId();
      this.title3.setText(this.articles.get(page + 2).getTitle());
      this.image3.setImage(new Image(this.articles.get(page + 2).getUrl()));
      id3 = this.articles.get(page + 2).getId();
      this.title4.setText(this.articles.get(page + 3).getTitle());
      this.image4.setImage(new Image(this.articles.get(page + 3).getUrl()));
      id4 = this.articles.get(page + 3).getId();
    }
    if (this.articles.size() - page >= 3) {
      this.title1.setText(this.articles.get(page).getTitle());
      this.image1.setImage(new Image(this.articles.get(page).getUrl()));
      id1 = this.articles.get(page).getId();
      this.title2.setText(this.articles.get(page + 1).getTitle());
      this.image2.setImage(new Image(this.articles.get(page + 1).getUrl()));
      id2 = this.articles.get(page + 1).getId();
      this.title3.setText(this.articles.get(page + 2).getTitle());
      this.image3.setImage(new Image(this.articles.get(page + 2).getUrl()));
      id3 = this.articles.get(page + 2).getId();
    }
    if (this.articles.size() - page >= 2) {
      this.title1.setText(this.articles.get(page).getTitle());
      this.image1.setImage(new Image(this.articles.get(page).getUrl()));
      id1 = this.articles.get(page).getId();
      this.title2.setText(this.articles.get(page + 1).getTitle());
      this.image2.setImage(new Image(this.articles.get(page + 1).getUrl()));
      id2 = this.articles.get(page + 1).getId();
    }
    if (this.articles.size() - page >= 1) {
      this.title1.setText(this.articles.get(page).getTitle());
      this.image1.setImage(new Image(this.articles.get(page).getUrl()));
      id1 = this.articles.get(page).getId();
    }

    return box;
  }

  @FXML
  void get(ActionEvent event) {
    this.articles = this.articles2;
    if (!"".equals(this.getByAuthor.getText())) {
      this.articles =
          this.articles.stream()
              .filter(
                  a ->
                      a.getAuthorID()
                          .getFirstName()
                          .toUpperCase()
                          .equals(this.getByAuthor.getText().toUpperCase()))
              .collect(Collectors.toList());
      this.title1.setText("");
      this.image1.setImage(null);
      this.title2.setText("");
      this.image2.setImage(null);
      this.title3.setText("");
      this.image3.setImage(null);
      this.title4.setText("");
      this.image4.setImage(null);
      this.createPage(0);
    }

    this.createPage(0);
  }

  @FXML
  void pdf1(ActionEvent event) {
    try {
      this.Pdf(as.getArticle(id1).getContent());
    } catch (IOException ex) {
      Logger.getLogger(AcceuilController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @FXML
  void pdf2(ActionEvent event) {
    try {
      this.Pdf(as.getArticle(id2).getContent());
    } catch (IOException ex) {
      Logger.getLogger(AcceuilController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @FXML
  void pdf3(ActionEvent event) {
    try {
      this.Pdf(as.getArticle(id3).getContent());
    } catch (IOException ex) {
      Logger.getLogger(AcceuilController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @FXML
  void pdf4(ActionEvent event) {
    try {
      this.Pdf(as.getArticle(id4).getContent());
    } catch (IOException ex) {
      Logger.getLogger(AcceuilController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @FXML
  void show1(ActionEvent event) throws IOException {
    ids = id1;
    Stage stage = (Stage) show1.getScene().getWindow();
    stage.close();
    Stage Stage = new Stage();
    Parent root = FXMLLoader.load(getClass().getResource("/views/commentList.fxml"));
    Stage.setTitle("comment list");
    Stage.setScene(new Scene(root));
    Stage.show();
  }

  @FXML
  void show2(ActionEvent event) throws IOException {
    ids = id2;
    Stage stage = (Stage) show1.getScene().getWindow();
    stage.close();
    Stage Stage = new Stage();
    Parent root = FXMLLoader.load(getClass().getResource("/views/commentList.fxml"));
    Stage.setTitle("comment list");
    Stage.setScene(new Scene(root));
    Stage.show();
  }

  @FXML
  void show3(ActionEvent event) throws IOException {
    ids = id3;
    Stage stage = (Stage) show1.getScene().getWindow();
    stage.close();
    Stage Stage = new Stage();
    Parent root = FXMLLoader.load(getClass().getResource("/views/commentList.fxml"));
    Stage.setTitle("comment list");
    Stage.setScene(new Scene(root));
    Stage.show();
  }

  @FXML
  void show4(ActionEvent event) throws IOException {
    ids = id4;
    Stage stage = (Stage) show1.getScene().getWindow();
    stage.close();
    Stage Stage = new Stage();
    Parent root = FXMLLoader.load(getClass().getResource("/views/commentList.fxml"));
    Stage.setTitle("comment list");
    Stage.setScene(new Scene(root));
    Stage.show();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    if (this.articles.size() % 4 == 0) pagination.setPageCount(this.articles.size() / 4);
    else pagination.setPageCount(this.articles.size() / 4 + 1);
    pagination.setPageFactory((Integer pageIndex) -> createPage(pageIndex));
  }

  public void Pdf(String url) throws IOException {
    try {
      Desktop d = Desktop.getDesktop();
      URI myURI = new URI(url);
      d.browse(myURI);

    } catch (URISyntaxException ex) {

    }
  }
}
