package com.druid.controllers;

import com.druid.enums.EventType;
import com.druid.models.Event;
import com.druid.models.User;
import com.druid.services.EventService;
import com.druid.services.UserService;
import com.druid.utils.ConnectedUser;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

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

    final FileChooser fileChooser = new FileChooser();
    ImageView imageView = new ImageView();
    String path;
    private User connectedUser = ConnectedUser.getInstance().getUser();

    @FXML
    private Button upload;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> types = FXCollections.observableArrayList("LIVE", "PRESENTIEL");
        type.getItems().clear();
        type.setItems(types);
        type.setValue("LIVE");

        if (type.getValue().equals("LIVE")) {
            event_location_label.setText("Online Link");
        } else {
            event_location_label.setText("Address");
        }

        type.addEventHandler(javafx.event.EventType.ROOT, (event) -> {
            if (type.getValue().equals("LIVE")) {
                event_location_label.setText("Online Link");
            } else {
                event_location_label.setText("Address");
            }

        });
        fileChooser.getExtensionFilters().addAll(

                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        upload.setOnAction(event1 -> {
            File file = fileChooser.showOpenDialog(upload.getScene().getWindow());

            if (file != null) {


                try {
                    path = file.getCanonicalFile().getName();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Image image1 = new Image(file.toURI().toString());
                imageView.setImage(image1);
                try {
                    OutputStream out = new FileOutputStream("C:/wamp/www/uploads/"+file.getName());
                    ImageIO.write( SwingFXUtils.fromFXImage(imageView.getImage(),
                            null),"png",out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    void add_event(MouseEvent event) {

        this.event.setUser(connectedUser);
        this.event.setName(event_name.getText());
        if (Double.parseDouble(event_price.getText()) == 0) {
            this.event.setPrix(0);
            this.event.setPayant(false);
        } else {
            this.event.setPrix(Double.parseDouble(event_price.getText()));
            this.event.setPayant(true);
        }
        Date date = Date.valueOf(event_date.getValue());
        this.event.setDate(date);
        this.event.setDescription(event_description.getText());
        this.event.setType(EventType.valueOf(type.getValue()));
        this.event.setLocation(event_location.getText());
        this.event.setImage(path);
        eventService.addEvent(this.event);
    }

}
