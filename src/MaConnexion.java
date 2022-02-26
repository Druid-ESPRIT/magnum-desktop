
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class MaConnexion {
    final static String URL="jdbc:mysql://localhost/gticket";
    final static String USERNAME = "root";
    final static String PWD ="";
    private Connection cnx;
    static MaConnexion instance = null ;

    private MaConnexion() {
        try {
            cnx = DriverManager.getConnection(URL, USERNAME, PWD);
            System.out.println("Cnx cv");
                    } catch (SQLException ex) {
                    ex.printStackTrace();
                    }
    }

    public Connection getCnx() {
        return cnx;
    }

    public static MaConnexion getInstance() {
        if(instance ==null)
        {
            instance =new MaConnexion();
        }
        return instance;
    }
      
    
}
