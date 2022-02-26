/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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

    Connection cnx = MaConnexion.getInstance().getCnx();
    @FXML
    private DatePicker CreationDate;
    @FXML
    private TextField Userid;
    @FXML
    private TextField Subject;
    @FXML
    private Button Aff;
    @FXML
    public TextField test;
    @FXML
    public Label Cat;
    String I,S;
    Ticket T1 = new Ticket();
   

 

    public void Useed(String CI,String CS) {
         Cat.setText(CI+": "+CS);
         T1.setCategorie(CS);
         S=CS;
         I=CI;
         
      //   C1.setIcat(CI);
        // C1.setCat(CS);
         
        
    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public DatePicker getCreationDate() {
        return CreationDate;
    }

    public TextField getUserid() {
        return Userid;
    }

    public TextField getSubject() {
        return Subject;
    }

    // String title = DetTicketController.getTitle();

    // ChoiceBox<Categorie> Tcat1 = new ChoiceBox<Categorie>();
//Used Used;
    @FXML
    private void initialize(ActionEvent event) throws IOException, SQLException {

        System.out.println(S);
        
        if ((!"".equals(Description.getText())) && (!"".equals(Userid.getText())) && (!"".equals(Subject.getText())) && (CreationDate.getValue() != null)) {

            String ID = UUID.randomUUID().toString().substring(24);
            T1.setID(ID);
            T1.setSubject(Subject.getText());
            T1.setDescription(Description.getText());
            //T1.setCreationDate(CreationDate.getValue());!!!!!!!!!!!              
            //res id
            T1.setUSERID(Userid.getText());
            T1.setSTATUS("En attente");
            T1.setReSolverId("");

            //   T1.setCategorie(sa.getCat());
            Statement st = null;
            try { 
               
                String req = "INSERT INTO `ticket`(`ID`, `Subject`, `Description`, `CreationDate`, `ReSolverID`, `USERID`, `STATUS`, `Categorie`) VALUES ('" + T1.getID() + "','" + T1.getSubject() + "','" + T1.getDescription() + "','" + CreationDate.getValue() + "','"+T1.getReSolverId()+"','" + T1.getUSERID() + "','" + T1.getSTATUS() + "','"+T1.getCategorie()+"')";
                st = cnx.createStatement();
                st.executeUpdate(req);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
            
            Statement st1 = null;
            try { 
               
                String req1 ="INSERT INTO `tickettype`(`Icat`, `Cat`) VALUES ('"+I+"','"+S+"')";
                st1 = cnx.createStatement();
                st1.executeUpdate(req1);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
            
            
            
            
              
            

            System.out.println(ID);

        } else {
            System.out.println("Ekteb haja ya bro");
        }
    }

    public TextArea getDescription() {
        return Description;
    }

    @FXML
    private void Aff(ActionEvent event) {

        test.setText(T1.getID());

    }

  

}
