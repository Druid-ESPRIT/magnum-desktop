package com.druid.controllers;

import com.druid.models.Event;
import com.druid.services.EventService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

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
