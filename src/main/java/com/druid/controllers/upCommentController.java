package com.druid.controllers;

import com.druid.models.Commentaire;
import com.druid.services.ArticleService;
import com.druid.services.CommentaireService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class upCommentController implements Initializable {

  @FXML private Button retour;

  @FXML private TextArea upMessage;

  @FXML private Button upcom;

  @FXML private Text uptitle;
  ArticleService as = new ArticleService();
  CommentaireService cs = new CommentaireService();

  @FXML
  void retour(ActionEvent event) throws IOException {
    Stage stage = (Stage) retour.getScene().getWindow();
    stage.close();
    Stage Stage = new Stage();
    Parent root = FXMLLoader.load(getClass().getResource("/views/commentList.fxml"));
    Stage.setTitle("update comment");
    Stage.setScene(new Scene(root));
    Stage.show();
  }

  @FXML
  void upcom(ActionEvent event) throws IOException {
    Commentaire c = commentListController.selectedComment;
    c.setMessage(upMessage.getText());
    cs.updateCommentaire(c);
    Stage stage = (Stage) retour.getScene().getWindow();
    stage.close();
    Stage Stage = new Stage();
    Parent root = FXMLLoader.load(getClass().getResource("/views/commentList.fxml"));
    Stage.setTitle("update comment");
    Stage.setScene(new Scene(root));
    Stage.show();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    uptitle.setText(as.getArticle(CommentCotroller.ids).getTitle());
    upMessage.setText(commentListController.selectedComment.getMessage());
  }
}
