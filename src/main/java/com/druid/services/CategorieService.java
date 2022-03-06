/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.druid.services;

import com.druid.interfaces.IcategorieService;
import com.druid.models.Categorie;
import com.druid.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** @author tahaj */
public class CategorieService implements IcategorieService {
  // var
  Connection cnx = DBConnection.getInstance().getConnection();

  @Override
  public void ajouterCategorie(Categorie c) {
    // request
    String req =
        "INSERT INTO `categorie`(`namecateg`, `descriptioncateg`) VALUES ('"
            + c.getNamecateg()
            + "','"
            + c.getDescriptioncateg()
            + "')";

    try {
      // insert

      Statement st = cnx.createStatement();
      st.executeUpdate(req);
      System.out.println("Categorie Ajoutee avec succee");
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public List<Categorie> afficherCategorie() {
    List<Categorie> categories = new ArrayList<>();
    String req = "SELECT * FROM categorie";
    try {
      // insert

      Statement st = cnx.createStatement();
      ResultSet rs = st.executeQuery(req);

      while (rs.next()) {
        categories.add(
            new Categorie(
                rs.getInt("idcategorie"),
                rs.getString("namecateg"),
                rs.getString("descriptioncateg")));
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return categories;
  }

  public void supprimerCategorie(int id) {
    String req = "DELETE FROM categorie WHERE idcategorie=" + id + "";
    try {
      Statement st = cnx.createStatement();
      st.executeUpdate(req);
      System.out.println("Categorie Suprrimé");

    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public Categorie getCategorie(int id) {
    String request = "select * from categorie where idcategorie=" + id;
    Statement st;
    try {

      st = cnx.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      ResultSet rs = st.executeQuery(request);
      rs.last();
      int nb = rs.getRow();
      rs.beforeFirst();

      if (nb != 0) {
        rs.next();
        Categorie c = new Categorie();
        c.setIdcateg(rs.getInt(1));
        c.setNamecateg(rs.getString(2));
        c.setDescriptioncateg(rs.getString(3));

        return c;
      }

    } catch (SQLException ex) {
      ex.printStackTrace();

      return null;
    }

    return null;
  }

  public boolean updateCategorie(Categorie c) {
    String request =
        "UPDATE `categorie` set `namecateg`='"
            + c.getNamecateg()
            + "',`descriptioncateg`='"
            + c.getDescriptioncateg()
            + "' where idcategorie='"
            + c.getIdcateg()
            + "'";
    try {
      PreparedStatement pst = cnx.prepareStatement(request);
      pst.executeUpdate();

      System.out.println("categorie update");

      return true;

    } catch (SQLException ex) {
      ex.printStackTrace();
      return false;
    }
  }

  public Categorie getCategorieByName(String id) {
    List<Categorie> list = this.afficherCategorie();
    return list.stream().filter(c -> c.getNamecateg().equals(id)).findFirst().orElse(null);
  }
}
