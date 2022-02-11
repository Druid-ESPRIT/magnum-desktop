package com.druid.interfaces;

import com.druid.models.User;
import java.util.List;

public interface IUser {
  public void addUser(User p);

  public List<User> getUsers();

  public boolean checkIfUserExists(User u);

  public void updateUser(User u);

  public void deleteUser(User u);
}
