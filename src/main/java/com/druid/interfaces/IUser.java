package com.druid.interfaces;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.druid.errors.register.EmailTakenException;
import com.druid.errors.register.UsernameTakenException;
import com.druid.models.User;
import com.druid.utils.DBConnection;
import java.sql.Connection;
import java.util.List;

public interface IUser<T extends User> {
  Connection con = DBConnection.getInstance().getConnection();

  public static String encrypt(String password) {
    return BCrypt.with(BCrypt.Version.VERSION_2Y).hashToString(12, password.toCharArray());
  }

  public void add(T o) throws EmailTakenException, UsernameTakenException;

  public List<T> fetchAll();

  public void update(T o);

  public boolean delete(T o);
}
