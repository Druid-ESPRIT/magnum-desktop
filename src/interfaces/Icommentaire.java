/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.List;
import models.Commentaire;

/**
 *
 * @author zeineb
 */
public interface Icommentaire {
      public boolean addCommentaire(Commentaire C);
    public boolean updateCommentaire(Commentaire C);
    public boolean cancelCommentaire(Commentaire C);
    public Commentaire getCommentaire(int id);
    public List<Commentaire> afficherCommentaire();
    
    
}
