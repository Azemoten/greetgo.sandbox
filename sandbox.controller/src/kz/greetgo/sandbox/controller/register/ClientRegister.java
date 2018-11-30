package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.Charm;
import kz.greetgo.sandbox.controller.model.ClientSave;
import kz.greetgo.sandbox.controller.model.ClientsDisplay;

import java.util.List;

public interface ClientRegister {

    List<ClientsDisplay> listClients();

    List<Charm> listCharms();

    void deleteClient(Integer id);

    void createFullClient(ClientSave clientSave);
}
