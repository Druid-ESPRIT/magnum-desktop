package com.druid.controllers;

import com.druid.models.Event;
import com.druid.models.Review;
import com.druid.services.EventService;
import com.druid.services.ReviewService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Item_Back_Review implements Initializable {
    List<Review> reviews;
    Event event;
    EventService eventService = new EventService();
    ReviewService reviewService = new ReviewService();
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }


    @FXML
    private VBox review_pnl;
    @FXML
    private Label event_name_review;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        event_name_review.setText(this.event.getName());
        reviews = reviewService.getAll();

        refreshReviews();

    }

    private void refreshReviews()
    {

        reviews.clear();
        reviews = reviewService.getAll();
        review_pnl.getChildren().clear();
        review_pnl.setSpacing(20);

        for(int i = 0; i<reviews.size(); i++)
        {
            if(reviews.get(i).getEvent().getId()==this.event.getId()) {

                Label reviewLabel = new Label();

                reviewLabel.setText(reviews.get(i).getUser().getUsername()+" : "+reviews.get(i).getReview());
                review_pnl.getChildren().add(reviewLabel);
            }

        }

    }


}
