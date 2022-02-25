/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

/**
 *
 * @author zeineb
 */



import interfaces.Iuser;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import models.User;
import utils.MaConnexion;

/**
 *
 * @author zeineb
 */
public class UserService implements Iuser{
    
    Connection cnx;
    MaConnexion db;

    /**
     *
     */
    public UserService () {
        cnx = MaConnexion.getInstance().getCnx();
    }
    
    
         public User getUser(int id){
         String request = "select * from user where id=" + id;
        Statement st;
        try {
            st = cnx.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(request);
            rs.last();
            int nb = rs.getRow();
            rs.beforeFirst();
            if (nb != 0) {
                rs.next();
                User u = new User();
                u.setId(rs.getInt(1));
                
                
                return u;
            }

        } catch (SQLException ex) {
           ex.printStackTrace();
            return null;
        }

        return null;
    }

    

  
}
