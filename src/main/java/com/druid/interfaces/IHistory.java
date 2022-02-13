package com.druid.interfaces;

import com.druid.models.History;
import com.druid.models.User;
import java.util.List;

public interface IHistory {
  /** Add a history point to a user's history table. */
  public void addToHistory(History hist, User user);

  /** Return a list of history points of a particular user. */
  public List<History> getHistory(User user);

  /** Clear a user's entire history. */
  public void clearHistory(User user);
}
