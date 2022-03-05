package com.druid.controllers;

import com.druid.models.Article;
import com.druid.models.Podcaster;
import com.druid.services.ArticleService;
import com.druid.services.PodcasterService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AddArticle {

    @FXML
    private TextField AuthorName;

    @FXML
    private Button add;

    @FXML
    private TextField content;

    @FXML
    private Button retour;

    @FXML
    private TextField title;

    @FXML
    private TextField url;
    ArticleService as=new ArticleService();
    PodcasterService ps=new PodcasterService();
    @FXML
    void add(ActionEvent event) throws IOException {
        List<Podcaster> podcasters=ps.getPodcasters();
        Podcaster podcaster=ps.getPodcasters().stream().filter(p->p.getFirstName().toUpperCase().equals(AuthorName.getText().toUpperCase())).findFirst().orElse(null);
        Article a=new Article();
        a.setAuthorID(podcaster);
        a.setContent(content.getText());
        a.setUrl(url.getText());
        a.setTitle(title.getText());
        as.addArticle(a);
        Stage stage = (Stage) retour.getScene().getWindow();
        stage.close();
        Stage Stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/views/acceuil.fxml"));
        Stage.setTitle("list Article");
        Stage.setScene(new Scene(root));
        Stage.show();
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

}
