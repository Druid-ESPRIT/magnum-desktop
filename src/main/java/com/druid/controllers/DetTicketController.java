package com.druid.controllers;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import static java.lang.Math.abs;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

import com.druid.models.Ticket;
import com.druid.models.User;
import com.druid.utils.ConnectedUser;
import com.druid.utils.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author user
 */
public class DetTicketController implements Initializable {

    @FXML
    private TextArea Description;

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button Env;

    Connection cnx = DBConnection.getInstance().getConnection();
    @FXML
    private DatePicker CreationDate;

    @FXML
    private TextField Subject;
    @FXML
    private Button Aff;
    @FXML
    public TextField test;
    @FXML
    public Label Cat;
    String S;
    int I;
    private User connectedUser = ConnectedUser.getInstance().getUser();
    @FXML
    private Label Name;
    @FXML
    private Label Name1;
    @FXML
    private Label Cat1;

    Ticket T1 = new Ticket();
    public void Useed(int CI, String CS) {
        Cat.setText(CI + ": " + CS);
        T1.setCategorie(CS);
        S = CS;
        I = CI;


    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connectedUser.getID();
        connectedUser.getUsername();

        Name.setText(connectedUser.getUsername());
        Name1.setText("USER: "+String.valueOf(connectedUser.getID()));


    }

    public DatePicker getCreationDate() {
        return CreationDate;
    }


    public TextField getSubject() {
        return Subject;
    }

    // String title = DetTicketController.getTitle();
    // ChoiceBox<Categorie> Tcat1 = new ChoiceBox<Categorie>();
//Used Used;
    @FXML
    private void initialize(ActionEvent event) throws IOException, SQLException {
        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Edit an offer");
        alert1.setHeaderText("Do you really want to send your Ticket ?");
        alert1.setContentText("Ticket will be Sended");
        Optional<ButtonType> option = alert1.showAndWait();
        if (option.isPresent() && option.get() == ButtonType.OK) {

            System.out.println(S);

            if ((!"".equals(Description.getText())) && (connectedUser.getID() > 0) && (!"".equals(Subject.getText())) && (CreationDate.getValue() != null)) {

                // String ID = UUID.randomUUID().toString().substring(24);
                int ID = abs(ThreadLocalRandom.current().nextInt());
                T1.setID(ID);
                T1.setSubject(Subject.getText());
                T1.setDescription(Description.getText());
                //T1.setCreationDate(CreationDate.getValue());!!!!!!!!!!!              
                //res id
                T1.setUSERID(connectedUser.getID());
                T1.setSTATUS("Pending");
                T1.setReSolverId("");

                //   T1.setCategorie(sa.getCat());
                Statement st = null;
                try {

                    String req = "INSERT INTO `ticket`(`ID`, `Subject`, `Description`, `CreationDate`, `ReSolverID`, `USERID`, `STATUS`,`Evaluate`) VALUES ('" + T1.getID() + "','" + T1.getSubject() + "','" + T1.getDescription() + "','" + CreationDate.getValue() + "','" + T1.getReSolverId() + "','" + T1.getUSERID() + "','" + T1.getSTATUS() +"','"+0+"' )";
                    st = cnx.createStatement();
                    st.executeUpdate(req);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                //kind   
                Statement st2 = null;

                try {

                    String req2 = "INSERT INTO `ticketkind`(`ID`, `Itype`, `Type`) VALUES ('" + T1.getID() + "','" + I + "','" + T1.getCategorie() + "')";
                    st2 = cnx.createStatement();
                    st2.executeUpdate(req2);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                System.out.println(ID);

            } else {
                Cat1.setText("NOT SENDED");
            }
        }
    }

    public TextArea getDescription() {
        return Description;
    }

    @FXML
    private void Aff(ActionEvent event) {

        test.setText(Integer.toString(T1.getID()));

    }

}
