package com.druid.controllers;

import com.druid.interfaces.MyListener;
import com.druid.models.Offer;
import com.druid.services.OfferService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class offerManagerController implements Initializable {

  OfferService o_svc = new OfferService();
  List<Offer> offers = o_svc.getOffers();
  @FXML private Button newoffer;
  @FXML private AnchorPane anchorPane;
  @FXML private TextArea tfdesc1;
  @FXML private TextField tfprice1;
  @FXML private Label filechosen1;
  @FXML private Button addbutton1;
  @FXML private VBox myVbox;
  @FXML private AnchorPane anchorPane2;
  @FXML private TextArea tfdesc;
  @FXML private Label filechosen;
  @FXML private Button addbutton;
  @FXML private TextField tfprice;
  @FXML private Button deleteButton;
  @FXML private Label getid;
  @FXML private Label chid;
  @FXML private TextField tfsearch;
  @FXML private GridPane grid;
  private MyListener myListener;
  private Offer offer;

  @FXML
  void btnNewOfferClicked(ActionEvent event) {
    myVbox.setVisible(false);
    anchorPane.setVisible(true);
    anchorPane2.setVisible(false);
  }

  @FXML
  void CancelAdd(ActionEvent event) {
    anchorPane.setVisible(false);
    myVbox.setVisible(true);
  }

  @FXML
  void CancelSelect(ActionEvent event) {
    anchorPane2.setVisible(false);
  }

  private void setChosenOffer(Offer offer) {
    String id = String.valueOf(offer.getId());
    getid.setText(id);
    tfdesc.setText(offer.getDescription());
    tfprice.setText(String.valueOf(offer.getPrice()));
    filechosen.setText(offer.getImage());
  }

  @FXML
  void fileselector(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    fileChooser
        .getExtensionFilters()
        .addAll(
            new FileChooser.ExtensionFilter("All Images", "*.*"),
            new FileChooser.ExtensionFilter("JPG", "*.jpg"),
            new FileChooser.ExtensionFilter("PNG", "*.png"));
    Stage stage = (Stage) anchorPane2.getScene().getWindow();
    File file = fileChooser.showOpenDialog(stage);
    if (file != null) {
      filechosen.setText(file.getName());
    }
  }

  @FXML
  void fileselector1(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    fileChooser
        .getExtensionFilters()
        .addAll(
            new FileChooser.ExtensionFilter("All Images", "*.*"),
            new FileChooser.ExtensionFilter("JPG", "*.jpg"),
            new FileChooser.ExtensionFilter("PNG", "*.png"));
    Stage stage = (Stage) anchorPane.getScene().getWindow();
    File file = fileChooser.showOpenDialog(stage);
    if (file != null) {
      filechosen1.setText(file.getName());
    }
  }

  public void loadData(List<Offer> offers) {

    if (offers.size() > 0) {
      setChosenOffer(offers.get(0));
      myListener =
          new MyListener() {
            @Override
            public void onClickListener(Offer offer) {
              setChosenOffer(offer);
              anchorPane2.setVisible(true);
              anchorPane.setVisible(false);
              myVbox.setVisible(true);
            }
          };
    }

    int column = 0;
    int row = 1;

    try {
      for (int i = 0; i < offers.size(); i++) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/item.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();
        ItemController itemController = fxmlLoader.getController();
        itemController.setData(offers.get(i), myListener);
        if (column == 3) {
          column = 0;
          row++;
        }
        grid.add(anchorPane, column++, row);
        GridPane.setMargin(anchorPane, new Insets(10));
        grid.setMinWidth(Region.USE_COMPUTED_SIZE);
        grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
        grid.setMaxWidth(Region.USE_PREF_SIZE);
        grid.setMinHeight(Region.USE_COMPUTED_SIZE);
        grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
        grid.setMaxHeight(Region.USE_PREF_SIZE);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void refreshData() {
    grid.getChildren().clear();
    offers.clear();
  }

  public void listenForSearchInput() {
    tfsearch
        .textProperty()
        .addListener(
            (observable, previouslySearched, searchInput) -> {
              if (!searchInput.isEmpty()) {
                refreshData();
                loadData(o_svc.searchOffer(searchInput));
              } else {
                loadData(offers);
              }
            });
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    anchorPane.setVisible(false);
    anchorPane2.setVisible(false);
    anchorPane2.managedProperty().bind(anchorPane2.visibleProperty());
    loadData(offers);
    listenForSearchInput();
    addbutton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent e) {
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Edit an offer");
            alert1.setHeaderText("Do you really want to edit your offer ?");
            alert1.setContentText("Offer will be Edited");
            Optional<ButtonType> option = alert1.showAndWait();
            if (option.isPresent() && option.get() == ButtonType.OK) {
              Offer o =
                  new Offer(
                      10,
                      Float.parseFloat(tfprice.getText()),
                      tfdesc.getText(),
                      filechosen.getText());
              o_svc.updateOffer(o, Integer.parseInt(getid.getText()));
              refreshData();
              loadData(o_svc.getOffers());
              anchorPane2.setVisible(false);
            }
          }
        });
    deleteButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent e) {
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Delete an offer");
            alert1.setHeaderText("Do you really want to delete your offer ?");
            alert1.setContentText("Offer will be deleted");
            Optional<ButtonType> option = alert1.showAndWait();
            if (option.isPresent() && option.get() == ButtonType.OK) {
              Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
              alert.showAndWait();
              o_svc.deleteOffer(Integer.parseInt(getid.getText()));
              refreshData();
              loadData(o_svc.getOffers());
              anchorPane2.setVisible(false);
            }
          }
        });
    addbutton1.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent e) {
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Add an offer");
            alert1.setHeaderText("Do you really want to add this offer ?");
            alert1.setContentText("Offer will be add");
            Optional<ButtonType> option = alert1.showAndWait();
            if (option.isPresent() && option.get() == ButtonType.OK) {
              Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
              alert.showAndWait();
              Offer o =
                  new Offer(
                      10,
                      Float.parseFloat(tfprice1.getText()),
                      tfdesc1.getText(),
                      filechosen1.getText());
              o_svc.addOffer(o);
              tfprice1.setText("");
              tfdesc1.setText("");
              filechosen1.setText("");
              refreshData();
              loadData(o_svc.getOffers());
              anchorPane.setVisible(false);
              myVbox.setVisible(true);
            }
          }
        });
  }
}
