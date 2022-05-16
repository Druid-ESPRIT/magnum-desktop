/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.druid.interfaces;

import com.druid.models.Playlist;
import java.util.List;

/**
 * @author tahaj
 */
public interface IplaylistService {

  // ADD
  public void ajouterPlaylist(Playlist pl);
  // Select
  public List<Playlist> afficherPlaylist();
  // Delete
  public void supprimerPlaylist(int id);
}
