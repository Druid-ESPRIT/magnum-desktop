package com.druid.enums;

public enum HistoryActivities {
  PROFILE {
    @Override
    public String toString() {
      return "Profile";
    }
  },
  SECURITY {
    @Override
    public String toString() {
      return "Security";
    }
  },
  BILLING {
    @Override
    public String toString() {
      return "Billing";
    }
  };

  public static HistoryActivities fromString(String value) {
    switch (value) {
      case "Profile":
        return HistoryActivities.PROFILE;
      case "Security":
        return HistoryActivities.SECURITY;
      case "Billing":
        return HistoryActivities.BILLING;
      default:
        throw new IllegalArgumentException();
    }
  }
}
