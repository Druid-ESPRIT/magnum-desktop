package com.druid.enums;

public enum UserStatus {
    ACTIVE {
        @Override
        public String toString() {
            return "Active";
        }
    },
    DISABLED {
        @Override
        public String toString() {
            return "Disabled";
        }
    },
    BANNED {
        @Override
        public String toString() {
            return "Banned";
        }
    };

    public static UserStatus fromString(String value) {
        switch (value) {
            case "Active":
                return UserStatus.ACTIVE;
            case "Disabled":
                return UserStatus.DISABLED;
            case "Banned":
                return UserStatus.BANNED;
            default:
                throw new IllegalArgumentException();
        }
    }
}
