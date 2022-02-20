package com.druid.services;

import com.druid.enums.FlagOffense;
import com.druid.models.Flag;
import com.druid.models.User;
import com.druid.utils.DBConnection;
import com.druid.utils.Debugger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlagService {
  Connection con = DBConnection.getInstance().getConnection();

  public void flag(Flag flag, User user) {
    String query =
        "INSERT INTO `Flags` (`userID`, `offense`, `description`, `time`) "
            + "VALUES ('"
            + user.getID()
            + "','"
            + flag.getOffense()
            + "','"
            + flag.getDescription()
            + "','"
            + flag.getTime()
            + "')";

    try {
      Statement stmt = con.createStatement();
      stmt.executeUpdate(query);
      Debugger.log(
          "INFO: User (with username='"
              + user.getUsername()
              + "') successfully flagged for \""
              + flag.getOffense()
              + "\".");
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public List<Flag> getFlags(User user) {
    List<Flag> flagList = new ArrayList<>();
    String query = "SELECT * FROM `Flags` WHERE `userID` = ?";

    try {
      PreparedStatement stmt = con.prepareStatement(query);
      stmt.setInt(1, user.getID());
      ResultSet result = stmt.executeQuery();

      while (result.next()) {
        flagList.add(
            new Flag(
                result.getInt("ID"),
                result.getInt("userID"),
                FlagOffense.fromString(result.getString("offense")),
                result.getString("description"),
                result.getTimestamp("time")));
      }

      return flagList;
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return null;
  }

  public void unflag(User user) {
    String query = "DELETE FROM `Flags` WHERE `ID` = '" + user.getID() + "'";
    try {
      Statement stmt = con.createStatement();
      stmt.executeUpdate(query);
      Debugger.log("INFO: User successfully unflagged.");
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }
}
