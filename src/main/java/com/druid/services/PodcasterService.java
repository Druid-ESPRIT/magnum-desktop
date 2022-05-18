package com.druid.services;

// This service is modeled after class-table
// inheritance and borrows ideas found in:
// https://docs.oracle.com/cd/E28280_01/apirefs.1111/e13946/ejb3_overview_mapping_inher.html#ejb3_overview_mapping_inher_single

import at.favre.lib.crypto.bcrypt.BCrypt;

import com.druid.enums.UserStatus;
import com.druid.enums.UserDiscriminator;
import com.druid.errors.register.EmailTakenException;
import com.druid.errors.register.UsernameTakenException;
import com.druid.interfaces.IUser;
import com.druid.models.Podcaster;
import com.druid.models.User;
import com.druid.utils.Debugger;

import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PodcasterService implements IUser<Podcaster> {
  public void add(Podcaster podcaster) throws EmailTakenException, UsernameTakenException {
    // Check that the user being passed doesn't
    // already exist in the database.
    if (this.fetchOne(podcaster).isPresent()) {
      return;
    }

    // First, add a new user.
    UserService user_svc = new UserService();
    user_svc.add(podcaster);

    user_svc.fetchOne(podcaster) .ifPresent( u -> { podcaster.setID(u.getID()); });

    // Second, insert a new podcaster.
    String query =
        "INSERT INTO `Podcasters` (`ID`, `firstName`, `lastName`, `biography`) VALUES ('"
            + podcaster.getID()
            + "','"
            + podcaster.getFirstName()
            + "','"
            + podcaster.getLastName()
            + "','"
            + podcaster.getBiography()
            + "')";

    try {
      Statement stmt = con.createStatement();
      stmt.executeUpdate(query);
      Debugger.log(
          "INFO: User (with username='" + podcaster.getUsername() + "') successfully added.");
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public List<Podcaster> fetchAll() {
    List<Podcaster> podcasters = new ArrayList<>();
    String query =
        "SELECT U.*, P.firstName, P.lastName, P.biography "
            + "FROM Users AS U "
            + "INNER JOIN Podcasters AS P "
            + "ON P.ID = U.ID";

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
		UserDiscriminator.fromString(result.getString("discr")),
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

  public Optional<Podcaster> fetchOne(Podcaster podcaster) {
    String query =
        "SELECT U.*, P.firstName, P.lastName, P.biography "
            + "FROM Users AS U "
            + "INNER JOIN Podcasters AS P "
            + "ON P.ID = U.ID "
            + "WHERE P.ID = ?";
    try {
      PreparedStatement stmt = con.prepareStatement(query);
      stmt.setInt(1, podcaster.getID());
      ResultSet result = stmt.executeQuery();

      if (result.next()) {
        Podcaster pod = new Podcaster();
        pod.setID(result.getInt("ID"));
        pod.setEmail(result.getString("email"));
        pod.setUsername(result.getString("username"));
        pod.setPassword(result.getString("password"));
        pod.setPassword(result.getString("firstName"));
        pod.setPassword(result.getString("lastName"));
        pod.setPassword(result.getString("biography"));
        pod.setStatus(UserStatus.fromString(result.getString("status")));
        pod.setDiscriminator(UserDiscriminator.fromString(result.getString("discr")));

        Optional<String> avatar = Optional.ofNullable(result.getString("avatar"));
        if (avatar.isPresent()) {
          pod.setAvatar(Paths.get(avatar.get()));
        }

        return Optional.of(pod);
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return Optional.empty();
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
	    + "', "
	    + "`discr` = '"
            + podcaster.getDiscriminator().toString()
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
}
