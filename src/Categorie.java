/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */



public class Categorie {
   private   String Cat;
   private   String Icat;
   
   public Categorie(String Icat, String Cat) {
   this.Icat = Icat;
   this.Cat = Cat;
   }

    Categorie() {
    }

    public void setIcat(String Icat) {
        this.Icat = Icat;
    }

    
  
 public void setCat(String Cat) {
        this.Cat= Cat;
    }


  public String getIcat()   {    return Icat;    }

   @Override
  public String toString() {    return Cat;  }

    
  
  
  
  
}



 


