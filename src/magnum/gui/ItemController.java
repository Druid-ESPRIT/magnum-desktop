/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magnum.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import magnum.enums.EventStatus;
import magnum.enums.EventType;
import magnum.models.Event;
import magnum.models.User;
import magnum.services.EventService;
import magnum.services.UserService;

/**
 * FXML Controller class
 *
 * @author Litai
 */
public class ItemController implements Initializable {

   public Event event ;
    
    EventService eventService = new EventService();
    UserService userService = new UserService();

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
    
      @FXML
    private Label event_date;

    @FXML
    private Label event_description;

    @FXML
    private Label event_location;

    @FXML
    private Label event_location_label;

    @FXML
    private Label event_name;

    @FXML
    private Label event_price;

    @FXML
    private Label event_status;
    
    @FXML
    private JFXButton participer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
        event_name.setText(this.event.getName());  
        
        if(this.event.isPayant())
        {
            System.out.println(this.event.getPrix());
            event_price.setText(String.valueOf(this.event.getPrix())+" DT");
        }else{
             event_price.setText("Free");
        }
        
        event_date.setText(this.event.getDate().toString());
        
        if(this.event.getStatus()==EventStatus.NOT_FINISHED)
        {
            if(this.event.getDate().before(new java.sql.Date(System.currentTimeMillis()))){
                 event_status.setText("Not Started");
            }
            
        }else
        {
            event_status.setText("Finished");
        }
        
        event_description.setText(this.event.getDescription());
        
        if(this.event.getType()==EventType.LIVE)
        {
            event_location_label.setText("Online Link");
            event_location.setText(this.event.getLocation());
        }else
        {
            event_location_label.setText("Location");
            event_location.setText("Adress : "+this.event.getLocation());
        }
        
        participer.setId(String.valueOf(this.event.getId()));
        
        
    }    
    
    @FXML
    void participate(MouseEvent event)
    {
        User u = userService.getUser(1);
        Event e = eventService.getEvent(this.event.getId());
        if(eventService.userParticipation(e, u)){
              participer.setStyle("-fx-background-color: #C70039");
        participer.setText("Annuler Participation");
        }else{
            eventService.userParticipationCancel(e, u);
            participer.setStyle("-fx-background-color: #50C787");
        participer.setText("Participer");
        }
      
    }
     
    
}
