package com.druid.controllers;/*
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

import com.druid.models.Chat;
import com.druid.models.Ticket;
import com.druid.models.User;
import com.druid.utils.ConnectedUser;
import com.druid.utils.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import static jdk.nashorn.internal.objects.NativeString.substring;

/**
 * FXML Controller class
 *
 * @author user
 */





       
      



public class ReplyController implements Initializable {

    @FXML
    private TextField RID;
    @FXML
    private TextField TID;
    @FXML
    private Button Env;
    
        private ObservableList<Chat> list;
        Connection cnx = DBConnection.getInstance().getConnection();

    @FXML
    private TableView<Chat> Tab;
    private TableColumn<Chat,String> Ifrom;
    @FXML
    private TableColumn<Chat,String> Msg;
    @FXML
    private TextField Rmsg;
    @FXML
    private TextField UID;
    @FXML
    private TableColumn<Chat,String> Tmp;
    @FXML
    private Label Res;
    private User connectedUser = ConnectedUser.getInstance().getUser();

    Chat c1=new Chat();

    /**
     * Initializes the controller class.
     */
    
    
       void Useed(String text, String text0,String text00) {
         //Res.setText(connectedUser.getUsername());
        //   RID.setText(text);

           c1.setReSolverID(text);

           TID.setText(text0);
           c1.setID(text0);


           UID.setText(text00);
           c1.setUSERID(text00);
           

           list = FXCollections.observableArrayList();

        try {

            ResultSet rs = cnx.createStatement().executeQuery("SELECT * FROM chat WHERE ID='"+Integer.parseInt(text0)+"'");

            while (rs.next()) {
                      

 String s = "["+rs.getString("Ifrom")+"]: "+rs.getString("Msg");
                 list.add(new Chat(s,rs.getString("DateM")));
         
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        Msg.setCellValueFactory(new PropertyValueFactory<>("Msg"));
        Tmp.setCellValueFactory(new PropertyValueFactory<>("DateM"));
        
        Tab.setItems(list);
           
           
           
          }
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        
    }    

    @FXML
    private void Env(ActionEvent event) {
       // System.out.println(RID.getText());
        
        
        
           Statement st = null;
            try {
                String req ="INSERT INTO `chat`(`ID`, `ReSolverID`, `Msg`, `DateM`,`USERID`, `Ifrom`) VALUES ('"+Integer.parseInt(c1.getID())+"','"+c1.getReSolverID()+"','"+Rmsg.getText()+"',NOW(),'"+Integer.parseInt(c1.getUSERID())+"','Resolver')";

                st = cnx.createStatement();
                st.executeUpdate(req);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
            
            
            
            
            //#############################
                list = FXCollections.observableArrayList();

      
        try {

            ResultSet rs = cnx.createStatement().executeQuery("SELECT * FROM chat WHERE ID='"+Integer.parseInt(c1.getID())+"'");

            while (rs.next()) {
                      

 String s = "["+rs.getString("Ifrom")+"]: "+rs.getString("Msg");
                 list.add(new Chat(s,rs.getString("DateM")));
         
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        Msg.setCellValueFactory(new PropertyValueFactory<>("Msg"));
        Tmp.setCellValueFactory(new PropertyValueFactory<>("DateM"));
        
        Tab.setItems(list);
        
    }

    

 
    
}
