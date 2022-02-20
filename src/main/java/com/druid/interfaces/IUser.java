package com.druid.interfaces;

import com.druid.models.User;

import java.util.List;

public interface IUser {
  public void add(User p);

  public List<User> fetchAll();

  public void update(User u);

  public boolean delete(User u);
}
