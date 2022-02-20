/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magnum.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author Litai
 */
public class DbConnection {
     String url = "jdbc:mysql://localhost:3306/magnum";
    String login = "root";
    String mdp = "";
    public Connection cnx;
    
    public static DbConnection instance; 
    
    private DbConnection(){
        try {
            cnx = DriverManager.getConnection(url, login, mdp);
            System.out.println("Connexion Etablie!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
     public static  DbConnection getInstance(){
        if(instance == null){instance = new  DbConnection();}
        return instance ;
    }

    public Connection getDb() {
        return cnx;
    }
    
    
}
