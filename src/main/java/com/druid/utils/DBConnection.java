package com.druid.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
  static final String URL = "jdbc:mysql://ubuntu.localdomain:3306/magnum";
  static final String USERNAME = "grtcdr";
  static final String PWD = "%*ohG$)5UM5A}?!D^}Da?m?";
  static DBConnection instance = null;
  private Connection con;

  private DBConnection() {
    try {
      con = DriverManager.getConnection(URL, USERNAME, PWD);
      Debugger.log("INFO: Database connection established.");
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
  }

  public static DBConnection getInstance() {
    if (instance == null) {
      instance = new DBConnection();
    }
    return instance;
  }

  public Connection getConnection() {
    return con;
  }
}
