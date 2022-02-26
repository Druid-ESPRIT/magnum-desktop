/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class Chat {
    
    
    private String ID;
    private String ReSolverID;
    private String Msg;
    private String DateM;
    private String Evaluate;
    
    
    
    
      public Chat(String ID, String ReSolverID, String Msg, String DateM, String Evaluate) {
        this.ID = ID;
        this.ReSolverID = ReSolverID;
        this.Msg = Msg;
        this.DateM = DateM;
        this.Evaluate = Evaluate;
    }

   


    
    
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getReSolverID() {
        return ReSolverID;
    }

    public void setReSolverID(String ReSolverID) {
        this.ReSolverID = ReSolverID;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public String getDateM() {
        return DateM;
    }

    public void setDateM(String DateM) {
        this.DateM = DateM;
    }

    public String getEvaluate() {
        return Evaluate;
    }

    public void setEvaluate(String Evaluate) {
        this.Evaluate = Evaluate;
    }
    
    
    
    
}
