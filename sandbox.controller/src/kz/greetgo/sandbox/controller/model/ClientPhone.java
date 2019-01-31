package kz.greetgo.sandbox.controller.model;

public class ClientPhone {

  public Integer client;
  public String number;
  public PhoneType type;
  public String ciaId;

  public ClientPhone(String number, PhoneType type, String ciaId) {
    this.number = number;
    this.type = type;
    this.ciaId = ciaId;
  }
  public ClientPhone(){

  }
}
