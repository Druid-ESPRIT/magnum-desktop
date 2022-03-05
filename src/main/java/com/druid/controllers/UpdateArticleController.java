/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.druid.controllers;

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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.druid.models.Article;
import com.druid.services.ArticleService;
import com.druid.services.PodcasterService;

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
    ArticleService as=new ArticleService();
    PodcasterService ps=new PodcasterService();
    @FXML
    private Button update;
    @FXML
    private Button retour;
    

    /**
     * Initializes the controller class.
     */
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
         Article a=new Article();
        a.setAuthorID(ps.getName(AuthorUp.getText()));
        a.setContent(this.contentUp.getText());
        a.setTitle(this.titleUp.getText());
        a.setUrl(this.urlUp.getText());
        a.setId(AcceuilController.ids);
        as.updateArticle(a);
        Stage stage = (Stage) update.getScene().getWindow();
        stage.close();
         Stage Stage = new Stage();
         Parent root = FXMLLoader.load(getClass().getResource("/views/acceuil.fxml"));
        Stage.setTitle("home ");
        Stage.setScene(new Scene(root));
        Stage.show();
         
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
