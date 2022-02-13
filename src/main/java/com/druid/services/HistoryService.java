package com.druid.services;

import com.druid.enums.HistoryActivities;
import com.druid.interfaces.IHistory;
import com.druid.models.History;
import com.druid.models.User;
import com.druid.utils.DBConnection;
import com.druid.utils.Debugger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryService implements IHistory {
  Connection con = DBConnection.getInstance().getConnection();

  public void addToHistory(History hist, User user) {
    String query =
        "INSERT INTO `History` (`userID`, `activity`, `description`, `time`) "
            + "VALUES ('"
            + user.getID()
            + "','"
            + hist.getActivity()
            + "','"
            + hist.getDescription()
            + "','"
            + hist.getTime()
            + "')";

    try {
      Statement stmt = con.createStatement();
      stmt.executeUpdate(query);
      Debugger.log(
          "INFO: History point for user (with username='"
              + user.getUsername()
              + "') successfully added.");
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public List<History> getHistory(User u) {
    List<History> historyList = new ArrayList<>();
    String query = "SELECT * FROM `History` where `userID` = ?";

    try {
      PreparedStatement stmt = con.prepareStatement(query);
      stmt.setInt(1, u.getID());
      ResultSet result = stmt.executeQuery();

      while (result.next()) {
        historyList.add(
            new History(
                result.getInt("ID"),
                result.getInt("userID"),
                HistoryActivities.fromString(result.getString("activity")),
                result.getString("description"),
                result.getTimestamp("time")));
      }

      return historyList;
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return null;
  }

  @Override
  public void deleteHistory(User user) {
    String query = "DELETE FROM `History` WHERE `userID` = '" + user.getID() + "'";
    try {
      Statement stmt = con.createStatement();
      stmt.executeUpdate(query);
      Debugger.log(
          "INFO: History for user (with username='"
              + user.getUsername()
              + "') successfully deleted.");
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }
}
