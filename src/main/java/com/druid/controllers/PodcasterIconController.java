package com.druid.controllers;

import com.druid.interfaces.MyListener;
import com.druid.models.Podcaster;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class PodcasterIconController {

  @FXML private ImageView avatar;
  private Podcaster podcaster;
  private MyListener myListener;

  @FXML
  void podcasterSelected(MouseEvent event) {
    myListener.onClickListener2(podcaster);
  }

  public void showPodcaster(Podcaster podcaster, MyListener myListener) {
    this.myListener = myListener;
    this.podcaster = podcaster;
    Path assests = Paths.get("src", "main", "resources", "assets", "avatars", "blank.jpg");
    Image image = new Image(assests.toFile().toURI().toString());
    avatar.setImage(image);
  }
}
