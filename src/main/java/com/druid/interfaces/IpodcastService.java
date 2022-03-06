/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.druid.interfaces;

import com.druid.models.Podcast;

import java.util.List;

/**
 *
 * @author tahaj
 */
public interface IpodcastService {
    // ADD
    public void ajouterPodcast(Podcast p);
    //select
    public List<Podcast> afficherPodcast();
    //SUPP
    public void supprimerPodcast(int id);
    //MODIF
    public void modifierPodcast(Podcast p);
    
}
