package com.druid.controllers;

import com.druid.models.Event;
import com.druid.services.EventService;
import com.druid.services.ReviewService;
import com.druid.utils.ConnectedUser;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class EventsController implements Initializable {

  @FXML public JFXButton total_income;
  @FXML public JFXButton list_events_back;
  @FXML public JFXButton add_event_back;
  @FXML public JFXButton add_event_back1;
  @FXML public VBox pnl_scroll;

  EventService eventService = new EventService();
  List<Event> events = new ArrayList<>();
  private ConnectedUser connectedUser = ConnectedUser.getInstance();
  ReviewService reviewService = new ReviewService();

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    if (connectedUser.isPodcaster()) {

      total_income.setVisible(false);
      add_event_back.setVisible(true);
      add_event_back1.setVisible(true);
      events = eventService.getAll();
      refreshNodesBack();

    } else if (connectedUser.isUser()) {
      total_income.setVisible(false);
      add_event_back.setVisible(false);
      add_event_back1.setVisible(false);
      events = eventService.getAll();
      refreshNodes();
    }
  }

  private void refreshNodesBack() {
    events.clear();
    events = eventService.getAll();
    pnl_scroll.getChildren().clear();
    for (int i = 0; i < events.size(); i++) {
      if (events.get(i).getUser().getID() == connectedUser.getUser().getID()) {

        try {
          new FXMLLoader();
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Item_Back.fxml"));

          Item_BackController e = new Item_BackController();
          e.setEvent(events.get(i));
          loader.setController(e);

          pnl_scroll.getChildren().add((Node) loader.load());

        } catch (IOException ex) {
          Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
  }

  private void refreshNodesAdd() {

    pnl_scroll.getChildren().clear();
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Item_Back_Add.fxml"));
      Item_Back_AddController e = new Item_Back_AddController();
      loader.setController(e);
      pnl_scroll.getChildren().add((Node) loader.load());

    } catch (IOException ex) {
      Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private void refreshNodesStats() {
    events.clear();
    events = eventService.getAll();
    pnl_scroll.getChildren().clear();

    for (int i = 0; i < events.size(); i++) {
      if (events.get(i).getUser().getID() == connectedUser.getUser().getID()) {
        try {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Item_Stat.fxml"));
          Item_StatController e = new Item_StatController();
          e.setEvent(events.get(i));
          loader.setController(e);

          pnl_scroll.getChildren().add((Node) loader.load());

        } catch (IOException ex) {
          Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
  }

  private void refreshNodes() {
    total_income.setVisible(false);
    events.clear();
    events = eventService.getAll();
    pnl_scroll.getChildren().clear();

    for (int i = 0; i < events.size(); i++) {

      try {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Item_Front.fxml"));
        Item_Front_Controller e = new Item_Front_Controller();
        e.setEvent(events.get(i));
        loader.setController(e);

        pnl_scroll.getChildren().add((Node) loader.load());

      } catch (IOException ex) {
        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  @FXML
  public void EventsBack(MouseEvent mouseEvent) {

    if (connectedUser.isPodcaster()) {
      total_income.setVisible(false);
      add_event_back.setVisible(true);
      add_event_back1.setVisible(true);
      events = eventService.getAll();
      refreshNodesBack();

    } else if (connectedUser.isUser()) {
      add_event_back.setVisible(false);
      add_event_back1.setVisible(false);
      total_income.setVisible(false);
      events = eventService.getAll();
      refreshNodes();
    }
  }

  @FXML
  public void AddEventsBack(MouseEvent mouseEvent) {
    total_income.setVisible(false);
    refreshNodesAdd();
  }

  @FXML
  public void Stats(MouseEvent mouseEvent) {
    total_income.setVisible(true);
    total_income.setText(
        "Total Income From all Events : "
            + String.valueOf(eventService.totalIncome(connectedUser.getUser().getID()))
            + " DT");

    refreshNodesStats();
  }
}
