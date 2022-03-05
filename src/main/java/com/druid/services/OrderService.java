package com.druid.services;

import com.druid.enums.OrderStatus;
import com.druid.models.Order;
import com.druid.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderService {
    Connection con = DBConnection.getInstance().getConnection();


    public int addOrder(Order or) {
        int risultato=0;
        String query = "INSERT INTO `order`(`offer_id`,`user_id`,`plan`, `total`, `orderdate`,`status`)VALUES ('"+or.getofferId()+"','"+or.getUser_id()+"','"+or.getPlan()+"','"+or.getTotal()+"','"+or.getOrderDate()+"','"+or.getStatus().toString()+"')";
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()){
                risultato=rs.getInt(1);
            }
            rs.close();

            System.out.println("INFO: New order added.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return risultato;
    }



    public List<Order> getOrders(){
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM `order`";

        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while(result.next()) {
                orders.add(new Order(
                        result.getInt("id"),
                        result.getInt("offer_id"),
                        result.getInt("user_id"),
                        result.getInt("plan"),
                        result.getFloat("total"),
                        result.getTimestamp("orderdate"),
                        OrderStatus.fromString(result.getString("status"))
                ));
            }
            return orders;
            // Debugger.log("INFO: Successfully fetched all users.");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Optional<Order> findOrder(int ID) {
        String query = "SELECT * FROM `order` WHERE `ID` = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, ID);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                return Optional.of(
                        new Order(
                                result.getInt("id"),
                                result.getInt("offer_id"),
                                result.getInt("user_id"),
                                result.getInt("plan"),
                                result.getFloat("total"),
                                result.getTimestamp("orderdate"),
                                OrderStatus.fromString(result.getString("status"))
                        ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return Optional.empty();
    }


    public void updateOrder(Order or,int id) {
        String query = "UPDATE `order`set `offer_id`='"+or.getofferId()+"', `user_id`='"+or.getUser_id()+"',`plan`= '"+or.getPlan()+"',`total`= '"+or.getTotal()+"',`orderdate`='"+or.getOrderDate()+"',`status`='"+or.getStatus().toString()+"' where id ='"+id+"'";
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("INFO: order Updated.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public void deleteOrder(int id) {
        String query = "DELETE from `order` where id ='"+id+"'";
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("INFO: order Deleted.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public Timestamp getOrderTimeById(int id){
        String query = "SELECT `orderdate` from `order` where id ='"+id+"'";
        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while(result.next()) {
                return (
                        result.getTimestamp("orderdate"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public void renewOrder(Timestamp t,int id){
        String query = "UPDATE `order`set `orderdate`='"+t+"' where id ='"+id+"'";
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("INFO: order Updated.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public int getOfferPrice(int id){
        String query = "SELECT offer.price from `order` inner join `offer` on order.offer_id = offer.id where order.id ='"+id+"'";
        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while(result.next()) {
                return (
                        result.getInt("price"));

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id;
    }
    public int getOfferId(int id){
        String query = "SELECT offer_id from `order` where id ='"+id+"'";
        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while(result.next()) {
                return (
                        result.getInt("offer_id"));

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id;
    }

    public void updateOrderStatus(OrderStatus st,int id) {
        String query = "UPDATE `order`set `status`='"+st.toString()+"' where id ='"+id+"'";
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("INFO: order Updated.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
