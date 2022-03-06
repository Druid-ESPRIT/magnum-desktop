package com.druid.controllers;

import com.druid.services.CommentaireService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class suppNotif implements Initializable {

    @FXML
    private Button cancel;

    @FXML
    private Button ok;
    CommentaireService cs=new CommentaireService();

    @FXML
    void cancel(ActionEvent event) throws IOException {
        Stage stage = (Stage) ok.getScene().getWindow();
        stage.close();
        Stage Stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/views/userAcceuil.fxml"));
        Stage.setTitle("update comment");
        Stage.setScene(new Scene(root));
        Stage.show();
    }

    @FXML
    void ok(ActionEvent event) throws IOException {
        cs.cancelCommentaire(commentListController.selectedComment);
        Stage stage = (Stage) ok.getScene().getWindow();
        stage.close();
        Stage Stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/views/userAcceuil.fxml"));
        Stage.setTitle("update comment");
        Stage.setScene(new Scene(root));
        Stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}



