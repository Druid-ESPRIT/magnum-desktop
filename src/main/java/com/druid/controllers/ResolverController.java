package com.druid.controllers;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

import com.druid.controllers.ReplyController;
import com.druid.models.Ticket;
import com.druid.models.User;
import com.druid.utils.ConnectedUser;
import com.druid.utils.DBConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author user
 */
public class ResolverController implements Initializable {

    @FXML
    private TextField Res;

    @FXML
    private Button SuppB;
    public TextField Supp;
    private ChoiceBox<String> Msup;
    private TextField IDT;
    private TextArea Rep;
    @FXML
    private Button Refresh;
    @FXML
    private Button RechB;
    @FXML
    private ChoiceBox<String> Mrech;
    @FXML
    private TextField Rech;
    @FXML
    private Button ResB;
    @FXML
    private ChoiceBox<String> Mstat;
    @FXML
    private TextField Stat;

    @FXML
    private ListView<String> ID;
    @FXML
    private ListView<String> USERID;
    @FXML
    private ListView<String> CreationDate;
    @FXML
    private ListView<String> Subject;
    @FXML
    private ListView<String> Description;
    @FXML
    private ListView<String> Categorie;
    @FXML
    private ListView<String> STATUS;
    @FXML
    private TextField cat;
    @FXML
    private TextField search;
    @FXML
    private Label CreationDate1;
    @FXML
    private Label Subject1;
    @FXML
    private Label Description1;
    @FXML
    private Label Categorie1;
    @FXML
    private Label STATUS1;
    @FXML
    private Label USERID1;
    @FXML
    private Label ID1;
    @FXML
    private ListView<String> BEST;
    @FXML
    private ListView<String> Lcat;
    private User connectedUser = ConnectedUser.getInstance().getUser();

    @FXML
    private Label msg;

    public ChoiceBox<String> getMsup() {
        return Msup;
    }

    public void setMsup(ChoiceBox<String> Msup) {
        this.Msup = Msup;
    }

    public TextArea getRep() {
        return Rep;
    }

    public TextField getRech() {
        return Rech;
    }

    private ObservableList<Ticket> list;
    Connection cnx = DBConnection.getInstance().getConnection();

    public TextField getSupp() {
        return Supp;
    }

    public void setSupp(TextField Supp) {
        this.Supp = Supp;
    }

    public TextField getIDT() {
        return IDT;
    }

