package com.druid.errors.login;

public class InvalidCredentialsException extends LoginException {

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
