package com.druid.errors.register;

public class EmailTakenException extends RegisterException {

  public EmailTakenException(String message) {
    super(message);
  }
}
