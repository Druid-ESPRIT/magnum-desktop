package com.druid.enums;

public enum FlagOffense {
    HARASSMENT {
        @Override
        public String toString() {
            return "Harassment";
        }
    },
    SPAM {
        @Override
        public String toString() {
            return "Spam";
        }
    },
    VIOLENCE {
        @Override
        public String toString() {
            return "Violence";
        }
    };

    public static FlagOffense fromString(String value) {
        switch (value) {
            case "Harassment":
                return FlagOffense.HARASSMENT;
            case "Spam":
                return FlagOffense.SPAM;
            case "Violence":
                return FlagOffense.VIOLENCE;
            default:
                throw new IllegalArgumentException();
        }
    }
}
