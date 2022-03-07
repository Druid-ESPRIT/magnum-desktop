package com.druid.errors.register;

public class UsernameTakenException extends RegisterException {

    public UsernameTakenException(String message) {
        super(message);
    }
}
