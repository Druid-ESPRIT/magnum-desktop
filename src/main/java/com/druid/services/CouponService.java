package com.druid.services;
import com.druid.models.Coupon;
import com.druid.utils.DBConnection;

import java.nio.file.Paths;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;

public class CouponService {
    Connection con = DBConnection.getInstance().getConnection();
    List<Coupon> coupons = new ArrayList<>();

    public void generateCoupon(Coupon coupon) {
        Date date = new Date();
        Timestamp now = new Timestamp(date.getTime());


        byte[] bytes = new byte[12];
        new Random().nextBytes(bytes);
        String token = RandomStringUtils.randomAlphanumeric(12);

        String query =
                "INSERT INTO `coupon` (`user_id`, `code`,`reduction`,`used`, `created`) VALUES ('"+ coupon.getId() +"','"+token+ "','"+coupon.getReduction()+"','"+coupon.getUsed()+"','" +now+"')";

        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("INFO: New Coupon generated.");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void cleanCoupons() {
        String query = "DELETE FROM `coupon` WHERE `used` = '"+true+"'";
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("INFO: Coupons Deleted.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public int getReduction(String code){
        int reduction=0;
        String query = "SELECT `reduction` from `coupon` where code ='"+code+"'";
        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while(result.next()) {
                return (
                        result.getInt("reduction"));
            }
            reduction = result.getInt("reduction");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
       return reduction;
    }
    public List<Coupon> getCoupons(){
        String query = "SELECT * FROM Coupon";

        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while(result.next()) {
                coupons.add(new Coupon(
                        result.getInt("id"),
                        result.getInt("user_id"),
                        result.getString("code"),
                        result.getInt("reduction"),
                        result.getString("used"),
                        result.getTimestamp("created")
                ));
            }
            return coupons;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public List<Coupon> checkValidity(String search){
        List<Coupon> result = getCoupons().stream()
                .filter(su-> su.getCode().equals(search))
                .filter(su -> su.getUsed().equals("false"))
                .collect(Collectors.toList());
        return result;

    }

    public void useCoupon(String code){
        String query = "UPDATE `coupon`set `used`='"+true+"' where code ='"+code+"'";
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("INFO: Coupon used.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
