package com.druid.enums;

public enum UserDiscriminator {
    USERS {
        @Override
        public String toString() {
            return "users";
        }
    },
    ADMINISTRATORS {
        @Override
        public String toString() {
            return "administrators";
        }
    },
    PODCASTERS {
        @Override
        public String toString() { return "podcasters"; }
    };

    public static UserDiscriminator fromString(String value) {
        switch (value) {
            case "users":
                return UserDiscriminator.USERS;
            case "administrators":
                return UserDiscriminator.ADMINISTRATORS;
            case "podcasters":
                return UserDiscriminator.PODCASTERS;
            default:
                throw new IllegalArgumentException();
        }
    }
}
