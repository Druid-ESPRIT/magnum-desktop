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
public class ConnectedUser<T extends User> {
    private static ConnectedUser instance = null;
    private T user;

    public ConnectedUser(Class<T> UClass) {
        try {
            this.user = UClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static <T> ConnectedUser getInstance(Class<T> UClass) {
        if (instance == null) {
            instance = new ConnectedUser(UClass);
        }
        return instance;
    }

    public T getUser() {
        return this.user;
    }

    public void setUser(T user) {
        this.user = user;
    }
}
