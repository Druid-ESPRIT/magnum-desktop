 
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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

    @FXML
    private Button Aff;
    @FXML
    private TableColumn<Chat,String> ID;
    @FXML
    private TextField IDT;
    @FXML
    private TableView<Chat> Tab;
    @FXML
    private TableColumn<Chat,String> Msg;
    @FXML
    private TableColumn<Chat,String> ReSolverID;
    @FXML
    private TableColumn<Chat,String> DateM;
    @FXML
    private TableColumn<Chat,String> Evaluate;
    
        private ObservableList<Chat> list;
        Connection cnx = MaConnexion.getInstance().getCnx();

    
    
    public TextField getIDT() {
        return IDT;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
    }    

    @FXML
    private void Aff(ActionEvent event) {
        if (!"".equals(getIDT().getText()) ){ 
                    
        
         list = FXCollections.observableArrayList();

        try {

            ResultSet rs = cnx.createStatement().executeQuery("SELECT * FROM chat WHERE ID='"+getIDT().getText()+"'");

            while (rs.next()) {

                list.add(new Chat(rs.getString("ID"),rs.getString("ReSolverID"),rs.getString("Msg"),rs.getString("DateM"),rs.getString("Evaluate")));

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        ReSolverID.setCellValueFactory(new PropertyValueFactory<>("ReSolverID"));
        Msg.setCellValueFactory(new PropertyValueFactory<>("Msg"));
        DateM.setCellValueFactory(new PropertyValueFactory<>("DateM"));
        Evaluate.setCellValueFactory(new PropertyValueFactory<>("Evaluate"));
        
        
        Tab.setItems(list);
        
        
        
    }else{System.out.println("Ekteb");}
    }
    
}
