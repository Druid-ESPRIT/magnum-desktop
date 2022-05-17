package com.druid.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
  // MYSQL User.
  static final String USERNAME = "root";
  static final String PASSWORD = "";

  // Your Host.
  static final String HOST = "localhost";
  static final String DB_NAME = "magnum2";

  static DBConnection instance = null;
  private Connection con;

  private DBConnection() {
    try {
      Properties properties = new Properties();
      properties.put("user", USERNAME);
      properties.put("password", PASSWORD);
      con = DriverManager.getConnection("jdbc:mysql://" + HOST + ":/" + DB_NAME, properties);

      Debugger.log("INFO: Database connection established.");
    } catch (SQLException exception) {
      System.out.println("Failed to communicate with the database.");
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
