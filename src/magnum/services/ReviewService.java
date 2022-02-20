/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magnum.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import magnum.interfaces.IReviewService;
import magnum.models.Review;
import magnum.utils.DbConnection;

/**
 *
 * @author Litai
 */
public class ReviewService implements IReviewService {
    
     DbConnection db;
    Connection cnx;
    EventService eventService;
    UserService userService;

    public ReviewService() {
        cnx = DbConnection.getInstance().getDb();
        eventService = new EventService();
        userService = new UserService();
    }

    @Override
    public boolean addReview(Review e) {
        String request = "INSERT INTO review (userid, eventid, review)"
                + " VALUES (?,?,?)";

        try {
            PreparedStatement pst = cnx.prepareStatement(request);

            pst.setInt(1, e.getUser().getId());
            pst.setInt(2, e.getEvent().getId());
            pst.setString(3, e.getReview());
            pst.executeUpdate();
            System.out.println("Review Added");

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ReviewService.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }
    }

    @Override
    public boolean updateReview(Review e) {
        String request = "UPDATE review SET review=? WHERE id=?";
        try {
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setString(1, e.getReview());
            pst.setInt(2, e.getId());
            pst.executeUpdate();
            System.out.println("Review Updated");

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ReviewService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean removeReview(Review e) {
        String request = "DELETE FROM review where id=?";
        try {
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setInt(1, e.getId());
            pst.executeUpdate();
            System.out.println("Review Removed");

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ReviewService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public List<Review> getAll() {

        List<Review> myList = new ArrayList<>();
        String request = "SELECT * FROM review";
        Statement st;

        try {
            st = cnx.createStatement();
            ResultSet rs = st.executeQuery(request);
            while (rs.next()) {
                Review e = new Review();
                e.setId(rs.getInt("id"));
                e.setEvent(eventService.getEvent(rs.getInt("eventid")));
                e.setUser(userService.getUser(rs.getInt("userid")));
                e.setReview(rs.getString("review"));
                myList.add(e);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReviewService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return myList;

    }

    @Override
    public Review getReview(int id) {
        String request = "select * from review where id=" + id;
        Statement st;
        try {

            st = cnx.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(request);
            rs.last();
            int nb = rs.getRow();
            rs.beforeFirst();
            
             if (nb != 0) {
                 rs.next();
                  Review e = new Review();
                e.setId(rs.getInt("id"));
                e.setEvent(eventService.getEvent(rs.getInt("eventid")));
                e.setUser(userService.getUser(rs.getInt("userid")));
                e.setReview(rs.getString("review"));
                
                return e;
             }
             } catch (SQLException ex) {
            Logger.getLogger(ReviewService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
}
