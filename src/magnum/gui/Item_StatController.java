/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magnum.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import magnum.models.Event;
import magnum.services.EventService;

/**
 * FXML Controller class
 *
 * @author Litai
 */
public class Item_StatController implements Initializable {

    Event event;
    EventService eventService = new EventService();

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
    
    

    @FXML
    private Label event_description;
    @FXML
    private Label event_name;
    @FXML
    private Label income;
    @FXML
    private Label total_participants;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        event_description.setText(this.event.getDescription());
        event_name.setText(this.event.getName());
        
        income.setText(String.valueOf(eventService.incomePerEvent(this.event.getId()))+" DT");
        total_participants.setText(String.valueOf(eventService.numberOfParticipants(this.event.getId())));
        
        
    }    
}
