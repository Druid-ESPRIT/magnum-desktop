/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.druid.services;

import com.druid.interfaces.IpodcastService;
import com.druid.models.Podcast;
import com.druid.utils.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.media.AudioClip;

/**
 * @author tahaj
 */
public class PodcastService implements IpodcastService {

  // var
  Connection cnx = DBConnection.getInstance().getConnection();
  static AudioClip music;

  @Override
  public void ajouterPodcast(Podcast p) {
    // request
    String req =
        "INSERT INTO `podcasts`(`title`, `description`, `file` , `rating`, `views`,`idcateg`,"
            + " `image`) VALUES ('"
            + p.getTitle()
            + "','"
            + p.getDescription()
            + "','"
            + p.getFile()
            + "','"
            + p.getRating()
            + "','"
            + p.getViews()
            + "','"
            + p.getIdcateg()
            + "','"
            + p.getImage()
            + "')";

    try {
      // insert

      Statement st = cnx.createStatement();
      st.executeUpdate(req);
      System.out.println("Podcast Ajoutee avec succee");
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public List<Podcast> afficherPodcast() {
    List<Podcast> podcasts = new ArrayList<>();

    String req = "SELECT * FROM podcasts";
    try {
      // insert

      Statement st = cnx.createStatement();
      ResultSet rs = st.executeQuery(req);

      while (rs.next()) {
        podcasts.add(
            new Podcast(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("file"),
                rs.getInt("rating"),
                rs.getInt("views"),
                rs.getInt("idcateg")));
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return podcasts;
  }

  public void supprimerPodcast(int id) {
    String req = "DELETE FROM podcasts WHERE id='" + id + "'";
    try {
      Statement st = cnx.createStatement();
      st.executeUpdate(req);
      System.out.println("Podcast Suprrimé");

    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void modifierPodcast(Podcast p) {
    String req =
        "UPDATE `podcasts` SET `title`='"
            + p.getTitle()
            + "',`description`='"
            + p.getDescription()
            + "',`file`='"
            + p.getFile()
            + "',`rating`='"
            + p.getRating()
            + "',`views`='"
            + p.getViews()
            + "',`idcateg`='"
            + p.getIdcateg()
            + "' WHERE id='"
            + p.getId()
            + "'";
    try {
      Statement st = cnx.createStatement();
      st.executeUpdate(req);
      System.out.println("Podcast modifee");

    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public void PlayPodcaster(Podcast P, double volume) {
    music = new AudioClip(P.getFile());
    music.play(volume);
  }

  public void stopPodcaster(Podcast P) {
    music = new AudioClip(P.getFile());
    music.stop();
  }

  public void setVolume(double volume) {
    music.setVolume(volume);
  }

  public Podcast getPodcast(int id) {
    String request = "select * from podcasts where id=" + id;
    Statement st;
    try {

      st = cnx.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      ResultSet rs = st.executeQuery(request);
      rs.last();
      int nb = rs.getRow();
      rs.beforeFirst();
      if (nb != 0) {
        rs.next();
        Podcast p = new Podcast();
        p.setId(rs.getInt(1));
        p.setTitle(rs.getString(2));
        p.setDescription(rs.getString(3));
        p.setRating(rs.getInt(4));
        p.setViews(rs.getInt(5));
        p.setIdcateg(rs.getInt(6));
        p.setFile(rs.getString(7));
        p.setImage(rs.getString(8));

        return p;
      }

    } catch (SQLException ex) {
      ex.printStackTrace();

      return null;
    }

    return null;
  }

  public List<Podcast> getPodcastByCategorie(String categorie) {
    String req =
        "select * from podcasts p,categorie c where p.idcateg=c.idCategorie AND c.namecateg='"
            + categorie
            + "'";
    List<Podcast> podcasts = new ArrayList<>();

    try {
      // insert

      Statement st = cnx.createStatement();
      ResultSet rs = st.executeQuery(req);

      while (rs.next()) {
        podcasts.add(
            new Podcast(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("file"),
                rs.getInt("rating"),
                rs.getInt("views"),
                rs.getInt("idcateg")));
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return podcasts;
  }

  public List<Podcast> getPodcastByViews() {
    String req = "select * from podcasts order by(views) DESC";
    List<Podcast> podcasts = new ArrayList<>();

    try {
      // insert

      Statement st = cnx.createStatement();
      ResultSet rs = st.executeQuery(req);

      while (rs.next()) {
        podcasts.add(
            new Podcast(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("file"),
                rs.getInt("rating"),
                rs.getInt("views"),
                rs.getInt("idcateg")));
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return podcasts;
  }

  public List<Podcast> getPodcastByRating() {
    String req = "select * from podcasts order by(rating) DESC";
    List<Podcast> podcasts = new ArrayList<>();

    try {
      // insert

      Statement st = cnx.createStatement();
      ResultSet rs = st.executeQuery(req);

      while (rs.next()) {
        Podcast p = this.getPodcast(rs.getInt(1));
        podcasts.add(p);
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return podcasts;
  }
}
