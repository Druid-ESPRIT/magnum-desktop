package com.druid.services;

import com.druid.interfaces.ISubscription;
import com.druid.utils.DBConnection;
import com.druid.models.Subscription;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class SubscriptionService implements ISubscription {

    Connection con = DBConnection.getInstance().getCon();

    public void addSubscription(Subscription s) {
        String query = "INSERT INTO `subscription`(`podcasterid`, `price`, `description`, `image`) VALUES ('"+s.getPodcasterID()+"','"+s.getPrice()+"','"+s.getDescription()+"','"+s.getImage()+"')";
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("INFO: New Subscription added.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public List<Subscription> getSubscriptions(){
        List<Subscription> subscriptions = new ArrayList<>();
        String query = "SELECT * FROM Subscription";

        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(query);

            while(result.next()) {
                subscriptions.add(new Subscription(
                        result.getInt("id"),
                        result.getInt("podcasterid"),
                        result.getFloat("price"),
                        result.getString("description"),
                        Paths.get(result.getString("image"))
                ));
            }
            System.out.println("test");
            // Debugger.log("INFO: Successfully fetched all users.");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateSubscription(Subscription s) {
        String query = "UPDATE `subscription`set `podcasterid` = '"+s.getPodcasterID()+"', `price`='"+s.getPrice()+"', `description`='"+s.getDescription()+"', `image`= '"+s.getImage()+"' where id ='"+s.getId()+"'";
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("INFO: Subscription Updated.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteSubscription(Subscription s) {
        String query = "DELETE from `subscription` where id ='"+s.getId()+"'";
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("INFO: Subscription Deleted.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
