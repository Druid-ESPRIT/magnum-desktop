package com.druid.controllers;

import com.druid.models.Commentaire;
import com.druid.models.User;
import com.druid.services.ArticleService;
import com.druid.services.CommentaireService;
import com.druid.services.UserService;
import com.druid.utils.ConnectedUser;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class commentListController implements Initializable {

  private User connectedUser = ConnectedUser.getInstance().getUser();

  CommentaireService cs = new CommentaireService();
  ArticleService as = new ArticleService();
  UserService us = new UserService();
  static Commentaire selectedComment;

  @FXML private Button retour;

  @FXML private ListView<Commentaire> commentList;

  @FXML private Button deletec;

  @FXML private ListView<Commentaire> ownComments;

  @FXML private Text title;

  @FXML private Button updateC;
  @FXML private Button addC;
  @FXML private TextArea message;
  @FXML private Button flag;

  @FXML
  void deletec(ActionEvent event) throws IOException {
    selectedComment = ownComments.getSelectionModel().getSelectedItem();
    if (selectedComment != null) {
      Stage stage = (Stage) retour.getScene().getWindow();
      stage.close();
      Stage Stage = new Stage();
      Parent root = FXMLLoader.load(getClass().getResource("/views/delete.fxml"));
      Stage.setTitle("update comment");
      Stage.setScene(new Scene(root));
      Stage.show();

      // cs.cancelCommentaire(selectedComment);

    }
  }

  @FXML
  public void addC() {
    String content = message.getText();
    Commentaire com = new Commentaire();
    com.setArticleID(as.getArticle(CommentCotroller.ids));
    com.setUserID(connectedUser);
    com.setMessage(content);
    cs.addCommentaire(com);
    List<Commentaire> commentaires = cs.afficherCommentaire();
    commentaires =
        commentaires.stream()
            .filter(c -> c.getArticleID().getId() == CommentCotroller.ids)
            .collect(Collectors.toList());
    ObservableList<Commentaire> items2 = FXCollections.observableArrayList(commentaires);
    commentList.setItems(items2);
    List<Commentaire> commentaires2 =
        cs.afficherCommentaire().stream()
            .filter(
                w ->
                    w.getArticleID().getId() == CommentCotroller.ids
                        && w.getUserID().getID() == connectedUser.getID())
            .collect(Collectors.toList());
    ObservableList<Commentaire> items = FXCollections.observableArrayList(commentaires2);
    ownComments.setItems(items);
    message.setText("");
  }

  @FXML
  void updatec(ActionEvent event) throws IOException {
    selectedComment = ownComments.getSelectionModel().getSelectedItem();
    if (selectedComment != null) {
      Stage stage = (Stage) retour.getScene().getWindow();
      stage.close();
      Stage Stage = new Stage();
      Parent root = FXMLLoader.load(getClass().getResource("/views/upcomment.fxml"));
      Stage.setTitle("update comment");
      Stage.setScene(new Scene(root));
      Stage.show();
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    /// a commentee
    connectedUser.getUsername();
    /////////////
    title.setText(as.getArticle(CommentCotroller.ids).getTitle());
    List<Commentaire> commentaires = cs.afficherCommentaire();
    commentaires =
        commentaires.stream()
            .filter(c -> c.getArticleID().getId() == CommentCotroller.ids)
            .collect(Collectors.toList());
    ObservableList<Commentaire> items2 = FXCollections.observableArrayList(commentaires);
    commentList.setItems(items2);
    List<Commentaire> commentaires2 =
        cs.afficherCommentaire().stream()
            .filter(
                c ->
                    c.getArticleID().getId() == CommentCotroller.ids
                        && c.getUserID().getID() == connectedUser.getID())
            .collect(Collectors.toList());
    ObservableList<Commentaire> items = FXCollections.observableArrayList(commentaires2);
    ownComments.setItems(items);
    selectedComment = commentList.getSelectionModel().getSelectedItem();
  }

  @FXML
  void retour(ActionEvent event) throws IOException {
    Stage stage = (Stage) retour.getScene().getWindow();
    stage.close();
    Stage Stage = new Stage();
    Parent root = FXMLLoader.load(getClass().getResource("/views/userAcceuil.fxml"));
    Stage.setTitle("article list");
    Stage.setScene(new Scene(root));
    Stage.show();
  }

  @FXML
  void flag(ActionEvent event) throws IOException {
    selectedComment = commentList.getSelectionModel().getSelectedItem();
  }
}
