package com.druid.utils;

import org.jooq.SQLDialect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
  private static final String URL = "jdbc:mysql://ubuntu.localdomain:3306/magnum";
  private static final String USERNAME = "grtcdr";
  private static final String PWD = "ohma8chuth6zeiCh";
  static DBConnection instance = null;
  private Connection con;

  private DBConnection() {
    // Set a timeout to fail rather
    // than lock the application.
    DriverManager.setLoginTimeout(8);
    try (Connection con = DriverManager.getConnection(URL, USERNAME, PWD)) {
      Debugger.log("INFO: Database connection established.");
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
  }

  public static SQLDialect getDialect() {
    return SQLDialect.MYSQL;
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
