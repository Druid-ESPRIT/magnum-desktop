package com.druid.controllers;

import com.druid.interfaces.MyListener;
import com.druid.models.User;
import com.druid.utils.Debugger;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PodcasterIconController {

    @FXML
    private ImageView avatar;
    private User user;
    private MyListener myListener;


    @FXML
    void podcasterSelected(MouseEvent event) {
        myListener.onClickListener2(user);

    }
    public void showPodcaster(User user,MyListener myListener){
          this.myListener =myListener;
        this.user = user;
        Path assests = Paths.get("src","main","resources","assets","avatars" ,user.getAvatar().toString());
        Image image = new Image(assests.toFile().toURI().toString());
        avatar.setImage(image);
    }
}

