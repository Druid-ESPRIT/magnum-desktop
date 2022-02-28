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
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import magnum.models.Event;
import magnum.models.User;
import magnum.services.EventService;
import magnum.services.UserService;

/**
 * FXML Controller class
 *
 * @author Litai
 */
public class Item_Back_AddController implements Initializable {

    Event event = new Event();

    @FXML
    private JFXButton Add_event_btn;
    @FXML
    private Label event_location_label;
    @FXML
    private TextField event_name;
    @FXML
    private TextField event_price;
    @FXML
    private DatePicker event_date;
    @FXML
    private TextArea event_description;
    @FXML
    private TextField event_location;
    @FXML
    private ComboBox<String> type;

    EventService eventService = new EventService();
    UserService userService = new UserService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> types = FXCollections.observableArrayList("LIVE", "PRESENTIEL");
        type.getItems().clear();
        type.setItems(types);
        type.setValue("LIVE");

        if (type.getValue().equals("LIVE")) {
            event_location_label.setText("Online Link");
        } else {
            event_location_label.setText("Location");
        }

        type.addEventHandler(EventType.ROOT, (event) -> {
            if (type.getValue().equals("LIVE")) {
                event_location_label.setText("Online Link");
            } else {
                event_location_label.setText("Location");
            }

        });

    }

    @FXML
    void add_event(MouseEvent event) {
        /* Podcaster */
        User u = userService.getUser(1);
        this.event.setUser(u);
        this.event.setName(event_name.getText());
        if (Double.valueOf(event_price.getText()) == 0) {
            this.event.setPrix(0);
            this.event.setPayant(false);
        } else {
            this.event.setPrix(Double.valueOf(event_price.getText()));
            this.event.setPayant(true);
        }
        Date date = Date.valueOf(event_date.getValue());
        this.event.setDate(date);
        this.event.setDescription(event_description.getText());
        this.event.setType(magnum.enums.EventType.valueOf(type.getValue()));
        this.event.setLocation(event_location.getText());
        eventService.addEvent(this.event);
    }
    
}
