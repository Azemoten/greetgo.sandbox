package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.*;

import java.util.List;

public interface ClientRegister {

    List<ClientRecord> getList(ClientFilter clientFilter);

    List<Charm> listCharms();

    Integer numPage();

    void deleteClient(Integer id);

    void createFullClient(ClientSave clientSave);

    void exCreate(Client client);
}
