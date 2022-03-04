package com.druid.utils;

import com.druid.models.User;

/**
 * This is a singleton class that holds the user session.
 * <br>
 * <br>
 * <h2>Usage</h2>
 * <pre>{@code
 * // Instantiation
 * private User connectedUser = ConnectedUser.getInstance().getUser();
 *
 * // Retrieving information
 * connectedUser.getID(); // 1
 * connectedUser.getUsername(); // "grtcdr"
 * </pre>
 */
public class ConnectedUser {
    private static ConnectedUser instance = null;
    private User user;

    private ConnectedUser() {
        user = new User();
    }

    public static ConnectedUser getInstance() {
        if (instance == null) {
            instance = new ConnectedUser();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
