/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magnum.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import magnum.enums.Status;
import magnum.interfaces.IUserService;
import magnum.models.User;
import magnum.utils.DbConnection;

/**
 *
 * @author Litai
 */
public class UserService implements IUserService{
    
     DbConnection db;
    Connection cnx;

    public UserService() {
        cnx = DbConnection.getInstance().getDb();
    }

    @Override
    public User getUser(int id) {

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
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setBiography(rs.getString("biography"));
                u.setProfilePicturePath(rs.getString("profilepicturepath"));
                u.setPassword(rs.getString("password"));
                if(rs.getString("status")!=null)
                {
                    u.setStatus(Status.valueOf(rs.getString("status")));
                }
                u.setAdmin(rs.getBoolean("admin"));
                u.setBanned(rs.getBoolean("banned"));
                u.setScore(rs.getInt("score"));
                
                return u;
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        return null;
    }
    
}
