/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author zeineb
 */
public class MaConnexion {
    //BD CREDENTIAL
    final static String URL ="jdbc:mysql://127.0.0.1:3306/magnum";
    final static String USERNAME ="root";
    final static String PWD ="";
    
    //ATT
    private Connection cnx;
    //singleton #1
    static MaConnexion instance = null;
    
    
    //CONSTRU

    private MaConnexion() {
        try {
            cnx = DriverManager.getConnection(URL,USERNAME, PWD);
            System.out.println("connexion établie avec succés");
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
 }

    public Connection getCnx() {
        return cnx;
    }

    
//singleton #3

    public static MaConnexion getInstance() {
        
       if(instance == null)
           instance=new MaConnexion();
        return instance;
    }
    
    
    
    
}
