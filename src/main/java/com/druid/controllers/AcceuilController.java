/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.druid.controllers;

import com.druid.models.Article;
import com.druid.services.ArticleService;
import com.druid.services.CommentaireService;
import com.druid.services.PodcasterService;
import java.awt.Desktop;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author zeineb
 */
public class AcceuilController implements Initializable {

  @FXML private Pagination pagination;
  @FXML private Text title1;
  @FXML private Button add;


  ArticleService as = new ArticleService();
  PodcasterService ps = new PodcasterService();
  CommentaireService cs = new CommentaireService();
  List<Article> articles = as.afficherArticle();
  List<Article> articles2 = as.afficherArticle();
  static int id1 = 0;
  static int id2 = 0;
  static int id3 = 0;
  static int id4 = 0;
  static int ids;
  int itemsPerPage = 4;
  @FXML private ImageView image1;
  @FXML private ImageView image2;
  @FXML private ImageView image3;
  @FXML private ImageView image4;
  @FXML private Text title2;
  @FXML private Text title3;
  @FXML private Text title4;
  @FXML private Button delete1;
  @FXML private Button delete2;
  @FXML private Button delete3;
  @FXML private Button delete4;
  @FXML private Button Pdf1;
  @FXML private Button Pdf2;
  @FXML private Button Pdf3;
  @FXML private Button pdf4;
  @FXML private TextField getByAuthor;
  @FXML private Button get;
  @FXML private Button update1;
  @FXML private Button update2;
  @FXML private Button update3;
  @FXML private Button update4;

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

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
    // pagination=new
    if (this.articles.size() % 4 == 0) pagination.setPageCount(this.articles.size() / 4);
    else pagination.setPageCount(this.articles.size() / 4 + 1);
    pagination.setPageFactory((Integer pageIndex) -> createPage(pageIndex));
  }

  @FXML
  private void delete1(ActionEvent event) {
    as.cancelArticle(id1);
    ///articles2 = as.afficherArticle();
    articles2 = as.afficherArticle();
    this.createPage(0);
    if (this.articles2.size() % 4 == 0) pagination.setPageCount(this.articles2.size() / 4);
    else pagination.setPageCount(this.articles2.size() / 4 + 1);
    this.createPage(0);
  }

  @FXML
  private void delete2(ActionEvent event) {
    as.cancelArticle(id2);
    articles2 = as.afficherArticle();
    articles2 = as.afficherArticle();
    this.createPage(0);
    if (this.articles2.size() % 4 == 0) pagination.setPageCount(this.articles2.size() / 4);
    else pagination.setPageCount(this.articles2.size() / 4 + 1);
    this.createPage(0);
  }

  @FXML
  private void delete3(ActionEvent event) {
    as.cancelArticle(id3);
    articles2 = as.afficherArticle();
    articles2 = as.afficherArticle();
    this.createPage(0);
    if (this.articles2.size() % 4 == 0) pagination.setPageCount(this.articles2.size() / 4);
    else pagination.setPageCount(this.articles2.size() / 4 + 1);
    this.createPage(0);
  }

  @FXML
  private void delete4(ActionEvent event) {
    as.cancelArticle(id4);
    System.out.print(id4);
    articles2 = as.afficherArticle();
    articles2 = as.afficherArticle();
    this.createPage(0);
    if (this.articles2.size() % 4 == 0) pagination.setPageCount(this.articles2.size() / 4);
    else pagination.setPageCount(this.articles2.size() / 4 + 1);
    this.createPage(0);
  }

  public void Pdf(String url) throws IOException {
    try {
      Desktop d = Desktop.getDesktop();
      URI myURI = new URI(url);
      d.browse(myURI);

    } catch (URISyntaxException ex) {

    }
  }

  @FXML
  private void pdf1(ActionEvent event) {
    try {
      this.Pdf(as.getArticle(id1).getContent());
    } catch (IOException ex) {
      Logger.getLogger(AcceuilController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @FXML
  private void pdf2(ActionEvent event) {
    try {
      this.Pdf(as.getArticle(id2).getContent());
    } catch (IOException ex) {
      Logger.getLogger(AcceuilController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @FXML
  private void pdf3(ActionEvent event) {
    try {
      this.Pdf(as.getArticle(id3).getContent());
    } catch (IOException ex) {
      Logger.getLogger(AcceuilController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @FXML
  private void pdf4(ActionEvent event) {
    try {
      this.Pdf(as.getArticle(id4).getContent());
    } catch (IOException ex) {
      Logger.getLogger(AcceuilController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @FXML
  private void update1(ActionEvent event) throws IOException {
    if (id1 != 0) {
      ids = id1;
      Stage stage = (Stage) update1.getScene().getWindow();
      stage.close();
      Stage Stage = new Stage();
      Parent root = FXMLLoader.load(getClass().getResource("/views/UpdateArticle.fxml"));
      Stage.setTitle("Update Article");
      Stage.setScene(new Scene(root));
      Stage.show();
    }
  }

  @FXML
  private void update2(ActionEvent event) throws IOException {
    if (id2 != 0) {
      ids = id2;
      Stage stage = (Stage) update1.getScene().getWindow();
      stage.close();
      Stage Stage = new Stage();
      Parent root = FXMLLoader.load(getClass().getResource("/views/UpdateArticle.fxml"));
      Stage.setTitle("Update Article");
      Stage.setScene(new Scene(root));
      Stage.show();
    }
  }

  @FXML
  private void update3(ActionEvent event) throws IOException {
    if (id3 != 0) {
      ids = id3;
      Stage stage = (Stage) update1.getScene().getWindow();
      stage.close();
      Stage Stage = new Stage();
      Parent root = FXMLLoader.load(getClass().getResource("/views/UpdateArticle.fxml"));
      Stage.setTitle("Update Article");
      Stage.setScene(new Scene(root));
      Stage.show();
    }
  }

  @FXML
  private void get(ActionEvent event) {
    this.articles = this.articles2;

    if (!"".equals(this.getByAuthor.getText())) {
      this.articles =
          this.articles.stream()
              .filter(
                  a ->
                      a.getAuthorID()
                              .getUsername()
                          .toUpperCase()
                          .equals(this.getByAuthor.getText().toUpperCase()))
              .sorted((v, u) -> cs.getNbComment(u.getId()) - cs.getNbComment(v.getId()))
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
  private void update4(ActionEvent event) throws IOException {
    if (id4 != 0) {
      ids = id4;
      Stage stage = (Stage) update1.getScene().getWindow();
      stage.close();
      Stage Stage = new Stage();
      Parent root = FXMLLoader.load(getClass().getResource("/views/UpdateArticle.fxml"));
      Stage.setTitle("Update Article");
      Stage.setScene(new Scene(root));
      Stage.show();
    }
  }

  @FXML
  private void add(ActionEvent event) throws IOException {

    Stage stage = (Stage) update1.getScene().getWindow();
    stage.close();
    Stage Stage = new Stage();
    Parent root = FXMLLoader.load(getClass().getResource("/views/AddArticle.fxml"));
    Stage.setTitle("add Article");
    Stage.setScene(new Scene(root));
    Stage.show();
  }
}
