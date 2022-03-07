package com.druid.components;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class HistoryCard {
    @FXML
    private Text activity;
    @FXML
    private Text time;
    @FXML
    private Text description;

    public Text getActivity() {
        return activity;
    }

    public void setActivity(Text activity) {
        this.activity = activity;
    }

    public Text getTime() {
        return time;
    }

    public void setTime(Text time) {
        this.time = time;
    }

    public Text getDescription() {
        return description;
    }

    public void setDescription(Text description) {
        this.description = description;
    }
}
