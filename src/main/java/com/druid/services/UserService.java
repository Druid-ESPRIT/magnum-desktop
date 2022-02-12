package com.druid.services;

import com.druid.enums.UserStatus;
import com.druid.interfaces.IUser;
import com.druid.models.User;
import com.druid.utils.DBConnection;
import com.druid.utils.Debugger;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IUser {
  Connection con = DBConnection.getInstance().getConnection();

  @Override
  public void addUser(User u) {
    // Check that the user being passed doesn't
    // already exist in the database.
    if (this.doesUserExist(u)) {
      return;
    }

    String query =
        "INSERT INTO `Users`(`firstName`, `lastName`, `username`, `email`, `password`, `biography`,"
            + " `avatar`, `status`) VALUES ('"
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

  @Override
  public List<User> getUsers() {
    List<User> users = new ArrayList<>();
    String query = "SELECT * FROM Users";

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

  public boolean doesUserExist(User u) {
    List<User> users = new ArrayList<>();
    String query =
        "SELECT ID, username, email "
            + "FROM Users "
            + "WHERE ID='"
            + u.getID()
            + "' OR "
            + "email='"
            + u.getEmail()
            + "' OR "
            + "username='"
            + u.getUsername()
            + "'";
    try {
      Statement stmt = con.createStatement();
      ResultSet result = stmt.executeQuery(query);
      return result.next();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return false;
  }

  public User findUser(User u) {
    String query = "SELECT * FROM Users WHERE ID = ? OR email = ? OR username = ?";
    try {
      PreparedStatement stmt = con.prepareStatement(query);
      stmt.setInt(1, u.getID());
      stmt.setString(2, u.getEmail());
      stmt.setString(3, u.getUsername());
      ResultSet result = stmt.executeQuery();

      if (result.next()) {
        return new User(
            result.getInt("ID"),
            result.getString("firstName"),
            result.getString("lastName"),
            result.getString("username"),
            result.getString("email"),
            result.getString("password"),
            result.getString("biography"),
            Paths.get(result.getString("avatar")),
            UserStatus.fromString(result.getString("status")));
      } else {
        return null;
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return null;
  }

  @Override
  public void updateUser(User u) {
    String query =
        "UPDATE Users SET "
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
            + "';";

    Debugger.log(query);

    try {
      Statement stmt = con.createStatement();
      stmt.executeUpdate(query);
      Debugger.log("INFO: User (with username='" + u.getUsername() + "') successfully updated.");
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  @Override
  // TODO: Implement this.
  public void deleteUser(User u) {
    if (!this.doesUserExist(u)) {
      Debugger.log("WARN: User (with username='" + u.getUsername() + "') does not exist.");
      return;
    }

    String query = "DELETE FROM Users WHERE username = '" + u.getUsername() + "'";
    try {
      Statement stmt = con.createStatement();
      stmt.executeUpdate(query);
      Debugger.log("INFO: User (with username='" + u.getUsername() + "') successfully deleted.");
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }
}
