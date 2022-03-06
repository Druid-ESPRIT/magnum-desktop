package com.druid.utils;

import com.druid.models.Administrator;
import com.druid.models.Podcaster;
import com.druid.models.User;

/**
 * This is a singleton class that holds the user session. <br>
 * <br>
 *
 * <h2>Instantiation</h2>
 *
 * <pre>{@code
 * // Get a live instance of a user/podcaster/administrator.
 * private ConnectedUser connectedUser = ConnectedUser.getInstance();
 * }</pre>
 *
 * <br>
 *
 * <h2>Checking the type of the connected user</h2>
 *
 * <pre>{@code
 * if (connectedUser.isAdministrator() {
 *     // User is an administrator.
 *     // ...
 * } else if (connectedUser.isPodcaster() {
 *     // User is a podcaster.
 *     // ...
 * }
 * }</pre>
 *
 * <br>
 *
 * <h2>Retrieving user information</h2>
 *
 * <pre>{@code
 * connectedUser.getUser().getID(); // 1
 * connectedUser.getUser().getUsername(); // "grtcdr"
 * }</pre>
 */
public class ConnectedUser<T extends User> {
  private static ConnectedUser instance = null;
  private static Class<? extends User> UClass = User.class;
  private T user;

  public ConnectedUser(Class<T> UClass) {
    try {
      this.UClass = UClass;
      this.user = UClass.newInstance();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  public static <T> ConnectedUser getInstance() {
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

  public boolean isAdministrator() {
    return user.getClass().equals(Administrator.class);
  }

  public boolean isPodcaster() {
    return user.getClass().equals(Podcaster.class);
  }

  public boolean isUser() {
    return user.getClass().equals(User.class);
  }
}
