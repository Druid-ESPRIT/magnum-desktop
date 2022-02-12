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
}
