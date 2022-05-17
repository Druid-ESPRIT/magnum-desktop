package com.druid.controllers;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.druid.models.Chat;
import com.druid.models.User;
import com.druid.utils.ConnectedUser;
import com.druid.utils.DBConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author user
 */
public class ConsulterController implements Initializable {

  @FXML private Button Aff;
  private TextField IDT;
  @FXML private TableView<Chat> Tab;
  @FXML private TableColumn<Chat, String> Msg;
  private TableColumn<Chat, String> Ifrom;

  private ObservableList<Chat> list;
  Connection cnx = DBConnection.getInstance().getConnection();
  @FXML private TextField Rep;
  @FXML private Button RepB;

  @FXML private Label Type;
  @FXML private Label STATUS;
  @FXML private Label Desc;
  @FXML private ChoiceBox<String> Nt;
  @FXML private Button Ref;

  @FXML private Label Cat;
  @FXML private Label Cat1;
  @FXML private Label U;
  @FXML private ChoiceBox<String> Ev;
  @FXML private TableColumn<Chat, String> Tmp;
  @FXML private Label Name;
  @FXML private Label Name1;

  private User connectedUser = ConnectedUser.getInstance().getUser();

  public TextField getIDT() {
    return IDT;
  }

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    Name.setText(connectedUser.getUsername());
    Name1.setText("USER:  " + String.valueOf(connectedUser.getID()));
    U.setText("");
    Ev.getItems().clear();

    connectedUser.getID();
    connectedUser.getUsername();
    int t = connectedUser.getID();

    // initialiser

    Nt.getItems().clear();
    list = FXCollections.observableArrayList();

