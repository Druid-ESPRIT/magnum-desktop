package com.druid.components;

import com.druid.models.History;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class HistoryCard {
  @FXML private AnchorPane anchorPane;
  @FXML private Text activity;
  @FXML private Text time;
  @FXML private Text description;

  public void load(History hist) {
    activity.setText(String.valueOf(hist.getActivity()));
    description.setText(hist.getDescription());
    time.setText(hist.getTime().toString());
  }
}
