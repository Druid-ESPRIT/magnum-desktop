package com.druid.services;

import com.druid.enums.UserStatus;
import com.druid.interfaces.IUser;
import com.druid.models.Token;
import com.druid.models.User;
import com.druid.utils.DBConnection;
import com.druid.utils.Debugger;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class UserService implements IUser {
  Connection con = DBConnection.getInstance().getConnection();

  public void add(User u) {
    // Check that the user being passed doesn't
    // already exist in the database.
    if (this.fetchOne(u).isPresent()) {
      return;
    }

    String query =
        "INSERT INTO `Users` (`firstName`, `lastName`, `username`, `email`, `password`,"
            + " `biography`, `avatar`, `status`) VALUES ('"
            + u.getFirstName()
            + "','"
            + u.getLastName()
            + "','"
            + u.getUsername()
            + "','"
            + u.getEmail()
            + "' ,'"
            + u.getPassword()
            + "','"
            + u.getBiography()
            + "','"
            + u.getAvatar()
            + "','"
            + u.getStatus().toString()
            + "')";

    try {
      Statement stmt = con.createStatement();
      stmt.executeUpdate(query);
      Debugger.log("INFO: User (with username='" + u.getUsername() + "') successfully added.");
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public List<User> fetchAll() {
    List<User> users = new ArrayList<>();
    String query = "SELECT * FROM `Users`";

    try {
      Statement stmt = con.createStatement();
      ResultSet result = stmt.executeQuery(query);

      while (result.next()) {
        users.add(
            new User(
                result.getInt("ID"),
                result.getString("firstName"),
                result.getString("lastName"),
                result.getString("username"),
                result.getString("email"),
                result.getString("password"),
                result.getString("biography"),
                Paths.get(result.getString("avatar")),
                UserStatus.fromString(result.getString("status"))));
      }

      return users;
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return null;
  }

  public Optional<User> fetchOne(User u) {
    String query = "SELECT * FROM `Users` WHERE `ID` = ? OR `username` = ? OR `email` = ?";
    try {
      PreparedStatement stmt = con.prepareStatement(query);
      stmt.setInt(1, u.getID());
      stmt.setString(2, u.getUsername());
      stmt.setString(3, u.getEmail());
      ResultSet result = stmt.executeQuery();

      if (result.next()) {
        return Optional.of(
            new User(
                result.getInt("ID"),
                result.getString("firstName"),
                result.getString("lastName"),
                result.getString("username"),
                result.getString("email"),
                result.getString("password"),
                result.getString("biography"),
                Paths.get(result.getString("avatar")),
                UserStatus.fromString(result.getString("status"))));
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return Optional.empty();
  }

  public void resetPassword(Token inputToken, User user) {
    TokenService token_svc = new TokenService();
    Token token = token_svc.get(inputToken, user).orElse(null);

    if (token == null) {
      Debugger.log("The provided token is not valid.");
      return;
    }

    Date date = new Date();
    Timestamp now = new Timestamp(date.getTime());
    long diff = TimeUnit.MILLISECONDS.toHours(now.getTime() - token.getCreated().getTime());

    // Verify that the token hasn't
    // reached its maximum lifetime.
    if (diff > 24) {
      Debugger.log("The provided token is expired.");
      return;
    }

    // Verify that the token hasn't
    // already been consumed.
    if (token.isConsumed()) {
      Debugger.log("The provided token is no longer valid.");
      return;
    }

    token.setConsumed(true);
    token_svc.update(token);
    this.update(user);
  }

  public void update(User u) {
    String query =
        "UPDATE `Users` SET "
            + "`firstName` = '"
            + u.getFirstName()
            + "', "
            + "`lastName` = '"
            + u.getLastName()
            + "', "
            + "`email`= '"
            + u.getEmail()
            + "', "
            + "`password` = '"
            + u.getPassword()
            + "', "
            + "`biography` = '"
            + u.getBiography()
            + "', "
            + "`avatar` = '"
            + u.getAvatar()
            + "', "
            + "`status` = '"
            + u.getStatus().toString()
            + "' "
            + "WHERE `username` = '"
            + u.getUsername()
            + "'";

    try {
      Statement stmt = con.createStatement();
      stmt.executeUpdate(query);
      Debugger.log("INFO: User (with username='" + u.getUsername() + "') successfully updated.");
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public void delete(User u) {
    if (!this.fetchOne(u).isPresent()) {
      Debugger.log("WARN: User (with username='" + u.getUsername() + "') does not exist.");
      return;
    }

    String query = "DELETE FROM `Users` WHERE `username` = '" + u.getUsername() + "'";
    try {
      Statement stmt = con.createStatement();
      stmt.executeUpdate(query);
      Debugger.log("INFO: User (with username='" + u.getUsername() + "') successfully deleted.");
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }
}
