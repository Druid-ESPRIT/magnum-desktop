package com.druid.services;

// This service is modeled after class-table
// inheritance and borrows ideas found in:
// https://docs.oracle.com/cd/E28280_01/apirefs.1111/e13946/ejb3_overview_mapping_inher.html#ejb3_overview_mapping_inher_single

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.druid.enums.UserStatus;
import com.druid.enums.UserTypes;
import com.druid.interfaces.IUser;
import com.druid.models.Podcaster;
import com.druid.models.Token;
import com.druid.models.User;
import com.druid.utils.DBConnection;
import com.druid.utils.Debugger;
import com.druid.utils.QuickHistory;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PodcasterService implements IUser<Podcaster> {
  Connection con = DBConnection.getInstance().getConnection();

  public Optional<Podcaster> fetchByType(Podcaster podcaster, UserTypes type) {
    if (type == UserTypes.USER) {
      return fetchOne(podcaster);
    }
    return Optional.empty();
  }

  @Override
  public void add(Podcaster podcaster) {
    // Check that the user being passed doesn't
    // already exist in the database.
    if (this.fetchOne(podcaster).isPresent()) {
      return;
    }

    // First, add a new user.
    UserService user_svc = new UserService();
    user_svc.add(podcaster);

    user_svc
        .fetchOne(podcaster)
        .ifPresent(
            u -> {
              // Set the fetched ID
              podcaster.setID(u.getID());
            });

    // Second, insert a new podcaster.
    String query =
        "INSERT INTO `Podcasters` (`ID`, `firstName`, `lastName`, `biography`,"
            + " `avatar`) VALUES ('"
            + podcaster.getID()
            + "','"
            + podcaster.getFirstName()
            + "','"
            + podcaster.getLastName()
            + "','"
            + podcaster.getBiography()
            + "','"
            + podcaster.getAvatar()
            + "')";

    try {
      Statement stmt = con.createStatement();
      stmt.executeUpdate(query);
      Debugger.log(
          "INFO: User (with username='" + podcaster.getUsername() + "') successfully added.");

      // Fetch the recently created user
      // since we need their ID.
      fetchOne(podcaster)
          .ifPresent(
              u -> {
                // Set the fetched ID
                podcaster.setID(u.getID());
                // Record this action
                QuickHistory.logAccountCreation(podcaster);
              });
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public List<Podcaster> fetchAll() {
    List<Podcaster> podcasters = new ArrayList<>();
    String query =
        "SELECT u.*, p.firstName, p.lastName, p.biography, p.avatar"
            + "FROM Users as u "
            + "INNER JOIN Podcasters AS p "
            + "WHERE p.id = u.id";

    try {
      Statement stmt = con.createStatement();
      ResultSet result = stmt.executeQuery(query);

      while (result.next()) {
        podcasters.add(
            new Podcaster(
                result.getInt("ID"),
                result.getString("username"),
                result.getString("email"),
                result.getString("password"),
                Paths.get(result.getString("avatar")),
                UserStatus.fromString(result.getString("status")),
                result.getString("firstName"),
                result.getString("lastName"),
                result.getString("biography")));
      }

      return podcasters;
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return null;
  }

  public Optional<Podcaster> fetchOne(User user) {
    String query = "SELECT * FROM `Users` WHERE `ID` = ? OR `username` = ? OR `email` = ?";
    try {
      PreparedStatement stmt = con.prepareStatement(query);
      stmt.setInt(1, user.getID());
      stmt.setString(2, user.getUsername());
      stmt.setString(3, user.getEmail());
      ResultSet result = stmt.executeQuery();

      if (result.next()) {
        return Optional.of(
            new Podcaster(
                result.getInt("ID"),
                result.getString("username"),
                result.getString("email"),
                result.getString("password"),
                Paths.get(result.getString("avatar")),
                UserStatus.fromString(result.getString("status")),
                result.getString("firstName"),
                result.getString("lastName"),
                result.getString("biography")));
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return Optional.empty();
  }

  public void resetPassword(Token token, User user) {
    UserService user_svc = new UserService();
    user_svc.resetPassword(token, user);
  }

  /**
   * This function sends a mail containing the username of a particular user.
   *
   * @param podcaster The user who will be mailed their username.
   */
  public void mailUsername(Podcaster podcaster) {
    UserService svc = new UserService();
    svc.mailUsername(podcaster);
  }

  public void update(Podcaster podcaster) {
    String query =
        "UPDATE `Users` SET "
            + "`email`= '"
            + podcaster.getEmail()
            + "', "
            + "`password` = '"
            + IUser.encrypt(podcaster.getPassword())
            + "', "
            + "`avatar` = '"
            + podcaster.getAvatar()
            + "', "
            + "`status` = '"
            + podcaster.getStatus().toString()
            + "' "
            + "WHERE `username` = '"
            + podcaster.getUsername()
            + "'";

    try {
      Statement stmt = con.createStatement();
      stmt.executeUpdate(query);
      Debugger.log(
          "INFO: Podcaster (with username='"
              + podcaster.getUsername()
              + "') successfully updated.");
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * This function allows the deletion of particular users from the database.
   *
   * @param podcaster A user that is to be deleted from the database.
   * @return Returns true if deleted, and false if not.
   */
  public boolean delete(Podcaster podcaster) {
    if (!this.fetchOne(podcaster).isPresent()) {
      Debugger.log(
          "WARN: Podcaster (with username='" + podcaster.getUsername() + "') does not exist.");
      return false;
    }

    String query =
        "DELETE FROM `Podcasters` "
            + "WHERE ID=("
            + "SELECT ID FROM `Users` "
            + "WHERE username='"
            + podcaster.getUsername()
            + "')";

    try {
      Statement stmt = con.createStatement();
      stmt.executeUpdate(query);
      Debugger.log(
          "INFO: Podcaster (with username='"
              + podcaster.getUsername()
              + "') successfully deleted.");
      return true;
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return false;
  }

  /**
   * This function provides the mechanism for user authentication.
   *
   * @param podcaster A user to be compared against existing users in the database.
   * @return If a match is found, a User object, with their full details is returned.
   */
  public Optional<Podcaster> authenticate(Podcaster podcaster) {
    String query =
        "SELECT u.*, p.firstName, p.lastName, p.biography, p.avatar"
            + "FROM Users as u "
            + "INNER JOIN Podcaster AS p "
            + "WHERE p.id = u.id;";

    try {
      PreparedStatement stmt = con.prepareStatement(query);
      stmt.setString(1, podcaster.getUsername());
      ResultSet result = stmt.executeQuery();

      if (result.next()) {
        Podcaster match =
            new Podcaster(
                result.getInt("ID"),
                result.getString("username"),
                result.getString("email"),
                result.getString("password"),
                Paths.get(result.getString("avatar")),
                UserStatus.fromString(result.getString("status")),
                result.getString("firstName"),
                result.getString("lastName"),
                result.getString("biography"));

        BCrypt.Result BResult =
            BCrypt.verifyer().verify(podcaster.getPassword().toCharArray(), match.getPassword());

        // Do not authenticate if the
        // passwords do not match.
        if (!BResult.verified) return Optional.empty();

        // Do not authenticate if
        // previously banned.
        if (match.getStatus().equals(UserStatus.BANNED)) {
          Debugger.log("Unable to authenticate as this podcaster has been banned.");
          return Optional.empty();
        }

        // Re-enable account
        // if previously disabled.
        if (match.getStatus().equals(UserStatus.DISABLED)) {
          match.setStatus(UserStatus.ACTIVE);
          update(match);
        }

        return Optional.of(match);
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return Optional.empty();
  }
}
