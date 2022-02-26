

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author user
 */
public class Ticket {

    private String ID;
    private String Subject;
    private String Description;
    private String CreationDate;
    private String ReSolverId;
    private String USERID;
    private String STATUS;
    private String Categorie;

    public Ticket(String ID, String Subject, String Description, String CreationDate, String ReSolverId, String USERID, String STATUS,String Categorie) {
        this.ID = ID;
        this.Subject = Subject;
        this.Description = Description;
        this.CreationDate = CreationDate;
        this.ReSolverId = ReSolverId;
        this.USERID = USERID;
        this.STATUS = STATUS;
        this.Categorie = Categorie;
    }

    public String getCategorie() {
        return Categorie;
    }

    public void setCategorie(String Categorie) {
        this.Categorie = Categorie;
    }
    

    public String getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(String CreationDate) {
        this.CreationDate = CreationDate;
    }

    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    Ticket() {
    }

    Ticket(String string) {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

 

    public String getReSolverId() {
        return ReSolverId;
    }

    public void setReSolverId(String ReSolverId) {
        this.ReSolverId = ReSolverId;
    }

}