    try {

      ResultSet rs =
          cnx.createStatement()
              .executeQuery("SELECT ID FROM ticket WHERE  USERID='" + connectedUser.getID() + "' ");

      while (rs.next()) {

        String s1 = rs.getString("ID");

        Nt.getItems().add(s1);
      }

      // int
      // s2=Integer.parseInt(Nt.getItems().toString().substring(1,Nt.getItems().toString().length()-1));

      // System.out.println(Nt.getValue());
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  @FXML
  private void Aff(ActionEvent event) {
    Ev.getItems().clear();

    U.setText("");
    System.out.println(Nt.getValue());

    if (!"".equals(Nt.getValue())) {

      list = FXCollections.observableArrayList();

      try {

        ResultSet rs =
            cnx.createStatement()
                .executeQuery(
                    "SELECT * FROM chat WHERE ID='" + Integer.parseInt(Nt.getValue()) + "'  ");

        while (rs.next()) {

          String s = "[" + rs.getString("Ifrom") + "]: " + rs.getString("Msg");
          list.add(new Chat(s, rs.getString("DateM")));
        }

      } catch (SQLException ex) {
        ex.printStackTrace();
      }

      Msg.setCellValueFactory(new PropertyValueFactory<>("Msg"));
      Tmp.setCellValueFactory(new PropertyValueFactory<>("DateM"));

      Tab.setItems(list);

      // ################################# TYPEE

      list = FXCollections.observableArrayList();

      try {

        ResultSet rs =
            cnx.createStatement()
                .executeQuery(
                    "SELECT Type FROM ticketkind WHERE ID='"
                        + Integer.parseInt(Nt.getValue())
                        + "'   ");

        while (rs.next()) {

          String s1 = rs.getString("Type");
          Type.setText(s1);
        }

      } catch (SQLException ex) {
        ex.printStackTrace();
      }

      // ################################## STATUS ET DESC
      list = FXCollections.observableArrayList();

      try {

        ResultSet rs =
            cnx.createStatement()
                .executeQuery(
                    "SELECT STATUS,Description FROM ticket WHERE ID='"
                        + Integer.parseInt(Nt.getValue())
                        + "' AND USERID='"
                        + connectedUser.getID()
                        + "' ");

        while (rs.next()) {

          String s1 = rs.getString("STATUS");
          STATUS.setText(s1);
          String s2 = rs.getString("Description");
          Desc.setText(s2);
        }

      } catch (SQLException ex) {
        ex.printStackTrace();
      }

    } else {
      System.out.println("Ekteb");
    }

    if ("Resolved".equals(STATUS.getText())) {
      U.setText("Evaluate !!");

      Ev.getItems().add("Excelent");
      Ev.getItems().add("Good");
      Ev.getItems().add("Bad");
    }
  }

  @FXML
  private void RepB(ActionEvent event) {
    if (!Nt.getSelectionModel().isEmpty()) {

      if (!"Closed".equals(STATUS.getText())) {

        System.out.println(connectedUser.getID());

        Statement st = null;

        try {
          String req =
              "INSERT INTO `chat`(`ID`, `ReSolverID`, `Msg`, `DateM`, `USERID`, `Ifrom`) VALUES ('"
                  + Integer.parseInt(Nt.getValue())
                  + "','','"
                  + Rep.getText()
                  + "',NOW(),'"
                  + connectedUser.getID()
                  + "','User')";

          st = cnx.createStatement();
          st.executeUpdate(req);

        } catch (SQLException ex) {
          ex.printStackTrace();
        }

        list = FXCollections.observableArrayList();

        try {

          ResultSet rs =
              cnx.createStatement()
                  .executeQuery(
                      "SELECT * FROM chat WHERE ID='" + Integer.parseInt(Nt.getValue()) + "'  ");

          while (rs.next()) {

            String s = "[" + rs.getString("Ifrom") + "]: " + rs.getString("Msg");

            list.add(new Chat(s, rs.getString("DateM")));
          }

        } catch (SQLException ex) {
          ex.printStackTrace();
        }

        Msg.setCellValueFactory(new PropertyValueFactory<>("Msg"));
        Tmp.setCellValueFactory(new PropertyValueFactory<>("DateM"));

        Tab.setItems(list);

      } else {
        U.setText("YOU CANT SEND ANYMORE");
        System.out.println("Closed");
      }
    }
  }

  @FXML
  private void ref(ActionEvent event) {

    Nt.getItems().clear();
    list = FXCollections.observableArrayList();

    try {

      ResultSet rs =
          cnx.createStatement()
              .executeQuery("SELECT ID FROM ticket WHERE  USERID='" + connectedUser.getID() + "' ");

      while (rs.next()) {

        String s1 = rs.getString("ID");

        Nt.getItems().add(s1);
      }

      // int
      // s2=Integer.parseInt(Nt.getItems().toString().substring(1,Nt.getItems().toString().length()-1));

      // System.out.println(Nt.getValue());
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  @FXML
  private void Ev(ActionEvent event) {

    if ((!Nt.getSelectionModel().isEmpty()) && (!Ev.getSelectionModel().isEmpty())) {

      int t = -1;

      if (Ev.getSelectionModel().getSelectedItem().toString() == "Excelent") {
        t = 5;
      } else if (Ev.getSelectionModel().getSelectedItem().toString() == "Good") {
        t = 3;
      } else {
        t = 0;
      }

      try {

        ResultSet rs =
            cnx.createStatement()
                .executeQuery(
                    "SELECT Evaluate FROM ticket WHERE ID='"
                        + Integer.parseInt(Nt.getValue())
                        + "' ");

        while (rs.next()) {
          System.out.println(rs.getString("Evaluate"));
          if (rs.getInt("Evaluate") == 0) {

            Statement st = null;
            try {
              String req =
                  "UPDATE `ticket` SET `Evaluate`='"
                      + t
                      + "'  WHERE `ID`='"
                      + Integer.parseInt(Nt.getValue())
                      + "'";
              //      System.out.println(Ev.getSelectionModel().getSelectedItem().toString());
              st = cnx.createStatement();
              st.executeUpdate(req);

            } catch (SQLException ex) {
              ex.printStackTrace();
            }

          } else {
            U.setText("YOU HAVE ALREADY EVALUATED");
          }
        }

      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    }
  }
}
