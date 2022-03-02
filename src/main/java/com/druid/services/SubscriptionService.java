package com.druid.services;

import com.druid.interfaces.ISubscription;
import com.druid.models.Offer;
import com.druid.models.Subscription;
import com.druid.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SubscriptionService implements ISubscription {

    Connection con = DBConnection.getInstance().getCon();


    public Optional<Subscription> findSubscription(int ID) {
        String query = "SELECT * FROM `subscription` WHERE `ID` = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, ID);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                return Optional.of(
                        new Subscription(
                                result.getInt("id"),
                                result.getInt("order_id"),
                                result.getInt("user_id"),
                                result.getTimestamp("start_date"),
                                result.getTimestamp("expire_date")
 ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return Optional.empty();
    }




    @Override
    public void addSubscription(Subscription sub) {

        String query = "INSERT INTO `subscription`(`order_id`, `user_id`,`start_date`,`expire_date`) VALUES ('"+sub.getorder_id()+"','"+sub.getUser_id()+"','"+sub.getStart_date()+"','"+sub.getExpire_date()+"')";
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("INFO: New sub added.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public List<Subscription> getSubscriptions() {
        List<Subscription> Subscriptions = new ArrayList<>();
        String query = "SELECT * FROM Subscription";

        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(query);

            while(result.next()) {
                Subscriptions.add(new Subscription(
                        result.getInt("id"),
                        result.getInt("order_id"),
                        result.getInt("user_id"),
                        result.getTimestamp("start_date"),
                        result.getTimestamp("expire_date")
                ));
            }
            return Subscriptions;

            // Debugger.log("INFO: Successfully fetched all users.");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;

    }

    @Override
    public void updateSubscription(Subscription sub) {

            String query = "UPDATE `subscription`set `order_id` = '" + sub.getorder_id() + "', `user_id`='" + sub.getUser_id() + "',`start_date`='"+sub.getStart_date()+"',`expire_date`='"+sub.getExpire_date()+"' where id ='" + sub.getId() + "'";
            try {
                Statement stmt = con.createStatement();
                stmt.executeUpdate(query);
                System.out.println("INFO: sub Updated.");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    @Override
    public void deleteSubscription(int id) {
            String query = "DELETE from `subscription` where id ='"+id+"'";
            try {
                Statement stmt = con.createStatement();
                stmt.executeUpdate(query);
                System.out.println("INFO: sub Deleted.");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    public Timestamp getSubscriptionTimeById(int id){
        String query = "SELECT `expire_date` from `subscription` where id ='"+id+"'";
        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while(result.next()) {
                return (
                        result.getTimestamp("expire_date"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void renewSubscription(Timestamp t,int id){
        String query = "UPDATE `subscription`set `expire_date`='"+t+"' where id ='"+id+"'";
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("INFO: sub Updated.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public int getOrderId(int id){
        String query = "SELECT order_id from `subscription` where id ='"+id+"'";
        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while(result.next()) {
                return (
                        result.getInt("order_id"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id;
    }

}
