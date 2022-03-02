package com.druid.utils;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
public class DBConnection {

    final static String URL = "jdbc:mysql://localhost:3306/magnum";
    final static String USERNAME = "root";
    final static String PWD = "";

    private Connection con;
    static DBConnection instance = null;

    private DBConnection() {
        try {
            con = DriverManager.getConnection(URL, USERNAME, PWD);

            System.out.println("INFO: Database connection established");
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
