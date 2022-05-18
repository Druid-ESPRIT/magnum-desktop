package com.druid.services;

import com.druid.interfaces.IReviewService;
import com.druid.models.Review;
import com.druid.models.User;
import com.druid.utils.ConnectedUser;
import com.druid.utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReviewService implements IReviewService {

  Connection cnx = DBConnection.getInstance().getConnection();
  UserService userService = new UserService();
  EventService eventService = new EventService();
  private ConnectedUser connectedUser = ConnectedUser.getInstance();

  @Override
  public boolean addReview(Review e) {
    if (getReviewUser(connectedUser.getUser().getID()) != null) {
      return false;
    }
    String request = "INSERT INTO review (user_id, event_id, review)" + " VALUES (?,?,?)";

    try {
      PreparedStatement pst = cnx.prepareStatement(request);

      pst.setInt(1, e.getUser().getID());
      pst.setInt(2, e.getEvent().getId());
      pst.setString(3, e.getReview());
      pst.executeUpdate();
      System.out.println("Review Added");

      return true;
    } catch (SQLException ex) {
      return false;
    }
  }

  @Override
  public boolean updateReview(Review e) {
    String request = "UPDATE review SET review=? WHERE id=? and user_id=? and event_id=?";
    try {
      PreparedStatement pst = cnx.prepareStatement(request);
      pst.setString(1, e.getReview());
      pst.setInt(2, e.getId());
      pst.setInt(3, e.getUser().getID());
      pst.setInt(4, e.getEvent().getId());
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
        e.setEvent(eventService.getEvent(rs.getInt("event_id")));
        User u = new User();
        u.setID(rs.getInt("user_id"));
        u = userService.fetchOne(u).get();
        e.setUser(u);
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
        e.setEvent(eventService.getEvent(rs.getInt("event_id")));
        User u = new User();
        u.setID(rs.getInt("user_id"));
        u = userService.fetchOne(u).get();
        e.setUser(u);
        e.setReview(rs.getString("review"));

        return e;
      }
    } catch (SQLException ex) {
      Logger.getLogger(ReviewService.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }

  @Override
  public Review getReviewUser(int userid) {
    String request = "select * from review where user_id=" + userid;
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
        e.setEvent(eventService.getEvent(rs.getInt("event_id")));
        User u = new User();
        u.setID(rs.getInt("user_id"));
        u = userService.fetchOne(u).get();
        e.setUser(u);
        e.setReview(rs.getString("review"));

        return e;
      }
    } catch (SQLException ex) {
      Logger.getLogger(ReviewService.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }
}
