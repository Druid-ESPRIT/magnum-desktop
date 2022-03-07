package com.druid.errors.login;

public class BannedUserException extends LoginException {
    public BannedUserException(String message) {
        super(message);
    }
}
