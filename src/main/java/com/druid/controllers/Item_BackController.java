package com.druid.controllers;

import com.druid.enums.EventStatus;
import com.druid.enums.EventType;
import com.druid.models.Event;
import com.druid.services.EventService;
import com.druid.utils.ConnectedUser;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Item_BackController implements Initializable {
  public Event event;
  EventService eventService = new EventService();

  public Event getEvent() {
    return event;
  }

  public void setEvent(Event event) {
    this.event = event;
  }

  @FXML private JFXButton Update;
  @FXML private JFXButton Delete;
  @FXML private Label event_location_label;
  @FXML private Label event_status;
  @FXML private TextField event_name;
  @FXML private TextField event_price;
  @FXML private TextArea event_description;
  @FXML private TextField event_location;
  @FXML private DatePicker event_date;
  @FXML private Hyperlink hyperlink;
  @FXML private JFXButton show_reviews_btn;

  private ConnectedUser connectedUser = ConnectedUser.getInstance();

  /** Initializes the controller class. */
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
  }

  @FXML
  private void Update(ActionEvent event) {

    Alert alert =
        new Alert(
            Alert.AlertType.CONFIRMATION,
            "Are you sure you want to update this event ?",
            ButtonType.YES,
            ButtonType.NO);
    alert.showAndWait();

    if (alert.getResult() == ButtonType.YES) {
      this.event.setName(event_name.getText());
      if (event_price.getText().equals("Free")) {
        this.event.setPrix(0);
        this.event.setPayant(false);
      } else {
        this.event.setPrix(Double.parseDouble(event_price.getText()));
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
    Alert alert =
        new Alert(
            Alert.AlertType.CONFIRMATION,
            "Are you sure you want to cancel this event ?",
            ButtonType.YES,
            ButtonType.NO);
    alert.showAndWait();

    if (alert.getResult() == ButtonType.YES) {
      eventService.cancelEvent(this.event);
    }
  }

  @FXML
  public void get_reviews(MouseEvent event) {
    if (this.event.getUser().getID() == connectedUser.getUser().getID()) {

      try {
        new FXMLLoader();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Item_Back_Review.fxml"));

        Item_Back_Review e = new Item_Back_Review();
        e.setEvent(this.event);
        loader.setController(e);

        // loader.load();

        Parent root1 = (Parent) loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Reviews");
        stage.setScene(new Scene(root1));
        stage.show();

      } catch (IOException ex) {
        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
}
