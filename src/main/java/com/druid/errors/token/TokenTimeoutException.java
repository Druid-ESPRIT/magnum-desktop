package com.druid.errors.token;

public class TokenTimeoutException extends TokenException {
  public TokenTimeoutException(String message) {
    super(message);
  }
}
