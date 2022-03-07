package com.druid.controllers;

import com.druid.interfaces.MyListener;
import com.druid.models.Offer;
import com.druid.models.Podcaster;
import com.druid.services.OfferService;
import com.druid.services.PodcasterService;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class PodcasterListController implements Initializable {

  @FXML private GridPane grid;
  @FXML private GridPane grid2;
  @FXML private Pane chosenOffer;

  @FXML private ImageView img;

  @FXML private Label getid;
  @FXML private Pane orderPane;

  @FXML private ScrollPane showOffers;
  PodcasterService us = new PodcasterService();
  OfferService os = new OfferService();
  List<Podcaster> users = us.fetchAll();

  private MyListener myListener;

  @FXML
  void placeOrder(ActionEvent event) {
    chosenOffer.setVisible(false);
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/placeOrder.fxml"));
      Parent root = (Parent) loader.load();

      PlaceOrderController secController = loader.getController();
      secController.myFunction(getid.getText());

      Stage stage = new Stage();
      stage.setScene(new Scene(root));
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void setChosenOffer(Offer offer) {
    String id = String.valueOf(offer.getId());
    getid.setText(id);
    //  tfprice.setText(String.valueOf(offer.getPrice()));
    Path assests = Paths.get("src", "main", "resources", "assets", offer.getImage());
    Image image = new Image(assests.toFile().toURI().toString());
    img.setImage(image);
  }

  private List<Offer> selectedPodcaster(Podcaster podcaster) {
    List<Offer> offers = os.getOffersByUser(podcaster.getID());
    if (offers.size() > 0) {
      setChosenOffer(offers.get(0));
      myListener =
          new MyListener() {
            @Override
            public void onClickListener(Offer offer) {
              chosenOffer.setVisible(true);
              setChosenOffer(offer);
            }

            @Override
            public void onClickListener2(Podcaster podcaster) {}
          };
    }
    int column = 0;
    int row = 1;
    try {
      for (int i = 0; i < offers.size(); i++) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/Item.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();
        ItemController itemController = fxmlLoader.getController();
        itemController.setData(offers.get(i), myListener);
        if (column == 3) {
          column = 0;
          row++;
        }
        grid2.add(anchorPane, column++, row);
        GridPane.setMargin(anchorPane, new Insets(10));
        grid2.setMinWidth(Region.USE_COMPUTED_SIZE);
        grid2.setPrefWidth(Region.USE_COMPUTED_SIZE);
        grid2.setMaxWidth(Region.USE_PREF_SIZE);
        grid2.setMinHeight(Region.USE_COMPUTED_SIZE);
        grid2.setPrefHeight(Region.USE_COMPUTED_SIZE);
        grid2.setMaxHeight(Region.USE_PREF_SIZE);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return offers;
  }

  public void loadData(List<Podcaster> users) {
    if (users.size() > 0) {
      selectedPodcaster(users.get(0));
      myListener =
          new MyListener() {
            @Override
            public void onClickListener(Offer offer) {}

            @Override
            public void onClickListener2(Podcaster podcaster) {
              List<Offer> clofs = os.getOffers();
              clofs.clear();
              showOffers.setVisible(true);
              selectedPodcaster(podcaster);
            }
          };
    }
    int column = 0;
    try {
      for (int i = 0; i < users.size(); i++) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/PodcasterIcon.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();
        PodcasterIconController itemController = fxmlLoader.getController();
        itemController.showPodcaster(users.get(i), myListener);

        grid.add(anchorPane, column++, 1);
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

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    showOffers.setVisible(false);
    chosenOffer.setVisible(false);
    orderPane.setVisible(false);
    loadData(users);
  }
}
