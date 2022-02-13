package com.druid.interfaces;

import com.druid.models.History;
import com.druid.models.User;
import java.util.List;

/** This interface exposes the necessary methods to interact with the History model. */
public interface IHistory {
  /** Add a history point to a user's history table */
  public void addToHistory(History hist, User user);

  /** Return a list of history points of a particular user */
  public List<History> getHistory(User user);

  /** Clear a user's history. */
  public void deleteHistory(User user);
}
