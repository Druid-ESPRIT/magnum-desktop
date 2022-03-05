/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.druid.controllers;

import com.druid.models.Article;
import com.druid.services.ArticleService;
import com.druid.services.PodcasterService;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.druid.models.Podcaster;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author zeineb
 */
public class UpdateArticleController implements Initializable {


    @FXML
    private TextField titleUp;
    @FXML
    private TextField contentUp;
    @FXML
    private TextField AuthorUp;
    @FXML
    private TextField urlUp;
    AcceuilController ac;
    ArticleService as = new ArticleService();
    PodcasterService ps = new PodcasterService();
    @FXML
    private Button update;
    @FXML
    private Button retour;


    @FXML
    private Text authorctrl;
    @FXML
    private Text contentctrl;
    @FXML
    private Text urlctrl;


    @FXML
    private Text titlectrl;
    private void alert(Text field, String content) {
        field.setOpacity(100);
        field.setText(content);
    }
    private void hideAlert(Text field) {
        if (field.getOpacity() == 100) {
            field.setOpacity(0);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Article a=as.getArticle(AcceuilController.ids);
        titleUp.setText(a.getTitle());
        contentUp.setText(a.getContent());
        AuthorUp.setText(String.valueOf(a.getAuthorID().getFirstName()));
        System.err.println(a.getUrl());
        urlUp.setText(a.getUrl());

    }

    @FXML
    private void update(ActionEvent event) throws IOException {
        boolean valid=true;
        Article a = new Article();
        List<Podcaster> podcasters=ps.getPodcasters();
        Podcaster podcaster=ps.getPodcasters().stream().filter(p->p.getFirstName().toUpperCase().equals(AuthorUp.getText().toUpperCase())).findFirst().orElse(null);

        a.setAuthorID(ps.getName(AuthorUp.getText()));
        a.setContent(this.contentUp.getText());
        a.setTitle(this.titleUp.getText());
        a.setUrl(this.urlUp.getText());
        a.setId(AcceuilController.ids);
        if (AuthorUp.getText().equals("")) {
            valid = false;
            alert(authorctrl, "ce champ est oblegatoire");

        } else {

            if (podcaster == null) {
                alert(authorctrl, "invalid author Name");
                valid = false;
            } else
                hideAlert(authorctrl);
        }


        if (!contentUp.getText().equals("")) {
            hideAlert(contentctrl);

        }
        if (!urlUp.getText().equals("")) {
            hideAlert(urlctrl);

        }
        if (!titleUp.getText().equals("")) {
            hideAlert(titlectrl);

        } else {
            if (podcaster == null)
                alert(authorctrl, "invalid author Name");
            valid = false;
        }
        if (contentUp.getText().equals("")) {
            alert(contentctrl, "ce champ est oblegatoire");
            valid = false;
        }
        if (urlUp.getText().equals("")) {
            alert(urlctrl, "ce champ est oblegatoire");
            valid = false;
        }
        if (titleUp.getText().equals("")) {
            alert(titlectrl, "ce champ est oblegatoire");
            valid = false;
        }

        if (valid) {

            as.updateArticle(a);
            Stage stage = (Stage) update.getScene().getWindow();
            stage.close();
            Stage Stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/views/acceuil.fxml"));
            Stage.setTitle("home ");
            Stage.setScene(new Scene(root));
            Stage.show();

        }
    }
    @FXML
    private void retour(ActionEvent event) throws IOException {
        Stage stage = (Stage) update.getScene().getWindow();
        stage.close();
        Stage Stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/views/acceuil.fxml"));
        Stage.setTitle("home ");
        Stage.setScene(new Scene(root));
        Stage.show();

    }

}



