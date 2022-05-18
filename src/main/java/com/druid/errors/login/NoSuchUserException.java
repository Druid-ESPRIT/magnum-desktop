package com.druid.errors.login;

public class NoSuchUserException extends LoginException {

    public NoSuchUserException(String message) {
        super(message);
    }
}
