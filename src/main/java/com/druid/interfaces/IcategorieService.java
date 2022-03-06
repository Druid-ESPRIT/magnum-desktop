/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.druid.interfaces;

import com.druid.models.Categorie;
import java.util.List;

/** @author tahaj */
public interface IcategorieService {
  // ADD
  public void ajouterCategorie(Categorie c);
  // select
  public List<Categorie> afficherCategorie();
  // SUPP
  public void supprimerCategorie(int id);
}
