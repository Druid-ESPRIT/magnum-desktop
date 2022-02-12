package com.druid.interfaces;

import com.druid.models.History;
import java.util.List;

/** This interface exposes the necessary methods to interact with the History model. */
public interface IHistory {

  /** Add a history point to a user's history table */
  public void addHistoryPoint(History h);

  /** Return a list of history points of a particular user */
  public List<History> getHistory();

  /** Clear a user's history table. "clear" is synonymous with "delete", but less vague. */
  public void clearHistory(History u);
}
