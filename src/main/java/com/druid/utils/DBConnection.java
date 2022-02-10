package com.druid.utils;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {
    final static String URL = "jdbc:mariadb://localhost:3306/magnum";
    //                              ^^^^^^^
    //               Change this to "mysql" if using MYSQL.
    final static String USERNAME = "grtcdr";
    final static String PWD = "Rm,<(3(jfLSf[XHgX^XpN,Wr";

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

    public Connection getConnection() {
        return con;
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }
}
