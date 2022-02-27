package com.druid.utils;

import java.text.SimpleDateFormat;

public class Debugger {
  public static boolean isEnabled() {
    return true;
  }

  public static void log(Object o) {
    if (Debugger.isEnabled()) {
      java.util.Date date = new java.util.Date();
      SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
      System.out.println("[" + time + "] " + o.toString());
    }
  }
}
