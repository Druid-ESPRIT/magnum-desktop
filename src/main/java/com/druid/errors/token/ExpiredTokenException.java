package com.druid.errors.token;

public class ExpiredTokenException extends TokenException {
  public ExpiredTokenException(String message) {
    super(message);
  }
}
