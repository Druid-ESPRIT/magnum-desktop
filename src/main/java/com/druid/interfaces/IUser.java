package com.druid.interfaces;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.druid.enums.UserTypes;
import com.druid.models.User;
import java.util.List;
import java.util.Optional;

public interface IUser<T extends User> {
  public static String encrypt(String password) {
    return BCrypt.with(BCrypt.Version.VERSION_2Y).hashToString(12, password.toCharArray());
  }

  public void add(T o);

  public List<T> fetchAll();

  public void update(T o);

  public boolean delete(T o);

  public Optional<T> fetchByType(T o, UserTypes t);
}
