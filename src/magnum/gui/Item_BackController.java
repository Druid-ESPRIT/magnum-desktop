/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magnum.gui;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import magnum.enums.EventStatus;
import magnum.enums.EventType;
import magnum.models.Event;
import magnum.services.EventService;

/**
 * FXML Controller class
 *
 * @author Litai
 */
public class Item_BackController implements Initializable {

   public Event event;
    EventService eventService = new EventService();

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @FXML
    private JFXButton Update;
    @FXML
    private JFXButton Delete;
    @FXML
    private Label event_location_label;
    @FXML
    private Label event_status;
    @FXML
    private TextField event_name;
    @FXML
    private TextField event_price;
    @FXML
    private TextArea event_description;
    @FXML
    private TextField event_location;
    @FXML
    private DatePicker event_date;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        event_name.setText(this.event.getName());

        if (this.event.isPayant()) {
            System.out.println(this.event.getPrix());
            event_price.setText(String.valueOf(this.event.getPrix()));
        } else {
            event_price.setText("Free");
        }

        event_date.setValue(this.event.getDate().toLocalDate());

        if (this.event.getStatus() == EventStatus.NOT_FINISHED) {
            if (this.event.getDate().before(new java.sql.Date(System.currentTimeMillis()))) {
                event_status.setText("Not Started");
            }

        } else {
            event_status.setText("Finished");
        }

        event_description.setText(this.event.getDescription());

        if (this.event.getType() == EventType.LIVE) {
            event_location_label.setText("Online Link");
            event_location.setText(this.event.getLocation());
        } else {
            event_location_label.setText("Location");
            event_location.setText("Adress : " + this.event.getLocation());
        }
        // TODO
    }

    @FXML
    private void Update(ActionEvent event) {
        
         Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to update this event ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        
        if (alert.getResult() == ButtonType.YES) {
            this.event.setName(event_name.getText());
        if(event_price.getText().equals("Free"))
        {
            this.event.setPrix(0);
            this.event.setPayant(false);
        }else
        {
            this.event.setPrix(Double.valueOf(event_price.getText()));
             this.event.setPayant(true);
        }
      
        
        Date date = Date.valueOf(event_date.getValue());
        this.event.setDate(date);
        
        this.event.setDescription(event_description.getText());
         eventService.updateEvent(this.event);
            
        }
        
       
    }

    @FXML
    private void Delete(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to cancel this event ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            eventService.cancelEvent(this.event);
            
        }
    }
    
}
