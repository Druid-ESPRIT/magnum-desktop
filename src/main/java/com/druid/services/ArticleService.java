/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.druid.services;

import com.druid.interfaces.IarticleService;
import com.druid.models.Article;
import com.druid.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
// import java.util.ArrayList;
import java.util.List;
// import java.util.logging.Level;
// import java.util.logging.Logger;

/**
 * @author zeineb
 */
public class ArticleService implements IarticleService {
  Connection con = DBConnection.getInstance().getConnection();
  UserService podcasterService;

  public ArticleService() {
    con = DBConnection.getInstance().getConnection();
    podcasterService = new UserService();
  }

  /**
   * @param A
   * @return
   */
  @Override
  public boolean addArticle(Article A) {

    String request =
        "INSERT INTO `article`(`authorID`, `title`, `content`,`url`) VALUES ('"
            + A.getAuthorID().getID()
            + "','"
            + A.getTitle()
            + "','"
            + A.getContent()
            + "','"
            + A.getUrl()
            + "')"
            + "";
    try {

      Statement st = con.createStatement();
      st.executeUpdate(request);

      System.out.println("Article Added");

      return true;

    } catch (SQLException ex) {
      ex.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean updateArticle(Article A) {
    String request =
        "UPDATE `article` set `authorID`='"
            + A.getAuthorID().getID()
            + "',`title`='"
            + A.getTitle()
            + "',`content`='"
            + A.getContent()
            + "',`url`='"
            + A.getUrl()
            + "' where id ='"
            + A.getId()
            + "'";
    try {
      PreparedStatement pst = con.prepareStatement(request);
      pst.executeUpdate();

      System.out.println("Article update");

      return true;

    } catch (SQLException ex) {
      ex.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean cancelArticle(int id) {
    String request = "DELETE FROM `article` WHERE id=?";
    try {
      PreparedStatement pst = con.prepareStatement(request);
      pst.setInt(1, id);
      pst.executeUpdate();
      System.out.println("Article Canceled");
      return true;
    } catch (SQLException ex) {
      ex.printStackTrace();

      return false;
    }
  }

  public Article getArticle(int id) {
    String request = "select * from article where id=" + id;
    Statement st;
    try {

      st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      ResultSet rs = st.executeQuery(request);
      rs.last();
      int nb = rs.getRow();
      rs.beforeFirst();
      if (nb != 0) {
        rs.next();
        Article A = new Article();
        A.setId(rs.getInt(1));
        A.setAuthorID(podcasterService.getUser(rs.getInt(5)));
        A.setTitle(rs.getString(2));
        A.setContent(rs.getNString(4));
        A.setUrl(rs.getNString(3));

        return A;
      }

    } catch (SQLException ex) {
      ex.printStackTrace();

      return null;
    }

    return null;
  }

  @Override
  public List<Article> afficherArticle() {

    List<Article> articles = new ArrayList<>();
    String request = "SELECT * FROM `article`";

    try {
      Statement st = con.createStatement();
      ResultSet rs = st.executeQuery(request);

      while (rs.next()) {
        Article A = new Article();
        A.setId(rs.getInt(1));
        A.setAuthorID(podcasterService.getUser(rs.getInt(5)));
        A.setTitle(rs.getNString(2));
        A.setContent(rs.getNString(4));
        A.setUrl(rs.getNString(3));
        articles.add(A);
      }

    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return articles;
  }

  public List<Article> getArticleByAuthorName(String name) {
    String request =
        "select * from article a ,podcaster p where (a.authorID=p.ID AND p.Name='" + name + "');";
    Statement st;
    List<Article> articles = new ArrayList();
    try {

      st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      ResultSet rs = st.executeQuery(request);

      while (rs.next()) {

        Article A = new Article();
        A.setId(rs.getInt(1));
        A.setAuthorID(podcasterService.getUser(rs.getInt(2)));
        A.setTitle(rs.getNString(3));
        A.setContent(rs.getNString(4));
        articles.add(A);
      }

    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return articles;
  }

  public String mostActiveAuthor() {
    String request =
        "select authorID,count(*) as nb from article group by(authorID) ORDER by (nb) ;";
    Statement st;
    int id = 0;

    try {

      st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      ResultSet rs = st.executeQuery(request);
      rs.last();
      id = rs.getInt(1);

    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return podcasterService.getUser(id).getUsername();
  }
}
