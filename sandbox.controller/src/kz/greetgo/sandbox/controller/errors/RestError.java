package kz.greetgo.sandbox.controller.errors;

/**
 * <p>
 * Генерируется при обработке запроса по REST.
 * </p>
 * <p>
 * Created by pompei on 2017-06-02.
 * </p>
 */
public class RestError extends RuntimeException {

  public final int statusCode;

  public RestError(int statusCode, String message) {
    super(message);
    this.statusCode = statusCode;
  }

  public RestError(int statusCode) {
    this(statusCode, null);
  }

  public RestError() {
    this(500, null);
  }

  public RestError(String message) {
    this(500, message);
  }
}
