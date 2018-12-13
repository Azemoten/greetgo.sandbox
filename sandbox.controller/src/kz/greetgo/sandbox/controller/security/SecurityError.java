package kz.greetgo.sandbox.controller.security;


import kz.greetgo.sandbox.controller.errors.RestError;

public class SecurityError extends RestError {

  public SecurityError() {
    this("Security error");
  }

  public SecurityError(String message) {
    super(401, message);
  }
}
