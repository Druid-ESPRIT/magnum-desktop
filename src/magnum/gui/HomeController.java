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
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import magnum.models.Event;
import magnum.services.EventService;

/**
 *
 * @author Litai
 */
public class HomeController implements Initializable {

    EventService eventService = new EventService();
    List<Event> events = new ArrayList<>();

    @FXML
    private JFXButton menu_events;

    @FXML
    private VBox pnl_scroll;

    @FXML
    private JFXButton add_event_back;

    @FXML
    private JFXButton list_events_back;

    @FXML
    private JFXButton add_event_back1;

    @FXML
    private JFXButton total_income;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        total_income.setVisible(false);
        events = eventService.getAll();
        refreshNodes();
    }

    private void refreshNodes() {
        events.clear();
        events = eventService.getAll();
        pnl_scroll.getChildren().clear();

        for (int i = 0; i < events.size(); i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Item.fxml"));
                ItemController e = new ItemController();
                e.setEvent(events.get(i));
                loader.setController(e);

                pnl_scroll.getChildren().add((Node) loader.load());

            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void refreshNodesBack() {
        events.clear();
        /* Podcaster */
        events = eventService.getAll(1);
        pnl_scroll.getChildren().clear();
        for (int i = 0; i < events.size(); i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Item_Back.fxml"));
                Item_BackController e = new Item_BackController();
                e.setEvent(events.get(i));
                loader.setController(e);

                pnl_scroll.getChildren().add((Node) loader.load());

            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @FXML
    private void EventsBack(MouseEvent event) throws IOException {
        total_income.setVisible(false);
        refreshNodesBack();

    }

    @FXML
    private void AddEventsBack(MouseEvent event) throws IOException {
        total_income.setVisible(false);
        refreshNodesAdd();

    }

    private void refreshNodesAdd() {
        pnl_scroll.getChildren().clear();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Item_Back_Add.fxml"));
            Item_Back_AddController e = new Item_Back_AddController();
            loader.setController(e);
            pnl_scroll.getChildren().add((Node) loader.load());

        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void EventsList(MouseEvent event) throws IOException {
        total_income.setVisible(false);
        refreshNodes();
    }

    private void refreshNodesStats() {
        events.clear();
        /* Podcaster */
        events = eventService.getAll(1);
        pnl_scroll.getChildren().clear();
        for (int i = 0; i < events.size(); i++) {

            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("Item_Stat.fxml"));

                Item_StatController e = new Item_StatController();
                e.setEvent(events.get(i));
                loader.setController(e);
                pnl_scroll.getChildren().add((Node) loader.load());

            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    @FXML
    void Stats(MouseEvent event) {
        total_income.setVisible(true);
        /* Podcaster*/
        total_income.setText("Total Income From all Events : " + String.valueOf(eventService.totalIncome(1)) + " DT");

        refreshNodesStats();
    }

}
