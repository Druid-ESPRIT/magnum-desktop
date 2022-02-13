package com.druid.interfaces;

import com.druid.models.Flag;
import com.druid.models.User;

import java.util.List;

public interface IFlag {
  /** Flag a particular user. */
  public void flag(Flag flag, User user);

  /** Return a list of flags of a particular user. */
  public List<Flag> getFlags(User user);

  /** Remove a flag for a particular user. */
  public void unflag(int flagID);
}
