/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.druid.interfaces;
import java.util.List;
import com.druid.models.Article;
//import model.Podcaster;

//////mmmm///////////



/**
 *
 * @author zeineb
 */
public interface IarticleService {
     public boolean addArticle(Article A);
     public boolean updateArticle(Article A);
     public boolean cancelArticle(int id);
     public Article getArticle(int id);
    public List<Article> afficherArticle();
 
    
}