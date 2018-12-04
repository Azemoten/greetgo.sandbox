package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.*;

import java.util.List;

public interface ClientRegister {

    List<ClientsDisplay> listClients(Filter filter, Integer page);

    List<Charm> listCharms();

    Integer getClientListCount();

    void deleteClient(Integer id);

    void createFullClient(ClientSave clientSave);

    void exCreate(Client client);
}
