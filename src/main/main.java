/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import models.Article;
import models.Commentaire;
import models.Podcaster;
import models.User;
import services.ArticleService;
import services.CommentaireService;
import services.PodcasterService;
import services.UserService;


/**
 *
 * @author zeineb
 */
public class main{

  
    public static void main(String[] args) {
        
        
        // TODO code application logic here
        
         List<Article> articles = new ArrayList<>();
         List<Commentaire> commentaires = new ArrayList<>();
       
        
        //service
         ArticleService As =new ArticleService();
         CommentaireService Cs =new CommentaireService();
         PodcasterService P =new PodcasterService();
         Podcaster podcaster1=P.getPodcaster(2);
         UserService Us=new UserService();
         
         //Article article
         
         
        
         //Podcaster P1;
         //
         
         ///Article A = new Article(podcaster1, "farah", "farahDescription");
        // Article A1 = new Article(podcaster1, "TestEvent", "TestEventDescription");
         //*******************************add
          //As.addArticle(A);
          //As.addArticle(A1);
         //As.addArticle(A1);
         
         //******************************upload
        /* Article article1 = As.getArticle(33);
         article1.setTitle("llllllllllllllll");
         article1.setContent("hhhhhh");
         As.updateArticle(article1);*/
        System.out.println(As.afficherArticle());
        //*******************************Delete
        //Article article1 = As.getArticle(30);
        
                
         //********************************* Commentaire
         //*****add
         User user1;
         user1 = Us.getUser(1);
         Article article =  As.getArticle(34);
         Commentaire C;
         C = new Commentaire(user1,article,"message",new Date(System.currentTimeMillis()));
        //Cs.addCommentaire(C);       
         ////delete 
         
      Commentaire c2= Cs.getCommentaire(4);
      //Cs.cancelCommentaire(c2);
     //upldate  
      //c2.setMessage("warming");
      //Cs.updateCommentaire(c2);
       
      
        //max commented article
        System.out.println("max commented article ");
        System.out.println(Cs.getTopArticle());
        
        //tree par max comment
         System.out.println("tree par max comment ");
        List<Article> articlesort=Cs.articleSorted();
        articlesort.forEach(a -> System.out.println(a));
        System.out.println("article by author ");
        List<Article> articleAuthor=As.getArticleByAuthorName("zeineb");
        articleAuthor.forEach(a -> System.out.println(a));
        System.out.println("most active author");
        System.out.println(As.mostActiveAuthor());

        
    }}           
                
                
    

