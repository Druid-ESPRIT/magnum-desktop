package com.druid.enums;

public enum SubscriptionStatus {
    ON_HOLD {
        @Override
        public String toString() {
            return "On Hold";
        }
    },
    ACTIVE {
        @Override
        public String toString() {
            return "Active";
        }
    },
    EXPIRED {
        @Override
        public String toString() {
            return "Expired";
        }
    };


    public static SubscriptionStatus fromString(String value) {
        switch (value) {
            case "On hold":
                return SubscriptionStatus.ON_HOLD;
            case "Active":
                return SubscriptionStatus.ACTIVE;
            case "Expired":
                return SubscriptionStatus.EXPIRED;
            default:
                throw new IllegalArgumentException();
        }
    }

}
