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

  public void flag(Flag flag) {
    String query =
        "INSERT INTO `Flags` (`flaggerID`, `flaggedID`, `offense`, `description`, `time`) "
            + "VALUES ('"
            + flag.getFlaggerID()
            + "','"
            + flag.getFlaggedID()
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
          "INFO: User (with ID='"
              + flag.getFlaggedID()
              + "') successfully flagged for \""
              + flag.getOffense()
              + "\".");
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public List<Flag> getFlags(User flagged) {
    List<Flag> flagList = new ArrayList<>();
    String query = "SELECT * FROM `Flags` WHERE `flaggedID` = ?";

    try {
      PreparedStatement stmt = con.prepareStatement(query);
      stmt.setInt(1, flagged.getID());
      ResultSet result = stmt.executeQuery();

      while (result.next()) {
        flagList.add(
            new Flag(
                result.getInt("ID"),
                result.getInt("flaggerID"),
                result.getInt("flaggedID"),
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

  public void unflag(User flagged) {
    String query = "DELETE FROM `Flags` WHERE `ID` = '" + flagged.getID() + "'";
    try {
      Statement stmt = con.createStatement();
      stmt.executeUpdate(query);
      Debugger.log("INFO: User successfully unflagged.");
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }
}
