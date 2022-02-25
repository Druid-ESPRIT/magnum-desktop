package services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Podcaster;
import interfaces.IPodcasterService;
import java.sql.Connection;

import utils.MaConnexion;



public class PodcasterService implements IPodcasterService {
    
    Connection cnx;
    MaConnexion db;
    
    
    
     public PodcasterService () {
        cnx = MaConnexion.getInstance().getCnx();
    }

         public Podcaster getPodcaster(int id){
         String request = "select * from podcaster where id=" + id;
        Statement st;
        try {
            st = cnx.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(request);
            rs.last();
            int nb = rs.getRow();
            rs.beforeFirst();
            if (nb != 0) {
                rs.next();
                Podcaster P = new Podcaster();
                P.setID(rs.getInt(1));
                P.setName(rs.getNString(2));
                
                
                return P;
            }

        } catch (SQLException ex) {
            Logger.getLogger(PodcasterService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        return null;
    }
    
}
       

   

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

