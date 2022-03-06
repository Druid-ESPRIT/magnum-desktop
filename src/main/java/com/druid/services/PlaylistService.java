/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.druid.services;

import com.druid.interfaces.IplaylistService;
import com.druid.models.Playlist;
import com.druid.utils.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** @author tahaj */
public class PlaylistService implements IplaylistService {
  // var
  Connection cnx = DBConnection.getInstance().getConnection();

  @Override
  public void ajouterPlaylist(Playlist pl) {
    // request

    String req =
        "INSERT INTO `playlist`(`userid`, `description`) VALUES ('"
            + pl.getUserid()
            + "','"
            + pl.getDescription()
            + "')";
    try {
      // insert

      Statement st = cnx.createStatement();
      st.executeUpdate(req);
      System.out.println("Playlist Ajoutee avec succee");
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public List<Playlist> afficherPlaylist() {
    List<Playlist> playlists = new ArrayList<>();
    String req = "SELECT * FROM playlist";
    try {
      // insert

      Statement st = cnx.createStatement();
      ResultSet rs = st.executeQuery(req);

      while (rs.next()) {
        playlists.add(
            new Playlist(
                rs.getInt("idplaylist"),
                rs.getInt("userid"),
                rs.getString("description"),
                rs.getDate("dateplaylist")));
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return playlists;
  }

  public void supprimerPlaylist(int id) {
    String req = "DELETE FROM playlist WHERE idplaylist='" + id + "'";
    try {
      Statement st = cnx.createStatement();
      st.executeUpdate(req);
      System.out.println("Playlist Suprrimé");

    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public Playlist getPlaylist(int id) {
    String request = "select * from playlist where idplaylist=" + id;
    Statement st;
    try {

      st = cnx.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      ResultSet rs = st.executeQuery(request);
      rs.last();
      int nb = rs.getRow();
      rs.beforeFirst();
      if (nb != 0) {
        rs.next();
        Playlist p = new Playlist();
        p.setDescription(rs.getString(3));
        p.setDateplaylist(rs.getDate(4));
        p.setIdplaylist(rs.getInt(1));
        p.setUserid(rs.getInt(2));

        return p;
      }

    } catch (SQLException ex) {
      ex.printStackTrace();

      return null;
    }

    return null;
  }

  public void modifierPlaylist(Playlist p) {
    String req =
        "UPDATE `playlist` SET `userid`='"
            + p.getUserid()
            + "',`description`='"
            + p.getDescription()
            + "' WHERE idplaylist='"
            + p.getIdplaylist()
            + "'";
    try {
      Statement st = cnx.createStatement();
      st.executeUpdate(req);
      System.out.println("Podcast modifee");

    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }
}
