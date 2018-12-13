package kz.greetgo.sandbox.controller.errors;

public class IllegalLoginOrPassword extends RestError {

  public IllegalLoginOrPassword() {
    super(401, "Не верен пользователь и/или пароль");
  }
}
