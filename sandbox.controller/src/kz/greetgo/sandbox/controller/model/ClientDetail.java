package kz.greetgo.sandbox.controller.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class ClientDetail {

  public ClientDetail(Client client,
                      List<ClientAddr> addrs,
                      List<ClientPhone> phones) {
    this.client = client;
    this.addrs = addrs;
    this.phones = phones;
  }
  public ClientDetail(){}

  public Client client;
  public List<ClientAddr> addrs;
  public List<ClientPhone> phones;
}
