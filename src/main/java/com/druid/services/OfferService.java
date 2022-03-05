package com.druid.services;

import com.druid.interfaces.IOffer;
import com.druid.models.Offer;
import com.druid.utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OfferService implements IOffer {

  Connection con = DBConnection.getInstance().getConnection();
  List<Offer> Offers = new ArrayList<>();

  public void addOffer(Offer s) {

    if (this.findOffer(s.getId()).isPresent()) {
      return;
    }
    String query =
        "INSERT INTO `Offer`(`podcaster_id`, `price`, `description`, `image`) VALUES ('"
            + s.getPodcasterID()
            + "','"
            + s.getPrice()
            + "','"
            + s.getDescription()
            + "','"
            + s.getImage()
            + "')";
    try {
      Statement stmt = con.createStatement();
      stmt.executeUpdate(query);
      System.out.println("INFO: New offer added.");
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public List<Offer> getOffers() {

    String query = "SELECT * FROM Offer";

    try {
      Statement stmt = con.createStatement();
      ResultSet result = stmt.executeQuery(query);
      while (result.next()) {
        Offers.add(
            new Offer(
                result.getInt("id"),
                result.getInt("podcaster_id"),
                result.getFloat("price"),
                result.getString("description"),
                result.getString("image")));
      }
      return Offers;

    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return null;
  }

  public Optional<Offer> findOffer(int ID) {
    String query = "SELECT * FROM `offer` WHERE `ID` = ?";
    try {
      PreparedStatement stmt = con.prepareStatement(query);
      stmt.setInt(1, ID);
      ResultSet result = stmt.executeQuery();

      if (result.next()) {
        return Optional.of(
            new Offer(
                result.getInt("id"),
                result.getInt("podcaster_id"),
                result.getFloat("price"),
                result.getString("description"),
                result.getString("image")));
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return Optional.empty();
  }

  @Override
  public void updateOffer(Offer s, int id) {
    String query =
        "UPDATE `offer`set `podcaster_id` = '"
            + s.getPodcasterID()
            + "', `price`='"
            + s.getPrice()
            + "', `description`='"
            + s.getDescription()
            + "', `image`= '"
            + s.getImage()
            + "' where id ='"
            + id
            + "'";
    try {
      Statement stmt = con.createStatement();
      stmt.executeUpdate(query);
      System.out.println("INFO: offer Updated.");
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void deleteOffer(int id) {
    String query = "DELETE from `offer` where id ='" + id + "'";
    try {
      Statement stmt = con.createStatement();
      stmt.executeUpdate(query);
      System.out.println("INFO: offer Deleted.");
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public List<Offer> searchOffer(String search) {
    List<Offer> result =
        getOffers().stream()
            .filter(su -> su.getDescription().contains(search))
            .collect(Collectors.toList());

    System.out.println(result);
    return result;
  }
}
