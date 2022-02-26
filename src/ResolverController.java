/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author user
 */
public class ResolverController implements Initializable {

    @FXML
    private TableView<Ticket> Tab;
    @FXML
    private TableColumn<Ticket, String> ID;
    @FXML
    private TableColumn<Ticket, String> Subject;
    @FXML
    private TableColumn<Ticket, String> CreationDate;
    @FXML
    private TableColumn<Ticket, String> ReSolverID;
    @FXML
    private TableColumn<Ticket, String> USERID;
    @FXML
    private TableColumn<Ticket, String> STATUS;
    @FXML
    private TableColumn<Ticket, String> Description;
    @FXML
    private TableColumn<Ticket, String> Categorie;
    @FXML
    private TextField Res;

    @FXML
    private Button SuppB;
    @FXML
    public TextField Supp;
    @FXML
    private ChoiceBox<String> Msup;
    @FXML
    private TextField IDT;
    @FXML
    private Button Solved;
    @FXML
    private TextArea Rep;
    @FXML
    private Button Refresh;
    @FXML
    private Button RechB;
    @FXML
    private ChoiceBox<String> Mrech;
    @FXML
    private TextField Rech;

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
    Connection cnx = MaConnexion.getInstance().getCnx();

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

        Msup.getItems().add("ID");
        Msup.getItems().add("USERID");
        Mrech.getItems().add("ID");
        Mrech.getItems().add("USERID");
        list = FXCollections.observableArrayList();

        try {

            ResultSet rs = cnx.createStatement().executeQuery("SELECT * FROM `ticket`");

            while (rs.next()) {

                list.add(new Ticket(rs.getString("ID"), rs.getString("Subject"), rs.getString("Description"), rs.getString("CreationDate"), rs.getString("ReSolverID"), rs.getString("USERID"), rs.getString("STATUS"), rs.getString("Categorie")));

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        Subject.setCellValueFactory(new PropertyValueFactory<>("Subject"));
        Description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        CreationDate.setCellValueFactory(new PropertyValueFactory<>("CreationDate"));

        ReSolverID.setCellValueFactory(new PropertyValueFactory<>("ReSolverID"));

        USERID.setCellValueFactory(new PropertyValueFactory<>("USERID"));
        STATUS.setCellValueFactory(new PropertyValueFactory<>("STATUS"));
        Categorie.setCellValueFactory(new PropertyValueFactory<>("Categorie"));

        Tab.setItems(list);

        // TODO
    }

    @FXML
    private void SuppB(ActionEvent event) {
        if ("USERID".equals(Msup.getValue().toString())) {

            Statement st = null;
            try {
                String req = "DELETE FROM ticket WHERE USERID ='" + getSupp().getText() + "'";

                st = cnx.createStatement();
                st.executeUpdate(req);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } else if ("ID".equals(Msup.getValue().toString())) {

            Statement st = null;
            try {
                String req = "DELETE FROM ticket WHERE ID ='" + getSupp().getText() + "'";

                st = cnx.createStatement();
                st.executeUpdate(req);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

    }

    @FXML
    
    private void Solved(ActionEvent event) {
        if (!"".equals(getRes().getText()) && !"".equals(getIDT().getText()) && !"".equals(getRep().getText())  ) {

            Statement st = null;
            System.out.println(getRes().getText());
            try {
                String req = "UPDATE `ticket` SET `ReSolverID`='" + getRes().getText() + "',`STATUS`='Solved' WHERE `ID`='" + getIDT().getText() + "'";

                st = cnx.createStatement();
                st.executeUpdate(req);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
            
            
          
           
           //reponse

             Statement st1 = null;
            
            try {
                String req1 = "INSERT INTO `chat`(`ID`, `ReSolverID`, `Msg`, `DateM`, `Evaluate`) VALUES ('"+getIDT().getText()+"','"+getRes().getText()+"','"+getRep().getText()+"','Date','Mazel')";

                st1 = cnx.createStatement();
                st1.executeUpdate(req1);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
            
            
        }else{System.out.println("Ekteb");}
    }

    @FXML
    private void Refresh(ActionEvent event) {
        
         
        list = FXCollections.observableArrayList();

        try {

            ResultSet rs = cnx.createStatement().executeQuery("SELECT * FROM `ticket`");

            while (rs.next()) {

                list.add(new Ticket(rs.getString("ID"), rs.getString("Subject"), rs.getString("Description"), rs.getString("CreationDate"), rs.getString("ReSolverID"), rs.getString("USERID"), rs.getString("STATUS"), rs.getString("Categorie")));

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        Subject.setCellValueFactory(new PropertyValueFactory<>("Subject"));
        Description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        CreationDate.setCellValueFactory(new PropertyValueFactory<>("CreationDate"));

        ReSolverID.setCellValueFactory(new PropertyValueFactory<>("ReSolverID"));

        USERID.setCellValueFactory(new PropertyValueFactory<>("USERID"));
        STATUS.setCellValueFactory(new PropertyValueFactory<>("STATUS"));
        Categorie.setCellValueFactory(new PropertyValueFactory<>("Categorie"));

        Tab.setItems(list);
    }

    @FXML
    private void RechB(ActionEvent event) {
        
        
        
        if ("USERID".equals(Mrech.getValue().toString())) {
            
           
            
            
            list = FXCollections.observableArrayList();

        try {

            ResultSet rs = cnx.createStatement().executeQuery("SELECT * FROM ticket WHERE USERID='"+getRech().getText()+"'");

            while (rs.next()) {

                list.add(new Ticket(rs.getString("ID"), rs.getString("Subject"), rs.getString("Description"), rs.getString("CreationDate"), rs.getString("ReSolverID"), rs.getString("USERID"), rs.getString("STATUS"), rs.getString("Categorie")));

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        Subject.setCellValueFactory(new PropertyValueFactory<>("Subject"));
        Description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        CreationDate.setCellValueFactory(new PropertyValueFactory<>("CreationDate"));

        ReSolverID.setCellValueFactory(new PropertyValueFactory<>("ReSolverID"));

        USERID.setCellValueFactory(new PropertyValueFactory<>("USERID"));
        STATUS.setCellValueFactory(new PropertyValueFactory<>("STATUS"));
        Categorie.setCellValueFactory(new PropertyValueFactory<>("Categorie"));

        Tab.setItems(list);
        
        
        
        }else if("ID".equals(Mrech.getValue().toString())){
        
        
            list = FXCollections.observableArrayList();

        try {

            ResultSet rs = cnx.createStatement().executeQuery("SELECT * FROM ticket WHERE ID='"+getRech().getText()+"'");

            while (rs.next()) {

                list.add(new Ticket(rs.getString("ID"), rs.getString("Subject"), rs.getString("Description"), rs.getString("CreationDate"), rs.getString("ReSolverID"), rs.getString("USERID"), rs.getString("STATUS"), rs.getString("Categorie")));

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        Subject.setCellValueFactory(new PropertyValueFactory<>("Subject"));
        Description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        CreationDate.setCellValueFactory(new PropertyValueFactory<>("CreationDate"));

        ReSolverID.setCellValueFactory(new PropertyValueFactory<>("ReSolverID"));

        USERID.setCellValueFactory(new PropertyValueFactory<>("USERID"));
        STATUS.setCellValueFactory(new PropertyValueFactory<>("STATUS"));
        Categorie.setCellValueFactory(new PropertyValueFactory<>("Categorie"));

        Tab.setItems(list);
        
        }
           
        }
        
        
    }

    
    