    public TextField getRes() {
        return Res;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {



        Mrech.getItems().add("ID");
        Mrech.getItems().add("USERID");
        Mrech.getItems().add("Categorie");
        Mrech.getItems().add("STATUS");

        Mstat.getItems().add("Resolved");
        Mstat.getItems().add("Pending");
        Mstat.getItems().add("Closed");
        list = FXCollections.observableArrayList();

        try {

            ResultSet rs = cnx.createStatement().executeQuery("SELECT * FROM ticket,ticketkind WHERE ticket.ID=ticketkind.ID");

            while (rs.next()) {

                list.add(new Ticket(rs.getInt("ticket.ID"), rs.getString("ticket.Subject"), rs.getString("ticket.Description"), rs.getString("ticket.CreationDate"), rs.getString("ticket.ReSolverID"), rs.getInt("ticket.USERID"), rs.getString("ticket.STATUS"), rs.getString("ticketkind.Type")));

                ID.getItems().addAll(rs.getString("ticket.ID"));
                Subject.getItems().addAll(rs.getString("ticket.Subject"));
                Description.getItems().addAll(rs.getString("ticket.Description"));
                CreationDate.getItems().addAll(rs.getString("ticket.CreationDate"));
                USERID.getItems().addAll(rs.getString("ticket.USERID"));
                STATUS.getItems().addAll(rs.getString("ticket.STATUS"));
                Categorie.getItems().addAll(rs.getString("ticketkind.Type"));

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        ID.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String atg1, String atrg2) {
                System.out.println(ID.getSelectionModel().getSelectedItem());
                try {

                    ResultSet rs = cnx.createStatement().executeQuery("SELECT * FROM ticket,ticketkind WHERE ticket.ID='" + ID.getSelectionModel().getSelectedItem() + "' AND ticketkind.ID='" + ID.getSelectionModel().getSelectedItem() + "' ");

                    while (rs.next()) {

                        ID1.setText(rs.getString("ticket.ID"));

                        USERID1.setText(rs.getString("ticket.USERID"));
                        CreationDate1.setText(rs.getString("ticket.CreationDate"));
                        Subject1.setText(rs.getString("ticket.Subject"));
                        Description1.setText(rs.getString("ticket.Description"));
                        Categorie1.setText(rs.getString("ticketkind.Type"));
                        STATUS1.setText(rs.getString("ticket.STATUS"));

                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });

        USERID.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String atg1, String atrg2) {
                System.out.println(USERID.getSelectionModel().getSelectedItem());
            }
        });

        try {

            ResultSet rs = cnx.createStatement().executeQuery("SELECT ReSolverID,SUM(Evaluate) AS t FROM ticket GROUP BY ReSolverID");

            while (rs.next()) {
                String s = rs.getString("ReSolverID") + ":>" + rs.getString("t");

                BEST.getItems().addAll(s);

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {

            ResultSet rs = cnx.createStatement().executeQuery("SELECT * from tickettype");

            while (rs.next()) {

                Lcat.getItems().addAll(rs.getString("Type"));

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    private void SuppB(ActionEvent event) {
             Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Edit an offer");
        alert1.setHeaderText("Do you really want to DELETE ?");
        alert1.setContentText("Ticket will be UPDATED");
        Optional<ButtonType> option = alert1.showAndWait();
        if (option.isPresent() && option.get() == ButtonType.OK) {

        list = FXCollections.observableArrayList();

        if ("USERID".equals(Mrech.getValue().toString())) {

            Statement st = null;
            Statement st1 = null;
            Statement st2 = null;

            try {

                ResultSet rs = cnx.createStatement().executeQuery("SELECT * FROM ticket,ticketkind WHERE ticketkind.ID=ticket.ID AND ticket.USERID='" + Integer.parseInt(getRech().getText()) + "'  ");

                while (rs.next()) {

                    int s = rs.getInt("ticketkind.ID");
                    String req1 = "DELETE FROM ticketkind WHERE  ID='" + s + "' ";

                    st1 = cnx.createStatement();
                    st1.executeUpdate(req1);

                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            try {

                String req = "DELETE FROM ticket WHERE  USERID='" + Integer.parseInt(getRech().getText()) + "' ";

                String req2 = "DELETE FROM chat WHERE  USERID='" + Integer.parseInt(getRech().getText()) + "' ";

                st = cnx.createStatement();
                st.executeUpdate(req);
                st2 = cnx.createStatement();
                st2.executeUpdate(req2);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } else if ("ID".equals(Mrech.getValue().toString())) {

            //******************************
            Statement st = null;
            Statement st1 = null;
            Statement st2 = null;

            try {
                String req = "DELETE FROM ticket WHERE  ID='" + Integer.parseInt(getRech().getText()) + "' ";

                st = cnx.createStatement();
                st.executeUpdate(req);

                String req1 = "DELETE FROM ticketkind WHERE  ID='" + Integer.parseInt(getRech().getText()) + "' ";
                st1 = cnx.createStatement();
                st1.executeUpdate(req1);
                String req2 = "DELETE FROM chat WHERE  ID='" + Integer.parseInt(getRech().getText()) + "' ";
                st2 = cnx.createStatement();
                st2.executeUpdate(req2);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            //************
        } else if ("Categorie".equals(Mrech.getValue().toString())) {

            Statement st = null;
            Statement st1 = null;
            Statement st2 = null;
            Statement st3 = null;

            try {

                ResultSet rs = cnx.createStatement().executeQuery("SELECT * FROM ticket,ticketkind WHERE ticketkind.ID=ticket.ID AND ticketkind.type='" + getRech().getText() + "'  ");

                while (rs.next()) {

                    int s = rs.getInt("ticketkind.ID");

                    String req3 = "DELETE FROM chat WHERE  ID='" + s + "' ";

                    st3 = cnx.createStatement();
                    st3.executeUpdate(req3);

                    String req1 = "DELETE FROM ticketkind WHERE  ID='" + s + "' ";
                    st1 = cnx.createStatement();
                    st1.executeUpdate(req1);

                    String req2 = "DELETE FROM ticket WHERE  ID='" + s + "' ";
                    st2 = cnx.createStatement();
                    st2.executeUpdate(req2);

                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } else if ("STATUS".equals(Mrech.getValue().toString())) {

            Statement st = null;
            Statement st1 = null;
            Statement st2 = null;
            Statement st3 = null;

            try {

                ResultSet rs = cnx.createStatement().executeQuery("SELECT * FROM ticket,ticketkind WHERE ticketkind.ID=ticket.ID AND ticket.STATUS='" + getRech().getText() + "'  ");

                while (rs.next()) {

                    int s = rs.getInt("ticket.ID");

                    String req3 = "DELETE FROM chat WHERE  ID='" + s + "' ";

                    st3 = cnx.createStatement();
                    st3.executeUpdate(req3);

                    String req1 = "DELETE FROM ticketkind WHERE  ID='" + s + "' ";
                    st1 = cnx.createStatement();
                    st1.executeUpdate(req1);

                    String req2 = "DELETE FROM ticket WHERE  ID='" + s + "' ";
                    st2 = cnx.createStatement();
                    st2.executeUpdate(req2);

                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
    }
    }

    @FXML

    private void Solved(ActionEvent event) {
        
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Edit an offer");
        alert1.setHeaderText("Do you really want to Make This Ticket Resolved ?");
        alert1.setContentText("Ticket STATUS will be edited");
        Optional<ButtonType> option = alert1.showAndWait();
        if (option.isPresent() && option.get() == ButtonType.OK) {
        if (!"".equals(getRes().getText())) {

            Statement st = null;
            System.out.println(getRes().getText());
            try {
                String req = "UPDATE `ticket` SET `ReSolverID`='" + getRes().getText() + "',`STATUS`='Resolved' WHERE `ID`='" + Integer.parseInt(ID.getSelectionModel().getSelectedItem()) + "'";

                st = cnx.createStatement();
                st.executeUpdate(req);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } else {
            System.out.println("Ekteb");
        }
    }
    }

    @FXML
    private void Refresh(ActionEvent event) {

        ID.getItems().clear();
        Subject.getItems().clear();
        Description.getItems().clear();
        CreationDate.getItems().clear();
        USERID.getItems().clear();
        STATUS.getItems().clear();
        Categorie.getItems().clear();

        try {

            ResultSet rs = cnx.createStatement().executeQuery("SELECT * FROM ticket,ticketkind WHERE ticket.ID=ticketkind.ID");

            while (rs.next()) {
                ID.getItems().addAll(rs.getString("ticket.ID"));
                Subject.getItems().addAll(rs.getString("ticket.Subject"));
                Description.getItems().addAll(rs.getString("ticket.Description"));
                CreationDate.getItems().addAll(rs.getString("ticket.CreationDate"));
                USERID.getItems().addAll(String.valueOf(rs.getInt("ticket.USERID")));
                STATUS.getItems().addAll(rs.getString("ticket.STATUS"));
                Categorie.getItems().addAll(rs.getString("ticketkind.Type"));

                ID1.setText("");

                USERID1.setText("");
                CreationDate1.setText("");
                Subject1.setText("");
                Description1.setText("");
                Categorie1.setText("");
                STATUS1.setText("");

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    private void RechB(ActionEvent event) {

            ID.getItems().clear();
        Subject.getItems().clear();
        Description.getItems().clear();
        CreationDate.getItems().clear();
        USERID.getItems().clear();
        STATUS.getItems().clear();
        Categorie.getItems().clear();


        if ("USERID".equals(Mrech.getValue().toString() )) {

            list = FXCollections.observableArrayList();

            try {

                ResultSet rs = cnx.createStatement().executeQuery("SELECT * FROM ticket,ticketkind WHERE ticket.ID=ticketkind.ID  AND ticket.USERID='" + Integer.parseInt(getRech().getText()) + "' ");

                while (rs.next()) {

                    list.add(new Ticket(rs.getInt("ticket.ID"), rs.getString("ticket.Subject"), rs.getString("ticket.Description"), rs.getString("ticket.CreationDate"), rs.getString("ticket.ReSolverID"), rs.getInt("ticket.USERID"), rs.getString("ticket.STATUS"), rs.getString("ticketkind.Type")));
                    ID.getItems().addAll(String.valueOf(rs.getInt("ticket.ID")));
                    Subject.getItems().addAll(rs.getString("ticket.Subject"));
                    Description.getItems().addAll(rs.getString("ticket.Description"));
                    CreationDate.getItems().addAll(rs.getString("ticket.CreationDate"));
                    USERID.getItems().addAll(String.valueOf(rs.getInt("ticket.USERID")));
                    STATUS.getItems().addAll(rs.getString("ticket.STATUS"));
                    Categorie.getItems().addAll(rs.getString("ticketkind.Type"));

                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } else if ("ID".equals(Mrech.getValue().toString())) {

            list = FXCollections.observableArrayList();

            try {

                ResultSet rs = cnx.createStatement().executeQuery("SELECT * FROM ticket,ticketkind WHERE ticket.ID=ticketkind.ID  AND ticket.ID='" + Integer.parseInt(getRech().getText()) + "' ");

                while (rs.next()) {

                    list.add(new Ticket(rs.getInt("ticket.ID"), rs.getString("ticket.Subject"), rs.getString("ticket.Description"), rs.getString("ticket.CreationDate"), rs.getString("ticket.ReSolverID"), rs.getInt("ticket.USERID"), rs.getString("ticket.STATUS"), rs.getString("ticketkind.Type")));
                    ID.getItems().addAll(String.valueOf(rs.getInt("ticket.ID")));
                    Subject.getItems().addAll(rs.getString("ticket.Subject"));
                    Description.getItems().addAll(rs.getString("ticket.Description"));
                    CreationDate.getItems().addAll(rs.getString("ticket.CreationDate"));
                    USERID.getItems().addAll(String.valueOf(rs.getInt("ticket.USERID")));
                    STATUS.getItems().addAll(rs.getString("ticket.STATUS"));
                    Categorie.getItems().addAll(rs.getString("ticketkind.Type"));
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } else if ("STATUS".equals(Mrech.getValue().toString())) {

            try {

                ResultSet rs = cnx.createStatement().executeQuery("SELECT * FROM ticket,ticketkind WHERE ticket.ID=ticketkind.ID  AND ticket.STATUS='" + getRech().getText() + "' ");

                while (rs.next()) {

                    ID.getItems().addAll(String.valueOf(rs.getInt("ticket.ID")));
                    Subject.getItems().addAll(rs.getString("ticket.Subject"));
                    Description.getItems().addAll(rs.getString("ticket.Description"));
                    CreationDate.getItems().addAll(rs.getString("ticket.CreationDate"));
                    USERID.getItems().addAll(String.valueOf(rs.getInt("ticket.USERID")));
                    STATUS.getItems().addAll(rs.getString("ticket.STATUS"));
                    Categorie.getItems().addAll(rs.getString("ticketkind.Type"));
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if ("Categorie".equals(Mrech.getValue().toString())) {

             try {

                ResultSet rs = cnx.createStatement().executeQuery("SELECT * FROM ticket,ticketkind WHERE ticket.ID=ticketkind.ID  AND ticketkind.Type='" + getRech().getText() + "' ");

                while (rs.next()) {

                    ID.getItems().addAll(String.valueOf(rs.getInt("ticket.ID")));
                    Subject.getItems().addAll(rs.getString("ticket.Subject"));
                    Description.getItems().addAll(rs.getString("ticket.Description"));
                    CreationDate.getItems().addAll(rs.getString("ticket.CreationDate"));
                    USERID.getItems().addAll(String.valueOf(rs.getInt("ticket.USERID")));
                    STATUS.getItems().addAll(rs.getString("ticket.STATUS"));
                    Categorie.getItems().addAll(rs.getString("ticketkind.Type"));
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

    }

    @FXML
    private void ResB(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Reply.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();

        ReplyController ReplyController = fxmlLoader.getController();
        ReplyController.Useed(String.valueOf(connectedUser.getID()), ID.getSelectionModel().getSelectedItem(), USERID1.getText());
        Stage stage = new Stage();
        stage.setTitle(ID.getSelectionModel().getSelectedItem());
        stage.setScene(new Scene(root1));
        stage.show();

    }

    @FXML
    private void Stat(ActionEvent event) {

        if ("Resolved".equals(Mstat.getValue())) {

            list = FXCollections.observableArrayList();

            try {

                ResultSet rs = cnx.createStatement().executeQuery("SELECT AVG(STATUS='Resolved') As t FROM ticket");

                while (rs.next()) {

                    float s = rs.getFloat("t") * 100;
                    String s1 = Float.toString(s);
                    Stat.setText(s1 + " %");

                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } else if ("Pending".equals(Mstat.getValue())) {

            try {

                ResultSet rs = cnx.createStatement().executeQuery("SELECT AVG(STATUS='Pending') As t FROM ticket");

                while (rs.next()) {

                    float s = rs.getFloat("t") * 100;
                    String s1 = Float.toString(s);
                    Stat.setText(s1 + " %");

                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } else if ("Closed".equals(Mstat.getValue())) {
            try {

                ResultSet rs = cnx.createStatement().executeQuery("SELECT AVG(STATUS='Closed') As t FROM ticket");

                while (rs.next()) {

                    float s = rs.getFloat("t") * 100;
                    String s1 = Float.toString(s);
                    Stat.setText(s1 + " %");

                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    @FXML
    private void Pending(ActionEvent event) {
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Edit an offer");
        alert1.setHeaderText("Do you really want to Make This Ticket Pending ?");
        alert1.setContentText("Ticket STATUS will be edited");
        Optional<ButtonType> option = alert1.showAndWait();
        if (option.isPresent() && option.get() == ButtonType.OK) {

        if (!"".equals(getRes().getText())) {

            Statement st = null;
            System.out.println(getRes().getText());
            try {
                String req = "UPDATE `ticket` SET `ReSolverID`='" + getRes().getText() + "',`STATUS`='Pending' WHERE `ID`='" + Integer.parseInt(ID.getSelectionModel().getSelectedItem()) + "'";

                st = cnx.createStatement();
                st.executeUpdate(req);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } else {
            System.out.println("Ekteb");
        }
    }
    }

    @FXML
    private void cat(ActionEvent event) {
        
        
               Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Edit an offer");
        alert1.setHeaderText("Do you really want to ADD NEW TYPE ?");
        alert1.setContentText("Ticket TYPE will be ADDED");
        Optional<ButtonType> option = alert1.showAndWait();
        if (option.isPresent() && option.get() == ButtonType.OK) {

            if (!cat.getText().isEmpty()) {


            Statement st = null;
            try {

                String req = "INSERT INTO `tickettype`(`Type`) VALUES ('" + cat.getText() + "')";
                st = cnx.createStatement();
                st.executeUpdate(req);

            } catch (SQLException ex) {

            }

            Lcat.getItems().clear();

            try {

                ResultSet rs = cnx.createStatement().executeQuery("SELECT * from tickettype");

                while (rs.next()) {

                    Lcat.getItems().addAll(rs.getString("Type"));

                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    }

    @FXML
    private void SupC(ActionEvent event) {
        
                       Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Edit an offer");
        alert1.setHeaderText("Do you really want to DELETE this TYPE ?");
        alert1.setContentText("Ticket TYPE will be DELETED");
        Optional<ButtonType> option = alert1.showAndWait();
        if (option.isPresent() && option.get() == ButtonType.OK) {

        Statement st = null;
        try {

            String req = "DELETE FROM `tickettype` WHERE Type='" + Lcat.getSelectionModel().getSelectedItem().toString() + "'";
            st = cnx.createStatement();
            st.executeUpdate(req);

        } catch (SQLException ex) {

        }

        Lcat.getItems().clear();

        try {

            ResultSet rs = cnx.createStatement().executeQuery("SELECT * from tickettype");

            while (rs.next()) {

                Lcat.getItems().addAll(rs.getString("Type"));

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    }

    @FXML
    private void OpenLink(ActionEvent event) throws URISyntaxException, IOException {

        System.out.println("Link");
        Desktop.getDesktop().browse(new URI("http://www.google.com/search?q=" + search.getText()));

    }

    @FXML
    private void Closed(ActionEvent event) {
        
        
               Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Edit an offer");
        alert1.setHeaderText("Do you really want to Make This Ticket Closed ?");
        alert1.setContentText("Ticket STATUS will be edited");
        Optional<ButtonType> option = alert1.showAndWait();
        if (option.isPresent() && option.get() == ButtonType.OK) {

        if (!"".equals(getRes().getText())) {

            Statement st = null;
            System.out.println(getRes().getText());
            try {
                String req = "UPDATE `ticket` SET `ReSolverID`='" + getRes().getText() + "',`STATUS`='Closed' WHERE `ID`='" + Integer.parseInt(ID.getSelectionModel().getSelectedItem()) + "'";

                st = cnx.createStatement();
                st.executeUpdate(req);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } else {
            System.out.println("Ekteb");
        }
    }

    }

}
