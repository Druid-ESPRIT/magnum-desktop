package com.druid.utils;

import com.druid.enums.HistoryActivity;
import com.druid.models.History;
import com.druid.models.User;
import com.druid.services.HistoryService;
import java.sql.Timestamp;
import java.util.Date;

public class QuickHistory {

  /**
   * Records the account creation of the given user, <br>
   * i.e. it inserts a new row in the <code>History</code> table to keep track of when the account
   * was made.
   *
   * @param user The user whose account creation will be recorded.
   */
  public static void logAccountCreation(User user) {
    HistoryService hist_svc = new HistoryService();
    History hist = new History();
    hist.setTime(new Timestamp(new Date().getTime()));
    hist.setUserID(user.getID());
    hist.setActivity(HistoryActivity.CORE);
    hist.setDescription("The moment you created your account");
    hist_svc.add(hist, user);
  }

  public static void logAccountLogin(User user) {
    HistoryService hist_svc = new HistoryService();
    History hist = new History();
    hist.setTime(new Timestamp(new Date().getTime()));
    hist.setUserID(user.getID());
    hist.setActivity(HistoryActivity.SECURITY);
    hist.setDescription("You signed in");
    hist_svc.add(hist, user);
  }
}
