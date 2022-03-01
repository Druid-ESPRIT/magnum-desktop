package com.druid.enums;

public enum HistoryActivity {
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
    },
    CORE {
        @Override
        public String toString() {
            return "Core";
        }
    };

    public static HistoryActivity fromString(String value) {
        switch (value) {
            case "Profile":
                return HistoryActivity.PROFILE;
            case "Security":
                return HistoryActivity.SECURITY;
            case "Billing":
                return HistoryActivity.BILLING;
            case "Core":
                return HistoryActivity.CORE;
            default:
                throw new IllegalArgumentException();
        }
    }
}
