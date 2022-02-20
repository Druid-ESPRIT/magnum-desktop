package com.druid.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.druid.enums.UserStatus;
import com.druid.interfaces.IUser;
import com.druid.models.Token;
import com.druid.models.User;
import com.druid.utils.DBConnection;
import com.druid.utils.Debugger;
import com.druid.utils.Mail;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class UserService implements IUser {
  Connection con = DBConnection.getInstance().getConnection();

  public void add(User user) {
    // Check that the user being passed doesn't
    // already exist in the database.
    if (this.fetchOne(user).isPresent()) {
      return;
    }

    String query =
        "INSERT INTO `Users` (`firstName`, `lastName`, `username`, `email`, `password`,"
            + " `biography`, `avatar`, `status`) VALUES ('"
            + user.getFirstName()
            + "','"
            + user.getLastName()
            + "','"
            + user.getUsername()
            + "','"
            + user.getEmail()
            + "' ,'"
            + encrypt(user.getPassword())
            + "','"
            + user.getBiography()
            + "','"
            + user.getAvatar()
            + "','"
            + user.getStatus().toString()
            + "')";

    try {
      Statement stmt = con.createStatement();
      stmt.executeUpdate(query);
      Debugger.log("INFO: User (with username='" + user.getUsername() + "') successfully added.");
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

  public Optional<User> fetchOne(User user) {
    String query = "SELECT * FROM `Users` WHERE `ID` = ? OR `username` = ? OR `email` = ?";
    try {
      PreparedStatement stmt = con.prepareStatement(query);
      stmt.setInt(1, user.getID());
      stmt.setString(2, user.getUsername());
      stmt.setString(3, user.getEmail());
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

  /**
   * This function sends a mail containing the username of a particular user.
   *
   * @param user The user who will be mailed their username.
   */
  public void mailUsername(User user) {
    if (!this.fetchOne(user).isPresent()) {
      return;
    }

    String subject = "Your username on Magnum";
    String text =
        "Hi!\n"
            + "You recently forgot your username, but we haven't; here it is: "
            + user.getUsername();
    Mail.send(user.getEmail(), subject, text, false);
  }

  public void update(User user) {
    String query =
        "UPDATE `Users` SET "
            + "`firstName` = '"
            + user.getFirstName()
            + "', "
            + "`lastName` = '"
            + user.getLastName()
            + "', "
            + "`email`= '"
            + user.getEmail()
            + "', "
            + "`password` = '"
            + encrypt(user.getPassword())
            + "', "
            + "`biography` = '"
            + user.getBiography()
            + "', "
            + "`avatar` = '"
            + user.getAvatar()
            + "', "
            + "`status` = '"
            + user.getStatus().toString()
            + "' "
            + "WHERE `username` = '"
            + user.getUsername()
            + "'";

    try {
      Statement stmt = con.createStatement();
      stmt.executeUpdate(query);
      Debugger.log("INFO: User (with username='" + user.getUsername() + "') successfully updated.");
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * This function allows the deletion of particular users from the database.
   *
   * @param user A user that is to be deleted from the database.
   * @return Returns true if deleted, and false if not.
   */
  public boolean delete(User user) {
    if (!this.fetchOne(user).isPresent()) {
      Debugger.log("WARN: User (with username='" + user.getUsername() + "') does not exist.");
      return false;
    }

    String query = "DELETE FROM `Users` WHERE `username` = '" + user.getUsername() + "'";
    try {
      Statement stmt = con.createStatement();
      stmt.executeUpdate(query);
      Debugger.log("INFO: User (with username='" + user.getUsername() + "') successfully deleted.");
      return true;
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return false;
  }

  /**
   * This function encrypts a given password with BCrypt.
   *
   * @param password The password to encrypt.
   * @return The encrypted password.
   */
  public String encrypt(String password) {
    return BCrypt.with(BCrypt.Version.VERSION_2Y).hashToString(12, password.toCharArray());
  }

  /**
   * This function provides the mechanism for user authentication.
   *
   * @param user A user to be compared against existing users in the database.
   * @return If a match is found, a User object, with their full details is returned.
   */
  public Optional<User> authenticate(User user) {
    String query = "SELECT * FROM `Users` WHERE `username` = ?";
    try {
      PreparedStatement stmt = con.prepareStatement(query);
      stmt.setString(1, user.getUsername());
      ResultSet result = stmt.executeQuery();

      if (result.next()) {
        User match =
            new User(
                result.getInt("ID"),
                result.getString("firstName"),
                result.getString("lastName"),
                result.getString("username"),
                result.getString("email"),
                result.getString("password"),
                result.getString("biography"),
                Paths.get(result.getString("avatar")),
                UserStatus.fromString(result.getString("status")));

        BCrypt.Result BResult =
            BCrypt.verifyer().verify(user.getPassword().toCharArray(), match.getPassword());

        if (BResult.verified) return Optional.of(match);
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return Optional.empty();
  }
}
