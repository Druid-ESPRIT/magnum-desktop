package com.druid.utils;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {
    final static String URL = "jdbc:mariadb://localhost:3306/workshop"; // TODO: Change this to MYSQL
    final static String USERNAME = "grtcdr";
    final static String PWD = "rdctrg";

    private Connection con;
    static DBConnection instance = null;

    private DBConnection() {
        try {
            con = DriverManager.getConnection(URL, USERNAME, PWD);
            Debugger.log("INFO: Database connection established.");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public Connection getCon() {
        return con;
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }
}
