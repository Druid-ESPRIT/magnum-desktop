package com.druid.services;

import com.druid.interfaces.Icommentaire;
import com.druid.models.Article;
import com.druid.models.Commentaire;
import com.druid.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** @author zeineb */
public class CommentaireService implements Icommentaire {

  Connection cnx = DBConnection.getInstance().getConnection();
  UserService userService;
  ArticleService articleservice;

  public CommentaireService() {
    cnx = DBConnection.getInstance().getConnection();
    userService = new UserService();
    articleservice = new ArticleService();
  }

  /**
   * @param C
   * @return
   */
  @Override
  public boolean addCommentaire(Commentaire C) {

    String request =
        "INSERT INTO `commentaire`(`userID`,`articleID`,`message`) VALUES ('"
            + C.getUserID().getID()
            + "','"
            + C.getArticleID().getId()
            + "','"
            + C.getMessage()
            + "')";
    try {

      Statement st = cnx.createStatement();
      st.executeUpdate(request);

      System.out.println("commentaire Added");

      return true;

    } catch (SQLException ex) {
      ex.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean updateCommentaire(Commentaire C) {
    String request =
        "UPDATE `commentaire` set `userID`='"
            + C.getUserID().getID()
            + "',`articleID`='"
            + C.getArticleID().getId()
            + "',`message`='"
            + C.getMessage()
            + "' where id ='"
            + C.getId()
            + "'";
    try {
      /*PreparedStatement pst = cnx.prepareStatement(request);
      pst.executeUpdate();*/
      Statement st = cnx.createStatement();
      st.executeUpdate(request);
      System.out.println("commentaire updated");

      return true;

    } catch (SQLException ex) {
      ex.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean cancelCommentaire(Commentaire c) {
    String request = "DELETE FROM `commentaire` WHERE id=?";
    try {
      PreparedStatement pst = cnx.prepareStatement(request);
      pst.setInt(1, c.getId());
      pst.executeUpdate();
      System.out.println("commentaire Canceled");
      return true;
    } catch (SQLException ex) {
      ex.printStackTrace();

      return false;
    }
  }

  @Override
  public Commentaire getCommentaire(int id) {
    String request = "select * from commentaire where id=" + id;
    Statement st;
    try {

      st = cnx.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      ResultSet rs = st.executeQuery(request);
      rs.last();
      int nb = rs.getRow();
      rs.beforeFirst();
      if (nb != 0) {
        rs.next();
        Commentaire C = new Commentaire();
        C.setId(rs.getInt(1));
        //C.setUserID(userService.getUser(1));
        C.setArticleID(articleservice.getArticle(rs.getInt(3)));
        C.setMessage(rs.getString(4));
        C.setSubmitDate(rs.getDate(5));

        return C;
      }

    } catch (SQLException ex) {
      ex.printStackTrace();

      return null;
    }

    return null;
  }

  @Override
  public List<Commentaire> afficherCommentaire() {

    List<Commentaire> commentaires = new ArrayList<>();
    String request = "SELECT * FROM `commentaire`";

    try {
      Statement st = cnx.createStatement();
      ResultSet rs = st.executeQuery(request);

      while (rs.next()) {
        Commentaire C = new Commentaire();
        C.setId(rs.getInt(1));
        //C.setUserID(userService.getClass(rs.getInt(3));
        C.setUserID(userService.fetchAll().get(rs.getInt(2)));
        C.setArticleID(articleservice.getArticle(rs.getInt(2)));
        C.setMessage(rs.getString(4));
        C.setSubmitDate(rs.getDate(5));

        commentaires.add(C);
      }

    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return commentaires;
  }

  public int getMaxArticle() {
    String request =
        "select articleID,count(*) as nb from commentaire group by(articleID) ORDER by (nb) ;";
    Statement st;
    int id = 0;

    try {

      st = cnx.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      ResultSet rs = st.executeQuery(request);
      rs.last();
      id = rs.getInt(1);

    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return id;
  }

  public Article getTopArticle() {
    return articleservice.getArticle(this.getMaxArticle());
  }

  public List<Article> articleSorted() {
    String request =
        "select articleID,count(*) as nb from commentaire group by(articleID) ORDER by (nb) DESC ;";
    Statement st;
    List<Article> articles = new ArrayList();
    int id;
    try {

      st = cnx.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      ResultSet rs = st.executeQuery(request);
      while (rs.next()) {
        id = rs.getInt(1);
        Article a = articleservice.getArticle(id);
        articles.add(a);
      }

    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return articles;
  }

  public int getNbComment(int id) {
    String request = "select count(*) from commentaire where articleID='" + id + "'";
    Statement st;

    try {

      st = cnx.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      ResultSet rs = st.executeQuery(request);
      rs.last();
      return rs.getInt(1);

    } catch (SQLException ex) {
      ex.printStackTrace();

      return 0;
    }
  }
}
