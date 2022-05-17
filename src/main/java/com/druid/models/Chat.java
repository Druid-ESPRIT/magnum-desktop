package com.druid.models; /*
                           * To change this license header, choose License Headers in Project Properties.
                           * To change this template file, choose Tools | Templates
                           * and open the template in the editor.
                           */

/**
 * @author user
 */
public class Chat {

  private String ID;
  private String ReSolverID;
  private String Msg;
  private String DateM;
  private String USERID;
  private String Ifrom;

  public Chat(String ID, String ReSolverID, String Msg, String DateM, String USERID, String Ifrom) {
    this.ID = ID;
    this.ReSolverID = ReSolverID;
    this.Msg = Msg;
    this.DateM = DateM;
    this.USERID = USERID;
    this.Ifrom = Ifrom;
  }

  public Chat() {}

  public Chat(String Msg, String DateM) {
    this.Msg = Msg;
    this.DateM = DateM;
  }

  Chat(String string) {}

  public String getIfrom() {
    return Ifrom;
  }

  public void setIfrom(String Ifrom) {
    this.Ifrom = Ifrom;
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

  public String getUSERID() {
    return USERID;
  }

  public void setUSERID(String USERID) {
    this.USERID = USERID;
  }
}
