package com.druid.services;

import com.druid.models.Order;
import com.druid.utils.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    Connection con = DBConnection.getInstance().getCon();


    public void addOrder(Order or) {
        String query = "INSERT INTO `order`(`userid`, `subscriptionid`, `duration`, `total`, `orderdate`, `startdate`, `enddate`)VALUES ('"+or.getUserId()+"','"+or.getSubscriptionId()+"','"+or.getDuration()+"','"+or.getTotal()+"','"+or.getOrderDate()+"','"+or.getStartDate()+"','"+or.getEndDate()+"')";
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("INFO: New order added.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }



    public List<Order> getOrders(){
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM order";

        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(query);

            while(result.next()) {
                orders.add(new Order(
                        result.getInt("id"),
                        result.getInt("userid"),
                        result.getInt("subscriptionid"),
                        result.getInt("duration"),
                        result.getFloat("total"),
                        result.getDate("orderdate"),
                        result.getDate("startdate"),
                        result.getDate("enddate")
                ));
            }
            System.out.println("test");
            // Debugger.log("INFO: Successfully fetched all users.");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public void updateOrder(Order or) {
        String query = "UPDATE `order`set `userid` = '"+or.getUserId()+"', `subscriptionid`='"+or.getSubscriptionId()+"', `duration`='"+or.getDuration()+"', `total`= '"+or.getTotal()+"',`orderdate`='"+or.getOrderDate()+"',`startdate`= '"+or.getStartDate()+"', `enddate`= '"+or.getEndDate()+"' where id ='"+or.getId()+"'";
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("INFO: order Updated.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public void deleteOrder(Order or) {
        String query = "DELETE from `order` where id ='"+or.getId()+"'";
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("INFO: order Deleted.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
