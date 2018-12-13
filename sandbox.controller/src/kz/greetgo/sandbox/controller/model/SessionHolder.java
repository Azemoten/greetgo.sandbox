package kz.greetgo.sandbox.controller.model;

import java.io.Serializable;

public class SessionHolder implements Serializable {

  public final String personId;
  public final String mode;

  public SessionHolder(String personId, String mode) {
    this.personId = personId;
    this.mode = mode;
  }
}
