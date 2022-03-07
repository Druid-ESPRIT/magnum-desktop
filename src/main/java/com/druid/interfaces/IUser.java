package com.druid.interfaces;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.druid.errors.login.BannedUserException;
import com.druid.errors.login.InvalidCredentialsException;
import com.druid.errors.register.EmailTakenException;
import com.druid.errors.register.UsernameTakenException;
import com.druid.models.Administrator;
import com.druid.models.Podcaster;
import com.druid.models.User;
import com.druid.services.AdministratorService;
import com.druid.services.PodcasterService;
import com.druid.services.UserService;
import com.druid.utils.DBConnection;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface IUser<T extends User> {
  Connection con = DBConnection.getInstance().getConnection();

  public static String encrypt(String password) {
    return BCrypt.with(BCrypt.Version.VERSION_2Y).hashToString(12, password.toCharArray());
  }

  public void add(T o) throws EmailTakenException, UsernameTakenException;

  public List<T> fetchAll();

  public void update(T o);

  public boolean delete(T o);

  public static <T extends User> Optional<? extends User> authenticate(T o)
      throws InvalidCredentialsException, BannedUserException {
    if (o.getClass().equals(User.class)) {
      return new UserService().authenticate(o);
    } else if (o.getClass().equals(Administrator.class)) {
      return new AdministratorService().authenticate((Administrator) o);
    } else if (o.getClass().equals(Podcaster.class)) {
      return new PodcasterService().authenticate((Podcaster) o);
    }

    return Optional.empty();
  }
}
