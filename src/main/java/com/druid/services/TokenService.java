package com.druid.services;

import com.druid.models.Token;
import com.druid.models.User;
import com.druid.utils.DBConnection;
import com.druid.utils.Debugger;
import com.druid.utils.Mail;
import org.apache.commons.lang3.RandomStringUtils;

import java.sql.*;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TokenService {
  Connection con = DBConnection.getInstance().getConnection();

  public Optional<Token> getMostRecent(User user) {
    String query = "SELECT * FROM `Tokens` WHERE `userID` = ? ORDER BY `created` DESC LIMIT 1";

    try {
      PreparedStatement stmt = con.prepareStatement(query);
      stmt.setInt(1, user.getID());
      ResultSet result = stmt.executeQuery();

      if (result.next()) {
        return Optional.of(
            new Token(
                result.getInt("ID"),
                result.getInt("userID"),
                result.getString("token"),
                result.getBoolean("consumed"),
                result.getTimestamp("created")));
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return Optional.empty();
  }

  public Optional<Token> get(Token token, User user) {
    String query = "SELECT * FROM `Tokens` WHERE `token` = ? AND `userID` = ?";

    try {
      PreparedStatement stmt = con.prepareStatement(query);
      stmt.setString(1, token.getToken());
      stmt.setInt(2, user.getID());
      ResultSet result = stmt.executeQuery();

      if (result.next()) {
        return Optional.of(
            new Token(
                result.getInt("ID"),
                result.getInt("userID"),
                result.getString("token"),
                result.getBoolean("consumed"),
                result.getTimestamp("created")));
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return Optional.empty();
  }

  public void generate(User user) {
    Date date = new Date();
    Timestamp now = new Timestamp(date.getTime());
    Optional<Token> mostRecentToken = getMostRecent(user);

    if (mostRecentToken.isPresent()) {
      Timestamp lastCreated = mostRecentToken.get().getCreated();
      long diff = TimeUnit.MILLISECONDS.toSeconds(now.getTime() - lastCreated.getTime());
      if (diff < 120) {
        Debugger.log(
            "Failed to generate a new password reset token, too many tries. (last one sent "
                + diff
                + " seconds ago)");
        return;
      }
    }

    byte[] bytes = new byte[128];
    new Random().nextBytes(bytes);
    String token = RandomStringUtils.randomAlphanumeric(128);

    String query =
        "INSERT INTO `Tokens` (`userID`, `token`, `created`) VALUES ('"
            + user.getID()
            + "','"
            + token
            + "','"
            + now
            + "')";

    try {
      Statement stmt = con.createStatement();
      stmt.executeUpdate(query);
      Debugger.log("INFO: Password reset token successfully generated.");
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    String subject = "Reset your password";
    String text =
        "Hi, "
            + user.getUsername()
            + "!\n"
            + "You recently asked to reset your password, the following token can be used to"
            + " recover your account:\n"
            + token
            + "\n"
            + "Do not share this with anyone, and hurry up, this token will expire after 24 hours!";
    Mail.send(user.getEmail(), subject, text, false);
  }

  public void update(Token t) {
    int consumed = t.isConsumed() ? 1 : 0;
    String query =
        "UPDATE `Tokens` SET "
            + "`consumed` = '"
            + consumed
            + "' "
            + "WHERE `ID` = '"
            + t.getID()
            + "'";

    try {
      Statement stmt = con.createStatement();
      stmt.executeUpdate(query);
      Debugger.log("INFO: Token successfully consumed.");
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  /** This function cleans up the token table by removing all consumed tokens. */
  public void clean() {
    String query = "DELETE FROM `Tokens` WHERE `consumed` = 1";
    try {
      Statement stmt = con.createStatement();
      stmt.executeUpdate(query);
      Debugger.log("INFO: Consumed tokens successfully removed.");
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }
}
