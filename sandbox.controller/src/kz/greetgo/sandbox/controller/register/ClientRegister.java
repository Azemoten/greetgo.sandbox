package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.*;

import java.util.List;

public interface ClientRegister {

    List<ClientRecord> list(ClientFilter clientFilter);

    List<Charm> listCharms();

    Integer numPage(ClientFilter clientFilter);

    void remove(Integer id);

    void create(ClientDetail clientDetail);

    ClientDetail clientDetails(int id);

    void update(ClientDetail clientDetail);
}
