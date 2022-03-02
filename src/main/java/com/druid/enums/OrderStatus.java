package com.druid.enums;

public enum OrderStatus {
    PENDING{
        @Override
        public String toString(){
            return "Pending";
        }
    },
    COMPLETED{
        @Override
        public  String toString(){
            return "Completed";
        }
    },
    CANCELED{
        @Override
        public String toString(){
            return "Canceled";
        }
    },
    REFUNDED{
        @Override
         public String toString(){
            return "Refunded";
        }
    };

    public static OrderStatus fromString(String value) {
        switch (value) {
            case "Pending":
                return OrderStatus.PENDING;
            case "Completed":
                return OrderStatus.COMPLETED;
            case "Canceled":
                return OrderStatus.CANCELED;
            case "Refunded":
                return OrderStatus.REFUNDED;
            default:
                throw new IllegalArgumentException();
        }
    }



}
