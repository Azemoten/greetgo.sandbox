package kz.greetgo.sandbox.controller.errors;

public class NotFound extends RestError {

  public NotFound() {
    super(404, "Not Found");
  }
}
