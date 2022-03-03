package com.druid;

import com.druid.interfaces.MyListener;
import com.druid.models.Offer;
import com.druid.services.OfferService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class offerManagerController implements Initializable {

    @FXML
    private TextField tfsearch;

    @FXML
    private Button newoffer;

    @FXML
    private GridPane grid2;

    @FXML
    private GridPane grid;
    @FXML
    private Label chid;

    private MyListener myListener;
    private Offer offer;
    OfferService o_svc = new OfferService();
    List<Offer> offers = o_svc.getOffers();




    @FXML
    void SearchClicked(ActionEvent event) {
      /*List<Offer> searchedOffers = o_svc.searchOffer(tfsearch.getText());
      grid.getChildren().clear();
      //System.out.println(searchedOffers);
      loadData(searchedOffers);*/
    }

    @FXML
    void btnNewOfferClicked(ActionEvent event)  {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/offerfx.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();

            stage.initModality(Modality.APPLICATION_MODAL);
           // stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("ABC");
            stage.setScene(new Scene(root1));
            stage.show();
        }
     catch (
    IOException ex) {
        ex.printStackTrace();
    }

    }
    private void setChosenOffer(Offer offer) {

       String id = String.valueOf(offer.getId());

        chid.setText(id);
        grid2.getChildren().clear();

        try {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/offerEdit.fxml"));
        AnchorPane anchorPane2 = fxmlLoader.load();

            offerEditController itemController = fxmlLoader.getController();
            itemController.selectedOffer(offer);


        grid2.add(anchorPane2, 1, 1);
        GridPane.setMargin(anchorPane2, new Insets(10));

        grid2.setMinWidth(Region.USE_COMPUTED_SIZE);
        grid2.setPrefWidth(Region.USE_COMPUTED_SIZE);
        grid2.setMaxWidth(Region.USE_PREF_SIZE);


        grid2.setMinHeight(Region.USE_COMPUTED_SIZE);
        grid2.setPrefHeight(Region.USE_COMPUTED_SIZE);
        grid2.setMaxHeight(Region.USE_PREF_SIZE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData(List<Offer> offers){

        if (offers.size() > 0) {
            setChosenOffer(offers.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Offer offer) {
                    setChosenOffer(offer);
                }
            };
        }

        grid2.getColumnConstraints().clear();
        grid2.getChildren().clear();
        int column =0;
        int row =1;

        try {
            for (int i = 0; i < offers.size();i++) {
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
    public void refreshData(){
        grid.getChildren().clear();
        offers.clear();
    }
    public void listenForSearchInput(){
        tfsearch.textProperty().addListener((observable, previouslySearched, searchInput) -> {
            if (!searchInput.isEmpty()) {
                refreshData();
                loadData(o_svc.searchOffer(searchInput));
            }
            else {
                loadData(offers);
            }
        });

    }

@Override
    public void initialize(URL location, ResourceBundle resources) {
    loadData(offers);
    listenForSearchInput();

}
}
