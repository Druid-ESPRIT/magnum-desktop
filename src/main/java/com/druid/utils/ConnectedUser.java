package com.druid.utils;

import com.druid.models.User;

import java.util.Optional;

public class ConnectedUser {
    private static ConnectedUser instance = null;
    private User user;

    public static ConnectedUser getInstance() {
        if (instance == null) {
            instance = new ConnectedUser();
        }
        return instance;
    }

    private ConnectedUser() {
        user = new User();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
