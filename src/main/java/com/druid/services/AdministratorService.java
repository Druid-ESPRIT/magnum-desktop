package com.druid.services;

// This service is modeled after class-table
// inheritance and borrows ideas found in:
// https://docs.oracle.com/cd/E28280_01/apirefs.1111/e13946/ejb3_overview_mapping_inher.html#ejb3_overview_mapping_inher_single

import com.druid.enums.UserDiscriminator;
import com.druid.enums.UserStatus;
import com.druid.errors.register.EmailTakenException;
import com.druid.errors.register.UsernameTakenException;
import com.druid.interfaces.IUser;
import com.druid.models.Administrator;
import com.druid.utils.Debugger;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdministratorService implements IUser<Administrator> {
  public void add(Administrator administrator) throws EmailTakenException, UsernameTakenException {
    // Check that the user being passed doesn't
    // already exist in the database.
    if (this.fetchOne(administrator).isPresent()) {
      return;
    }

    // First, add a new user.
    UserService user_svc = new UserService();
    user_svc.add(administrator);

    user_svc
        .fetchOne(administrator)
        .ifPresent(
            u -> {
              // Set the fetched ID
              administrator.setID(u.getID());
            });

    // Second, insert a new Administrator.
    String query =
        "INSERT INTO `Administrators` (`ID`, `firstName`, `lastName`, `biography`,"
            + " `avatar`) VALUES ('"
            + administrator.getID()
            + "','"
            + administrator.getFirstName()
            + "','"
            + administrator.getLastName()
            + "','"
            + administrator.getAvatar()
            + "')";

    try {
      Statement stmt = IUser.con.createStatement();
      stmt.executeUpdate(query);
      Debugger.log(
          "INFO: User (with username='" + administrator.getUsername() + "') successfully added.");
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public List<Administrator> fetchAll() {
    List<Administrator> administrators = new ArrayList<>();
    String query =
        "SELECT U.*, A.firstName, A.lastName "
            + "FROM Users AS U "
            + "INNER JOIN Administrators AS A "
            + "ON A.ID = U.ID";

    try {
      Statement stmt = IUser.con.createStatement();
      ResultSet result = stmt.executeQuery(query);

      while (result.next()) {
        Administrator adm = new Administrator();
        adm.setID(result.getInt("ID"));
        adm.setEmail(result.getString("email"));
        adm.setUsername(result.getString("username"));
        adm.setPassword(result.getString("password"));
        adm.setPassword(result.getString("firstName"));
        adm.setPassword(result.getString("lastName"));
        adm.setStatus(UserStatus.fromString(result.getString("status")));
        adm.setDiscriminator(UserDiscriminator.fromString(result.getString("discr")));

        Optional<String> avatar = Optional.ofNullable(result.getString("avatar"));
        if (avatar.isPresent()) {
          adm.setAvatar(Paths.get(avatar.get()));
        }

        administrators.add(adm);
      }

      return administrators;
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return null;
  }

  public Optional<Administrator> fetchOne(Administrator administrator) {
    String query =
        "SELECT U.*, A.firstName, A.lastName "
            + "FROM Users AS U "
            + "INNER JOIN Administrators AS A "
            + "ON A.ID = U.ID "
            + "WHERE A.ID = ?";
    try {
      PreparedStatement stmt = IUser.con.prepareStatement(query);
      stmt.setInt(1, administrator.getID());
      ResultSet result = stmt.executeQuery();

      if (result.next()) {
        Administrator adm = new Administrator();
        adm.setID(result.getInt("ID"));
        adm.setEmail(result.getString("email"));
        adm.setUsername(result.getString("username"));
        adm.setPassword(result.getString("password"));
        adm.setPassword(result.getString("firstName"));
        adm.setPassword(result.getString("lastName"));
        adm.setStatus(UserStatus.fromString(result.getString("status")));
        adm.setDiscriminator(UserDiscriminator.fromString(result.getString("discr")));

        Optional<String> avatar = Optional.ofNullable(result.getString("avatar"));
        if (avatar.isPresent()) {
          adm.setAvatar(Paths.get(avatar.get()));
        }

        return Optional.of(adm);
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return Optional.empty();
  }

  /**
   * This function sends a mail containing the username of a particular user.
   *
   * @param administrator The user who will be mailed their username.
   */
  public void mailUsername(Administrator administrator) {
    UserService user_svc = new UserService();
    user_svc.mailUsername(administrator);
  }

  public void update(Administrator administrator) {
    String query =
        "UPDATE `Users` SET "
            + "`email`= '"
            + administrator.getEmail()
            + "', "
            + "`password` = '"
            + IUser.encrypt(administrator.getPassword())
            + "', "
            + "`avatar` = '"
            + administrator.getAvatar()
            + "', "
            + "`status` = '"
            + administrator.getStatus().toString()
            + "', "
            + administrator.getDiscriminator().toString()
            + "' "
            + "WHERE `username` = '"
            + administrator.getUsername()
            + "'";

    try {
      Statement stmt = IUser.con.createStatement();
      stmt.executeUpdate(query);
      Debugger.log(
          "INFO: Administrator (with username='"
              + administrator.getUsername()
              + "') successfully updated.");
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * This function allows the deletion of particular users from the database.
   *
   * @param administrator A user that is to be deleted from the database.
   * @return Returns true if deleted, and false if not.
   */
  public boolean delete(Administrator administrator) {
    if (!this.fetchOne(administrator).isPresent()) {
      Debugger.log(
          "WARN: Administrator (with username='"
              + administrator.getUsername()
              + "') does not exist.");
      return false;
    }

    String query =
        "DELETE FROM `Administrators` "
            + "WHERE ID=("
            + "SELECT ID FROM `Users` "
            + "WHERE username='"
            + administrator.getUsername()
            + "')";

    try {
      Statement stmt = IUser.con.createStatement();
      stmt.executeUpdate(query);
      Debugger.log(
          "INFO: Administrator (with username='"
              + administrator.getUsername()
              + "') successfully deleted.");
      return true;
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return false;
  }
}
